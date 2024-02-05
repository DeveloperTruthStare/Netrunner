package com.smith.netrunner.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.smith.netrunner.BackgroundManager;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.Corporation.Corporation;
import com.smith.netrunner.Corporation.RunTarget;
import com.smith.netrunner.GameData.BattleState;
import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.GameData.Deck;
import com.smith.netrunner.GameData.IHoverableCallback;
import com.smith.netrunner.HandDisplay.HandDisplay;
import com.smith.netrunner.HardwareRig.HardwareRig;
import com.smith.netrunner.HealthBar.HealthBarView;
import com.smith.netrunner.InfoWindow.InfoWindow;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.UI.ClickCallbackListener;
import com.smith.netrunner.UI.ImageButton;

import java.util.ArrayList;

public class BattleScreen extends BaseGameObject implements IHoverableCallback {
    private ClickCallbackListener onClickEndTurn  = new ClickCallbackListener() {
        @Override
        public void onClick() {
            if (battleState.getState() == BattleState.STATE.PLAYER_ACTION) {
                endTurn();
                if (battleState.turns == 2)
                    ((GameScreen)parent).quitBattle();
            }
        }
    };
    private ClickCallbackListener onClickRND = new ClickCallbackListener() {
        @Override
        public void onClick() {
            if (battleState.getState() != BattleState.STATE.SELECTING_SERVER) return;
            battleState.selectServer(0);

            for(RunTarget server : corporationServers) {
                server.setShowOutline(false);
            }
            corporationServers.get(battleState.currentServer).targetServer();
        }
    };
    private ClickCallbackListener onInfoClick = new ClickCallbackListener() {
        @Override
        public void onClick() {
            System.out.println("Info Clicked");
            if (battleState.getState() == BattleState.STATE.ACCEPTING_REWARDS) {
                battleState.nextState();
                infoWindow.setActive(false);
                corporationServers.get(battleState.currentServer).unTarget();
                battleState.infoToDisplay = "";
            }
        }
    };
    private final InfoWindow infoWindow;
    private final HandDisplay handDisplay;
    private final HealthBarView hpView;
    private final Stage stage;
    private final HardwareRig hardwareRig;
    private final Texture[] cyclesTexture;
    private final BattleState battleState;
    private final ImageButton endTurnButton;
    private final ArrayList<RunTarget> corporationServers;

    private final Image bannerImage;
    private int bannerImagexPos = -1920;
    private final BackgroundManager background;
    private final Deck deck;

    public BattleScreen(RootApplication app, GameScreen gameScreen) {
        super(app, gameScreen);
        deck = new Deck();

        background = new BackgroundManager(app);
        stage = new Stage();

        battleState = new BattleState();

        infoWindow = new InfoWindow(app, onInfoClick);

        hpView = new HealthBarView(app);

        endTurnButton = new ImageButton(app, onClickEndTurn, new Image(new Texture("BattleScreen/endTurnButton.png")));
        endTurnButton.setPosition(1720, 10);


        // Create Child Game Objects
        handDisplay = new HandDisplay(app);
        hardwareRig = new HardwareRig(app);

        // Create Cycles Texture
        cyclesTexture = new Texture[4];
        cyclesTexture[0] = new Texture("CycleCounter/0x3Cycles.png");
        cyclesTexture[1] = new Texture("CycleCounter/1x3Cycles.png");
        cyclesTexture[2] = new Texture("CycleCounter/2x3Cycles.png");
        cyclesTexture[3] = new Texture("CycleCounter/3x3Cycles.png");

        // Create Background image and add it to the stage
        //Image hardwareBackground = new Image(new Texture("BattleScreen/battleBackground.png"));
        //stage.addActor(hardwareBackground);

        bannerImage = new Image(new Texture("BattleScreen/battleBanner.png"));
        bannerImage.setPosition(-1920, 980);
        stage.addActor(bannerImage);

        corporationServers = new ArrayList<>();

        RunTarget server = new RunTarget(app, onClickRND);
        server.setPosition(1270, 150);
        addChild(server);
        corporationServers.add(server);

        // Add child objects
        addChild(hardwareRig);
        addChild(handDisplay);
        addChild(endTurnButton);
        addChild(infoWindow);
    }

    public void reset() {
        background.reset();
        hardwareRig.reset();
        handDisplay.reset();
        battleState.reset();

        deck.loadDeck(((GameScreen)parent).deck);
    }

    public void setCorporation(Corporation corp) {

    }

    @Override
    public void draw(float delta) {
        if (!isActive) return;
        // Draw the background
        background.draw(delta);
        stage.draw();
        hpView.draw(delta);

        // Draw Children
        super.draw(delta);

        // Draw remaining Cycles Texture
        app.batch.draw(cyclesTexture[battleState.curCycles], 20, 80);
        update(delta);

        if (!battleState.infoToDisplay.isEmpty()) {
            infoWindow.setData(battleState.infoToDisplay);
            infoWindow.setActive(true);
            infoWindow.setPosition(1920/2 - 170, 1080/2 - 50);
            infoWindow.setSize(340, 100);
        }
    }

    public void update(float delta) {
        switch(battleState.getState()) {
            case LOADING:
                loadingUpdate(delta);
                break;
            case DISCARD:
                discardUpdate(delta);
                break;
            case DRAW:
                drawHandUpdate(delta);
                break;
            case PLAYER_ACTION:
                break;
            case SELECTING_SERVER:
                for(RunTarget server : corporationServers) {
                    server.setShowOutline(true);
                }
                break;
            case DEALING_WITH_ICE:
                if (corporationServers.get(battleState.currentServer).hasAccess()) {
                    battleState.nextState();
                }
                RunTarget.Ice ice = corporationServers.get(battleState.currentServer).getActiveIce();

                break;
            case ACCEPTING_REWARDS:
                // Show window for rewards
                battleState.infoToDisplay = "Server Access Granted, Here are the rewards";
                break;
            case ENEMY_TURN:
                enemyTurnUpdate(delta);
                break;
        }

    }
    private void enemyTurnUpdate(float delta) {
        System.out.println("Doing enemy turn");
        battleState.nextState();
    }
    private void loadingUpdate(float delta) {
        updateBannerPosition(delta);

        timeSinceStart += delta;
        if (timeSinceStart >= 2) {
            timeSinceStart = 0;
            battleState.nextState();
        }
    }
    private float timeSinceStart = 0;
    public void discardUpdate(float delta) {
        timeSinceStart += delta;
        // Play Animations here

        if (timeSinceStart > 0.2) {
            timeSinceStart = 0;
            Card card = handDisplay.discard();
            if (card == null) {
                battleState.nextState();
            } else {
                deck.discardCard(card);
            }
        }
    }
    private int cardsDrawn = 0;
    private final int cardsToDraw = 5;
    public void drawHandUpdate(float delta) {
        timeSinceStart += delta;
        // Play Animations here
        if (timeSinceStart > 0.2) {
            timeSinceStart = 0;
            Card card = deck.drawFromDeck();
            if (card == null) {
                cardsDrawn = 0;
                battleState.nextState();
            } else {
                handDisplay.addToHand(card);
                cardsDrawn++;
                if (cardsDrawn == cardsToDraw) {
                    battleState.nextState();
                    cardsDrawn = 0;
                }
            }
        }
    }

    private void updateBannerPosition(float delta) {
        if (battleState.getState() != BattleState.STATE.LOADING) return;

        int bannerImageSpeed = 5000;
        bannerImagexPos += bannerImageSpeed * delta;
        if (bannerImagexPos >= 0) {
            bannerImagexPos = 0;
        }
        bannerImage.setPosition(bannerImagexPos, 980);
    }

    public void onHover(String dataToDisplay, int x, int y) {
        infoWindow.setData(dataToDisplay);
        infoWindow.setActive(true);
        infoWindow.setPosition(x, y);
    }
    public void onHoverEnd() {
        infoWindow.setActive(false);
    }


    private void installHardware(Card card) {
        if (hardwareRig.installOnHovered(card)) {
            battleState.curCycles -= card.cost;
            handDisplay.removeHoveredCard();
        } else {
            handDisplay.unHoverCard();
        }
    }
    private void startEvent(Card card) {
        // Start the event if we're not hovering the hardware rig
        if (-1 != hardwareRig.getHoveredHardware()) {
            handDisplay.unHoverCard();
            return;
        }
        battleState.curCycles -= card.cost;
        handDisplay.removeHoveredCard();
        switch(card.cardSubType) {
            case RUN:
                battleState.startRun();
                deck.discardCard(card);
                break;
        }
    }
    @Override
    public void touchUp(int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY, pointer, button);
        if (!isActive) return;

        // Get the hovered card
        Card hoveredCard = handDisplay.getHoveredCard();
        if (null == hoveredCard) return;
        if (!battleState.canPerformAction(hoveredCard.cardType, hoveredCard.cost)){
            handDisplay.unHoverCard();
            return;
        }
        switch(hoveredCard.cardType) {
            case HARDWARE:
                installHardware(hoveredCard);
                break;
            case EVENT:
                startEvent(hoveredCard);
                break;
            default:
                handDisplay.unHoverCard();
        }
    }
    public void endTurn() {
        battleState.nextState();
    }
}

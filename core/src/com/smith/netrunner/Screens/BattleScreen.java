package com.smith.netrunner.Screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.Corporation.ResearchAndDevelopment;
import com.smith.netrunner.Corporation.RunTarget;
import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.GameData.IHoverableCallback;
import com.smith.netrunner.HandDisplay.HandDisplay;
import com.smith.netrunner.HardwareRig.DefaultIceBreaker;
import com.smith.netrunner.HardwareRig.HardwareRig;
import com.smith.netrunner.HealthBar.HealthBarView;
import com.smith.netrunner.InfoWindow.InfoWindow;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.Target;
import com.smith.netrunner.UI.ClickCallbackListener;
import com.smith.netrunner.UI.ImageButton;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class BattleScreen extends BaseGameObject implements Screen, IHoverableCallback {
    private ClickCallbackListener onClickEndTurn  = new ClickCallbackListener() {
        @Override
        public void onClick() {
            battleState.endTurn();
        }
    };
    private ClickCallbackListener onClickRND = new ClickCallbackListener() {
        @Override
        public void onClick() {
            battleState.setAction("RUNNING_0");
        }
    };

    public class BattleState {
        private int maxCycles = 3;
        public int curCycles = 3;
        public boolean playerTurn = true;
        public boolean playerPerformingAction = true;
        public String currentAction = "LOADING";
        public boolean canPerformAction(Card.CardType action, int cost) {
            if (cost > curCycles) return false;
            if (!playerTurn) return false;
            if (currentAction.startsWith("RUNNING")) {
                return false;
            }
            return true;
        }
        public void startPlayerTurn() {
            curCycles = maxCycles;
        }
        public void setAction(String newAction) {
            currentAction = newAction;
            for(RunTarget rt : corporationServers) {
                rt.setAction(newAction);
            }

            if (newAction.equals("RUNNING")) {
                // Need to choose target
                infoWindow.setData("Select Target server to run against");
                infoWindow.setActive(true);
                infoWindow.setPosition(1920/2 - 170, 1080/2 - 50);
                infoWindow.setSize(340, 100);
            } else {
                infoWindow.setActive(false);
            }
        }
        public void endTurn() {
            if (!currentAction.startsWith("RUNNING")) {
                playerTurn = false;
            }
        }
    }
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
    private int bannerImageSpeed = 5000;

    RunTarget server;
    public BattleScreen(RootApplication app) {
        super(app);
        stage = new Stage();

        battleState = new BattleState();

        infoWindow = new InfoWindow(app);

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
        Image hardwareBackground = new Image(new Texture("BattleScreen/battleBackground.png"));
        stage.addActor(hardwareBackground);

        bannerImage = new Image(new Texture("BattleScreen/battleBanner.png"));
        bannerImage.setPosition(-1920, 980);
        stage.addActor(bannerImage);

        corporationServers = new ArrayList<>();

        server = new ResearchAndDevelopment(app, onClickRND);
        server.setPosition(1270, 150);
        addChild(server);
        corporationServers.add(server);

        // Add child objects

        addChild(handDisplay);
        addChild(hardwareRig);
        addChild(endTurnButton);
        addChild(infoWindow);

    }

    @Override
    public void render(float delta) {
        // Draw the background
        stage.draw();
        hpView.draw(delta);

        // Draw Children
        super.draw(delta);

        // Draw remaining Cycles Texture
        app.batch.draw(cyclesTexture[battleState.curCycles], 0, 0);

        update(delta);
    }


    public void update(float delta) {
        updateBannerPosition(delta);
    }

    private void updateBannerPosition(float delta) {
        if (!battleState.currentAction.equals("LOADING")) return;

        bannerImagexPos += bannerImageSpeed * delta;
        if (bannerImagexPos >= 0) {
            battleState.setAction("PLAYER_TURN");
            bannerImagexPos = 0;
        }
        bannerImage.setPosition(bannerImagexPos, 980);
    }
    @Override
    public void show() {

    }
    @Override
    public void resize(int width, int height) {

    }
    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
    @Override
    public void hide() {

    }
    @Override
    public void dispose() {

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
                battleState.setAction("RUNNING");
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

    @Override
    public void keyDown(int keycode) {
        super.keyDown(keycode);
        if (keycode == Input.Keys.SPACE && battleState.playerTurn) {
            // Add a new card
            handDisplay.addToHand(Card.GenerateRandomCard());
        } else if (Input.Keys.A == keycode) {
            battleState.curCycles = battleState.maxCycles;
        }
    }
}

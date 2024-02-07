package com.smith.netrunner.Screens;

import com.badlogic.gdx.Game;
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
import com.smith.netrunner.GameData.Reward;
import com.smith.netrunner.HandDisplay.HandDisplay;
import com.smith.netrunner.HardwareRig.HardwareRig;
import com.smith.netrunner.HealthBar.HealthBarView;
import com.smith.netrunner.InfoWindow.InfoWindow;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.UI.Button;
import com.smith.netrunner.UI.ClickCallbackListener;
import com.smith.netrunner.UI.ImageButton;
import com.smith.netrunner.UI.RewardWindow;

import java.util.ArrayList;

public class BattleScreen extends BaseGameObject implements IHoverableCallback {
    // Add Money
    private ClickCallbackListener acceptMoneyCallback = new ClickCallbackListener() {
        @Override
        public void onClick() {
            System.out.println("Accept Money");
            rewardWindow.removeButton(acceptMoneyButton);
            ((GameScreen)parent).world.player.money += rewardSum.money;
            rewardsToAccept--;
        }
    };
    private final Button acceptMoneyButton;
    // Add trace button
    private ClickCallbackListener acceptTraceCallback = new ClickCallbackListener() {
        @Override
        public void onClick() {
            rewardWindow.removeButton(acceptTraceButton);
            ((GameScreen)parent).world.player.trace += rewardSum.trace;
            rewardsToAccept--;
        }
    };
    private final Button acceptTraceButton;
    // Add proxy server location
    private ClickCallbackListener proxyServerCallback = new ClickCallbackListener() {
        @Override
        public void onClick() {
            rewardWindow.removeButton(proxyServerButton);
            ((GameScreen)parent).world.player.hasServerLocation = true;
            rewardsToAccept--;
        }
    };
    private final Button proxyServerButton;
    // Add decrypted key
    private ClickCallbackListener decryptedKeyCallback = new ClickCallbackListener() {
        @Override
        public void onClick() {
            rewardWindow.removeButton(decryptedKeyButton);
            ((GameScreen)parent).world.player.hasDecryptedKey = true;
            rewardsToAccept--;
        }
    };
    private final Button decryptedKeyButton;
    // Add encrypted key
    private ClickCallbackListener encryptedKeyCallback = new ClickCallbackListener() {
        @Override
        public void onClick() {
            rewardWindow.removeButton(encryptedKeyButton);
            ((GameScreen)parent).world.player.hasEncryptedKey = true;
            rewardsToAccept--;
        }
    };
    private final Button encryptedKeyButton;
    // Add decryption algorithm
    private ClickCallbackListener decryptionAlgoCallback = new ClickCallbackListener() {
        @Override
        public void onClick() {
            rewardWindow.removeButton(decryptionAlgoButton);
            ((GameScreen)parent).world.player.hasDecryptionAlgo = true;
            rewardsToAccept--;
        }
    };
    private final Button decryptionAlgoButton;

    // Add boss location
    private ClickCallbackListener bossLocationCallback = new ClickCallbackListener() {
        @Override
        public void onClick() {
            rewardWindow.removeButton(bossLocationButton);
            ((GameScreen)parent).world.revealBossLocation();
            rewardsToAccept--;
        }
    };
    private final Button bossLocationButton;
    // Add card rewards
    private ClickCallbackListener cardCallback = new ClickCallbackListener() {
        @Override
        public void onClick() {
            rewardWindow.removeButton(cardButton);
            ((GameScreen)parent).world.player.hasDecryptionAlgo = true;
            rewardsToAccept--;
        }
    };
    private final Button cardButton;
    private int rewardsToAccept;


    private ClickCallbackListener onClickEndTurn  = new ClickCallbackListener() {
        @Override
        public void onClick() {
            if (battleState.getState() == BattleState.STATE.PLAYER_ACTION) {
                endTurn();
            }
        }
    };
    private ClickCallbackListener onInfoClick = new ClickCallbackListener() {
        @Override
        public void onClick() {
            if (battleState.getState() == BattleState.STATE.ACCEPTING_REWARDS) {
                // Accept rewards
                Reward reward = corporationServers.get(battleState.currentServer).server.reward;
                rewards.add(reward);
                corporationServers.get(battleState.currentServer).server.hacked = true;

                // Reset to waiting_for_player_action
                battleState.nextState();
                infoWindow.setActive(false);
                corporationServers.get(battleState.currentServer).unTarget();
                battleState.infoToDisplay = "";
            }
        }
    };
    private final InfoWindow infoWindow;
    private final HandDisplay handDisplay;
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
    private RewardWindow rewardWindow;
    public ArrayList<Reward> rewards;
    private Reward rewardSum;

    public BattleScreen(RootApplication app, GameScreen gameScreen) {
        super(app, gameScreen);
        deck = new Deck();
        rewards = new ArrayList<>();

        background = new BackgroundManager(app);
        stage = new Stage();

        battleState = new BattleState();

        infoWindow = new InfoWindow(app, onInfoClick);
        rewardWindow = new RewardWindow(app, this);
        rewardWindow.setActive(false);
        rewardWindow.setPosition(1920/2, 1080/2);
        rewardWindow.setSize(1920/3, 1080-300);

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

        // Add child objects
        addChild(hardwareRig);
        addChild(handDisplay);
        addChild(endTurnButton);
        addChild(infoWindow);
        addChild(rewardWindow);


        acceptMoneyButton = new Button(app, acceptMoneyCallback);
        acceptTraceButton = new Button(app, acceptTraceCallback);
        proxyServerButton = new Button(app, proxyServerCallback);
        proxyServerButton.setText("Accept Firewall Address");
        decryptedKeyButton = new Button(app, decryptedKeyCallback);
        decryptedKeyButton.setText("Accept Decrypted Key");
        decryptionAlgoButton = new Button(app, decryptionAlgoCallback);
        decryptionAlgoButton.setText("Accept Decryption Algorithm");
        encryptedKeyButton = new Button(app, encryptedKeyCallback);
        encryptedKeyButton.setText("Accept Encrypted Key");
        bossLocationButton = new Button(app, bossLocationCallback);
        bossLocationButton.setText("Reveal Firewall address");
        cardButton = new Button(app, cardCallback);
        cardButton.setText("Select card to add to deck");
    }

    public void reset() {
        background.reset();
        hardwareRig.reset();
        handDisplay.reset();
        battleState.reset();

        deck.loadDeck(((GameScreen)parent).deck);
    }
    ArrayList<ClickCallbackListener> listeners = new ArrayList<>();
    public void setCorporation(Corporation corp) {
        listeners.clear();
        for(RunTarget target : corporationServers) {
            removeChild(target);
        }
        corporationServers.clear();
        rewards.clear();
        for(int i = 0; i < corp.servers.size(); ++i) {
            int finalI = i;
             ClickCallbackListener l = new ClickCallbackListener() {
                @Override
                public void onClick() {
                    if (battleState.getState() == BattleState.STATE.SELECTING_SERVER) {
                        battleState.selectServer(finalI);
                        for(RunTarget server : corporationServers) {
                            server.setShowOutline(false);
                        }
                        corporationServers.get(finalI).targetServer();

                    }
                }
            };
            listeners.add(l);
            RunTarget target = new RunTarget(app, l);
            target.setServer(corp.servers.get(i));
            target.setPosition(1320, 200 + 200 * i);
            corporationServers.add(target);
            addChild(target);
        }
    }

    @Override
    public void draw(float delta) {
        if (!isActive) return;
        // Draw the background
        background.draw(delta);
        stage.draw();

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
            case POST_ACCEPT_REWARDS:
                boolean unHackedServer = false;
                for(RunTarget corp : corporationServers) {
                    if (!corp.server.hacked) {
                        unHackedServer = true;
                    }
                }
                if (unHackedServer) {
                    battleState.nextState();
                } else {
                    battleState.endBattle();
                    rewardSum = new Reward();
                    for(Reward reward : rewards) {
                        rewardSum.add(reward);
                    }
                    rewardsToAccept = 0;
                    acceptMoneyButton.setText(rewardSum.money + " Credits");
                    rewardWindow.addButton(acceptMoneyButton);
                    rewardsToAccept++;
                    if (rewardSum.trace > 0) {
                        rewardWindow.addButton(acceptTraceButton);
                        acceptTraceButton.setText("You got " + rewardSum.trace + " additional traces");
                        rewardsToAccept++;
                    }
                    if (rewardSum.proxyServer) {
                        rewardsToAccept++;
                        rewardWindow.addButton(proxyServerButton);
                    }
                    if (rewardSum.decryptedKey) {
                        rewardsToAccept++;
                        rewardWindow.addButton(decryptedKeyButton);
                    }
                    if (rewardSum.encryptedKey) {
                        rewardsToAccept++;
                        rewardWindow.addButton(encryptedKeyButton);
                    }
                    if (rewardSum.decryptionAlgorithm) {
                        rewardsToAccept++;
                        rewardWindow.addButton(decryptionAlgoButton);
                    }
                    if (rewardSum.bossLocation) {
                        rewardsToAccept++;
                        rewardWindow.addButton(bossLocationButton);
                    }
                    if (rewardSum.cards.size() > 0) {
                        rewardsToAccept++;
                        rewardWindow.addButton(cardButton);
                    }
                    rewardWindow.setActive(true);
                }
                break;
            case ENEMY_TURN:
                enemyTurnUpdate(delta);
                break;
            case FINALIZING_BATTLE:
                if (rewardsToAccept == 0) {
                    rewardWindow.setActive(false);
                    ((GameScreen)parent).quitBattle();
                }
                // Show Battle Rewards Window
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
            case ACTION:
                ((GameScreen)parent).world.player.money += card.value;
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

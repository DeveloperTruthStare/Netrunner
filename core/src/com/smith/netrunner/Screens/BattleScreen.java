package com.smith.netrunner.Screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.GameData.IHoverableCallback;
import com.smith.netrunner.HandDisplay.HandDisplay;
import com.smith.netrunner.HardwareRig.DefaultIceBreaker;
import com.smith.netrunner.HardwareRig.HardwareRig;
import com.smith.netrunner.HealthBar.HealthBarView;
import com.smith.netrunner.InfoWindow.InfoWindow;
import com.smith.netrunner.RootApplication;

public class BattleScreen extends BaseGameObject implements Screen, IHoverableCallback {
    public class BattleState {
        private int maxCycles = 3;
        public int curCycles = 3;
        public boolean canPerformAction(int cost) {
            return cost >= curCycles;
        }
        public void startPlayerTurn() {
            curCycles = maxCycles;
        }
    }
    private final InfoWindow infoWindow;
    private final HandDisplay handDisplay;
    private final HealthBarView hpView;
    private final Stage stage;
    private final HardwareRig hardwareRig;
    private String currentAction;
    private final Texture[] cyclesTexture;
    private final BattleState battleState;

    public BattleScreen(RootApplication app) {
        super(app);
        battleState = new BattleState();
        infoWindow = new InfoWindow(app);
        hpView = new HealthBarView(app);
        stage = new Stage();

        // Create Child Game Objects
        handDisplay = new HandDisplay(app);
        addChild(handDisplay);
        hardwareRig = new HardwareRig(app);
        addChild(hardwareRig);

        // Create Cycles Texture
        cyclesTexture = new Texture[4];
        cyclesTexture[0] = new Texture("CycleCounter/0x3Cycles.png");
        cyclesTexture[1] = new Texture("CycleCounter/1x3Cycles.png");
        cyclesTexture[2] = new Texture("CycleCounter/2x3Cycles.png");
        cyclesTexture[3] = new Texture("CycleCounter/3x3Cycles.png");

        // Create Background image and add it to the stage
        Image hardwareBackground = new Image(new Texture("BattleScreen/battleBackground.png"));
        stage.addActor(hardwareBackground);

        Image bannerTexture = new Image(new Texture("BattleScreen/battleBanner.png"));
        bannerTexture.setPosition(0, 980);
        stage.addActor(bannerTexture);

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
        infoWindow.setVisible(true);
        infoWindow.setMousePosition(x, y);
    }
    public void onHoverEnd() {
        infoWindow.setVisible(false);
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

    }
    @Override
    public void touchUp(int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY, pointer, button);
        if (!isActive) return;

        // Check end turn button?

        // Get the hovered card
        Card hoveredCard = handDisplay.getHoveredCard();
        if (null == hoveredCard) return;
        if (battleState.curCycles < hoveredCard.cost){
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
        if (keycode == Input.Keys.SPACE) {
            // Add a new card
            handDisplay.addToHand(Card.GenerateRandomCard());
        } else if (Input.Keys.A == keycode) {
            battleState.curCycles = battleState.maxCycles;
        }
    }
}

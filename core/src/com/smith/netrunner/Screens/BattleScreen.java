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

    private final InfoWindow infoWindow;
    private final HandDisplay handDisplay;
    private final HealthBarView hpView;
    private final Stage stage;
    private final HardwareRig hardwareRig;
    private String currentAction;

    public BattleScreen(RootApplication app) {
        super(app);
        infoWindow = new InfoWindow(app);
        handDisplay = new HandDisplay(app);
        hpView = new HealthBarView(app);
        stage = new Stage();

        Image hardwareBackground = new Image(new Texture("BattleScreen/battleBackground.png"));
        stage.addActor(hardwareBackground);

        Image bannerTexture = new Image(new Texture("BattleScreen/battleBanner.png"));
        bannerTexture.setPosition(0, 980);
        stage.addActor(bannerTexture);
        hardwareRig = new HardwareRig(app);

        addListener(hardwareRig);
    }

    @Override
    public void render(float delta) {
        stage.draw();
        hpView.draw(delta);
        handDisplay.draw(delta);
        hardwareRig.draw(delta);
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

    @Override
    public void mouseMoved(int screenX, int screenY) {
        super.mouseMoved(screenX, screenY);
        System.out.println(screenX + ", " + screenY);
        handDisplay.mouseMoved(screenX, screenY);
    }
    @Override
    public void keyDown(int keycode) {
        super.keyDown(keycode);
        if (keycode == Input.Keys.SPACE) {
            // Add a new card
            handDisplay.addToHand(new Card());
        } else if (keycode == Input.Keys.Q) {
            hardwareRig.installOnHovered(new DefaultIceBreaker(this.app));
        }
    }
}

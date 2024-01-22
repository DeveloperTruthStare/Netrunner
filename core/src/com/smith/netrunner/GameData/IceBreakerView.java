package com.smith.netrunner.GameData;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.BaseGameObject;

public class IceBreakerView extends BaseGameObject {
    private RootApplication app;
    private IceBreaker iceBreaker;
    private IHoverableCallback callback;
    private boolean showingData = false;
    private int x, y, width, height;
    private int bottomHeight = 60;
    private int positionInHardwareRig;
    private BitmapFont font;

    Texture defaultIB;
    Image image;
    private Stage stage;
    public IceBreakerView(RootApplication app, IceBreaker iceBreaker, IHoverableCallback callback, int positionInHardwareRig) {
        super(app);
        this.app = app;
        this.iceBreaker = iceBreaker;
        this.callback = callback;
        this.positionInHardwareRig = positionInHardwareRig;
        this.iceBreaker.setStrength(positionInHardwareRig);
        this.x = 150 + positionInHardwareRig*200;
        this.y = 250;
        this.width = 200;
        this.height = 300;
        defaultIB = new Texture("card_icons/default_ice_breaker.png");
        font = new BitmapFont();
        stage = new Stage(new ScreenViewport());

        image = new Image(defaultIB);
        image.setPosition(x, y);
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);

        stage.addActor(image);
    }
    @Override
    public void mouseMoved(int x, int y) {
        if (x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height) {
            // We're within the bounds of the ice breaker
            if (y < this.y + this.bottomHeight) {
                // we're in the bottom control section
                callback.onHover("Strength: " + iceBreaker.getStrength(), this.x + this.width, this.y + this.height);
            } else {
                // We're hovering over the main icon
                callback.onHover("Ice Breaker State", this.x + this.width, this.y + this.height);
            }
            showingData = true;
        } else {
            if (showingData)
                callback.onHoverEnd();
            showingData = false;
        }
    }
    public void updatePosition(int positionInHardwareRig) {
        this.positionInHardwareRig = positionInHardwareRig;
        // TODO Animate this move
        this.x = 150 + 200*positionInHardwareRig;

    }
    public void setIceBreaker(IceBreaker iceBreaker) {
        this.iceBreaker = iceBreaker;
    }
    public void draw(float dt) {
        stage.act();
        stage.draw();

        font.getData().setScale(2, 2);
        // Draw Bottom bar text
        font.draw(app.batch, "+", this.x + 160, this.y + 50);
        font.draw(app.batch, "D", this.x + 20, this.y + 50);

        font.getData().setScale(2.5f, 2.5f);
        font.draw(app.batch, "" + iceBreaker.getStrength(), this.x + this.width - 65, this.y + this.height - 45);
    }
}

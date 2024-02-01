package com.smith.netrunner.UI;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.RootApplication;

public class ImageButton extends BaseGameObject {
    private ClickCallbackListener callback;
    private int id;
    private Image image;
    private Stage stage;
    public ImageButton(RootApplication app, ClickCallbackListener callback, Image image) {
        super(app);
        this.callback = callback;
        this.image = image;
        this.image.setSize(180, 60);
        this.width = (int)image.getWidth();
        this.height = (int)image.getHeight();
        this.x = 10;
        this.y = 1080 - this.height;
        stage = new Stage();
        stage.addActor(this.image);
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        image.setPosition(x, y);
    }

    @Override
    public void draw(float dt) {
        super.draw(dt);
        if (!isActive) return;
        stage.draw();
    }

    @Override
    public void touchDown(int screenX, int screenY, int pointer, int button) {
        if(!isActive) return;
        super.touchDown(screenX, screenY, pointer, button);
        if (screenX > this.x && screenX < this.x + this.width && (1080-screenY) > this.y && (1080-screenY) < this.y + this.height)
            callback.onClick();
    }
}

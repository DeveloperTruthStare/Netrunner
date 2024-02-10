package com.smith.netrunner.HardwareRig;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.UIState;

public class HardwareView extends BaseGameObject {
    private Card card;

    private final Image defaultImage = new Image(new Texture("HardwareRig/emptySlot1x4.png"));
    private final Stage stage = new Stage();
    public HardwareView(RootApplication app) {
        super(app);
        this.card = null;
        stage.addActor(defaultImage);
    }
    public void setCard(Card card) {
        this.card = card;
    }

    public void reset() {
        this.card = null;
    }
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        defaultImage.setSize(width, height);
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        defaultImage.setPosition(x, y);
    }

    @Override
    public void touchDragged(int screenX, int screenY, int pointer) {
        super.mouseMoved(screenX, screenY);
        if (!isActive) return;
        if (screenX > this.x && screenX < this.x + this.width &&
                (1080-screenY) > this.y && (1080-screenY) < this.y + this.height)
            UIState.hoveredHardware = this;
    }

    public Card getInstalledHardware() {
        return this.card;
    }

    @Override
    public void draw(float delta) {
        super.draw(delta);
        if (!isActive) return;
        if (card != null)
            stage.draw();

        if (this == UIState.hoveredHardware) {

        }

    }
}

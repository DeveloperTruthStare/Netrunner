package com.smith.netrunner.HardwareRig;

import com.badlogic.gdx.graphics.Texture;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.RootApplication;

public class HardwareView extends BaseGameObject {
    public static Texture emptyHardwareImage;
    public static Texture hoveredHardwareImage;
    private boolean hovered = false;
    private Card card;

    public HardwareView(RootApplication app) {
        super(app);
        this.card = null;

        if (null == emptyHardwareImage)
            emptyHardwareImage = new Texture("HardwareRig/emptySlot1x4.png");
        if (null == hoveredHardwareImage)
            hoveredHardwareImage = new Texture("HardwareRig/hoveredEmptySlot1x4.png");
        this.setSize(emptyHardwareImage.getWidth(), emptyHardwareImage.getHeight());
    }
    public void setCard(Card card) {
        this.card = card;
    }
    @Override
    public void touchDragged(int screenX, int screenY, int pointer) {
        super.mouseMoved(screenX, screenY);
        if (!isActive) return;
        if (screenX > this.x && screenX < this.x + this.width && (1080-screenY) > this.y && (1080-screenY) < this.y + this.height)
            hovered = true;
        else
            hovered = false;
    }

    public boolean getHoveredStatus() {
        return hovered;
    }

    @Override
    public void draw(float delta) {
        super.draw(delta);
        if (!isActive) return;
        if (null == card) {

            if (hovered)
                app.batch.draw(hoveredHardwareImage, this.x, this.y);
            else
                app.batch.draw(emptyHardwareImage, this.x, this.y);

        }
    }
}

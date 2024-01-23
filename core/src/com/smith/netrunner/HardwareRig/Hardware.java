package com.smith.netrunner.HardwareRig;

import com.badlogic.gdx.graphics.Texture;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.RootApplication;

public class Hardware extends BaseGameObject {
    public static Texture emptyHardwareImage;
    public static Texture hoveredHardwareImage;
    private boolean hovered = false;

    public Hardware(RootApplication app) {
        super(app);
        if (null == emptyHardwareImage)
            emptyHardwareImage = new Texture("HardwareRig/emptySlot1x4.png");
        if (null == hoveredHardwareImage)
            hoveredHardwareImage = new Texture("HardwareRig/hoveredEmptySlot1x4.png");
        this.setSize(emptyHardwareImage.getWidth(), emptyHardwareImage.getHeight());
    }

    @Override
    public void touchDragged(int screenX, int screenY, int pointer) {
        super.mouseMoved(screenX, screenY);
        if (screenX > this.x && screenX < this.x + this.width && (1080-screenY) > this.y && (1080-screenY) < this.y + this.height)
            hovered = true;
        else
            hovered = false;
    }

    @Override
    public void touchUp(int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY, pointer, button);
        hovered = false;
    }

    public boolean getHoveredStatus() {
        return hovered;
    }
    public void draw(float delta) {
        if (hovered)
            app.batch.draw(hoveredHardwareImage, this.x, this.y);
        else
            app.batch.draw(emptyHardwareImage, this.x, this.y);
    }
}

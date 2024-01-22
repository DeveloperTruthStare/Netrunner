package com.smith.netrunner.HardwareRig;

import com.badlogic.gdx.graphics.Texture;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.RootApplication;

public class HardwareRig extends BaseGameObject {
    public Hardware[] hardwares = new Hardware[6];
    private Texture emptyHardwareImage;
    private Texture hoveredEmptyHardwareImage;
    private final int imageWidth = 330, imageHeight = 210;
    private final int topRightX = 13, topRightY = 685;
    private int hoveredHardware = -1;
    public HardwareRig(RootApplication app) {
        super(app);
        emptyHardwareImage = new Texture("empty_hardware_slot.png");
        hoveredEmptyHardwareImage = new Texture("emptyHardwareHovered.png");
    }
    public void draw(float delta) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 2; ++j) {
                if (hardwares[j * 3 + i] == null) {
                    // Draw empty hardware image
                    if (hoveredHardware == j * 3 + i)
                        app.batch.draw(hoveredEmptyHardwareImage, topRightX + j * imageWidth, topRightY - i * imageHeight);
                    else
                        app.batch.draw(emptyHardwareImage, topRightX + j * imageWidth, topRightY - i * imageHeight);
                }
            }
        }
    }

    @Override
    public void mouseMoved(int screenX, int screenY) {
        super.mouseMoved(screenX, screenY);
        System.out.println(screenX + ", " + (screenY));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 2; ++j) {
                if (    screenX > topRightX + j * imageWidth &&
                        screenX < topRightX + (j+1) * imageWidth &&
                        (screenY) < topRightY - (i-1) * imageHeight &&
                        (screenY) > topRightY - (i) * imageHeight) {
                    hoveredHardware = j * 3 + i;
                    return;
                }
            }
        }
        hoveredHardware = -1;
    }
}

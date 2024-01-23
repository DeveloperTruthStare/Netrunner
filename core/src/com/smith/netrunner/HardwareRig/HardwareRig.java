package com.smith.netrunner.HardwareRig;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.RootApplication;

import java.util.ArrayList;

public class HardwareRig extends BaseGameObject {
    protected int hardwareLength = 4;
    private ArrayList<Hardware> baseHardwares;

    private final int imageHeight = 200;
    private final int bottomRightX = 5, bottomRightY = 180;
    private int hoveredHardware = -1;
    private final Image hardwareRig;
    private final Stage stage;
    public HardwareRig(RootApplication app) {
        super(app);
        baseHardwares = new ArrayList<>();
        for(int i = 0; i < hardwareLength; ++i) {
            Hardware hw = new Hardware(app);
            hw.setPosition(bottomRightX, bottomRightY + (i * imageHeight));
            baseHardwares.add(hw);
            addListener(hw);
        }
        hardwareRig = new Image(new Texture("HardwareRig/hardwareArea1x4.png"));
        hardwareRig.setPosition(bottomRightX, bottomRightY);
        stage = new Stage();
        stage.addActor(hardwareRig);
    }
    public void draw(float delta) {
        stage.draw();
        for(Hardware hw : baseHardwares)
            hw.draw(delta);
    }

    public boolean installOnHovered(Hardware hardware) {
        int hoveredHardware = -1;
        for(int i = 0; i < hardwareLength; ++i) {
            if (baseHardwares.get(i).getHoveredStatus()) {
                hoveredHardware = i;
                break;
            }
        }
        if (-1 == hoveredHardware)
            return false;
        hardware.setPosition(bottomRightX, bottomRightY + hoveredHardware * imageHeight);
        this.baseHardwares.set(hoveredHardware, hardware);

        return true;
    }

    @Override
    public void mouseMoved(int screenX, int screenY) {
        super.mouseMoved(screenX, screenY);
        hoveredHardware = -1;
    }
}

package com.smith.netrunner.HardwareRig;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.RootApplication;

import java.util.ArrayList;

public class HardwareRig extends BaseGameObject {
    protected int hardwareLength = 4;
    private final ArrayList<HardwareView> baseHardwareViews;

    private final int imageHeight = 200;
    private final int bottomRightX = 5, bottomRightY = 180;
    private final Image hardwareRig;
    private final Stage stage;
    public HardwareRig(RootApplication app) {
        super(app);
        baseHardwareViews = new ArrayList<>();
        for(int i = 0; i < hardwareLength; ++i) {
            HardwareView hw = new HardwareView(app);
            hw.setPosition(bottomRightX, bottomRightY + (i * imageHeight));
            baseHardwareViews.add(hw);
            addChild(hw);
        }
        hardwareRig = new Image(new Texture("HardwareRig/hardwareArea1x4.png"));
        hardwareRig.setPosition(bottomRightX, bottomRightY);
        stage = new Stage();
        stage.addActor(hardwareRig);
    }

    @Override
    public void draw(float delta) {
        stage.draw();
        super.draw(delta);
    }
    public int getHoveredHardware() {
        int hoveredHardware = -1;
        for(int i = 0; i < hardwareLength; ++i) {
            if (baseHardwareViews.get(i).getHoveredStatus()) {
                hoveredHardware = i;
                break;
            }
        }
        return hoveredHardware;
    }
    public boolean installOnHovered(Card hardware) {
        // Assume
        // - valid hardware card is provided
        // - Hovered isn't already occupied
        int hoveredHardware = getHoveredHardware();
        if (-1 == hoveredHardware)
            return false;
        baseHardwareViews.get(hoveredHardware).setCard(hardware);

        return true;
    }
}

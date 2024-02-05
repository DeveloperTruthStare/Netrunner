package com.smith.netrunner.HardwareRig;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.RootApplication;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class HardwareRig extends BaseGameObject {
    protected int hardwareLength = 3;
    private final int spaceBetween = 60;
    private final int hardwareHeight = 140;
    private final int hardwareWidth = 240;
    private final Point2D hardwareStartPos = new Point2D.Float(220, 360);



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
            hw.setPosition((int)hardwareStartPos.getX(), (int)hardwareStartPos.getY() + (i * (hardwareHeight + spaceBetween)));
            hw.setSize(hardwareWidth, hardwareHeight);
            baseHardwareViews.add(hw);
            addChild(hw);
        }
        hardwareRig = new Image(new Texture("HardwareRig/hardwareRig1x3.png"));
        hardwareRig.setPosition(0, 0);
        stage = new Stage();
        stage.addActor(hardwareRig);
    }
    public void reset() {
        for(HardwareView hv : baseHardwareViews) {
            hv.reset();
        }
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
        if (hardware.cardType != Card.CardType.HARDWARE) return false;
        int hoveredHardware = getHoveredHardware();
        if (-1 == hoveredHardware) return false;
        if (null != baseHardwareViews.get(hoveredHardware).getInstalledHardware()) return false;
        baseHardwareViews.get(hoveredHardware).setCard(hardware);

        return true;
    }
}

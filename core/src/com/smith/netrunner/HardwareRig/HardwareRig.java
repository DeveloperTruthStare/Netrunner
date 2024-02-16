package com.smith.netrunner.HardwareRig;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.UIState;

import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

public class HardwareRig extends BaseGameObject {
    protected int hardwareLength = 3;
    private final ArrayList<HardwareView> baseHardwareViews;
    private final ArrayList<HardwareView> extendedHardwareViews;
    public HardwareRig(RootApplication app) {
        super(app);
        baseHardwareViews = new ArrayList<>();
        extendedHardwareViews = new ArrayList<>();
        for(int i = 0; i < hardwareLength; ++i) {
            HardwareView hw = new HardwareView(app);
            HardwareView extended = new HardwareView(app);

            Vector2 hardwareStartPos = new Vector2(220, 360);
            int hardwareHeight = 140;
            int spaceBetween = 60;
            int hardwareWidth = 240;

            hw.setPosition((int) hardwareStartPos.x, (int) hardwareStartPos.y + (i * (hardwareHeight + spaceBetween)));
            hw.setSize(hardwareWidth, hardwareHeight);
            baseHardwareViews.add(hw);
            addChild(hw);

            extended.setPosition(((int)hardwareStartPos.x + hardwareWidth + 40), (int)hardwareStartPos.y + (i * (hardwareHeight + spaceBetween)));
            extended.setSize(hardwareWidth, hardwareHeight);
            extended.setActive(false);
            extendedHardwareViews.add(extended);
            addChild(extended);
        }

        Image hardwareRig = new Image(new Texture("HardwareRig/hardwareRig1x3.png"));
        hardwareRig.setPosition(0, 0);

    }
    public int getNumberOfInstalledHardware() {
        int installedHardware = 0;
        for(HardwareView hv : baseHardwareViews) {
            if (hv.getInstalledHardware() != null && hv.getInstalledHardware().cardSubType == Card.CardSubType.ICE_BREAKER) {
                installedHardware++;
            }
        }
        for(HardwareView hv : extendedHardwareViews) {
            if (hv.getInstalledHardware() != null && hv.getInstalledHardware().cardSubType == Card.CardSubType.ICE_BREAKER) {
                installedHardware++;
            }
        }
        return installedHardware;
    }
    public void reset() {
        for(HardwareView hv : baseHardwareViews) {
            hv.reset();
        }
        for(HardwareView hv : extendedHardwareViews) {
            hv.reset();
        }
    }
    @Override
    public void draw(float delta) {
        super.draw(delta);
    }
    public boolean installOnHovered(Card hardware) {
        if (hardware.cardType != Card.CardType.HARDWARE) return false;
        if (null == UIState.hoveredHardware) return false;
        if (null != UIState.hoveredHardware.getInstalledHardware()) return false;
        if (hardware.cardSubType == Card.CardSubType.CONSOLE) {
            if (!baseHardwareViews.contains(UIState.hoveredHardware)) {
                return false;       // We cannot install a console on extended view
            }
            extendedHardwareViews.get(baseHardwareViews.indexOf(UIState.hoveredHardware)).setActive(true);
            UIState.hoveredHardware.setPositionConsole();
        }
        UIState.hoveredHardware.setCard(hardware);
        return true;
    }
}

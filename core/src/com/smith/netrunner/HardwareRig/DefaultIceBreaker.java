package com.smith.netrunner.HardwareRig;

import com.badlogic.gdx.graphics.Texture;
import com.smith.netrunner.RootApplication;

public class DefaultIceBreaker extends Hardware {
    private static Texture defaultIceBreaker;
    public DefaultIceBreaker(RootApplication app) {
        super(app);
        if (null == defaultIceBreaker)
            defaultIceBreaker = new Texture("HardwareRig/defaultIceBreaker.png");
        this.setSize(defaultIceBreaker.getWidth(), defaultIceBreaker.getHeight());
    }

    @Override
    public void draw(float delta) {
        app.batch.draw(defaultIceBreaker, this.x, this.y);
    }
}

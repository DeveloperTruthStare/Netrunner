package com.smith.netrunner.Corporation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.Target;
import com.smith.netrunner.UI.ClickCallbackListener;

public class ResearchAndDevelopment extends RunTarget {
    Target target;
    public ResearchAndDevelopment(RootApplication app, ClickCallbackListener callback) {
        super(app, callback);
        serverImage.setDrawable(new SpriteDrawable(new Sprite(new Texture("BattleScreen/corporation/serverDeckIcon.png"))));
        setSize(600, 200);

        target = new Target(app);
        target.setPosition(1320, 250);
        target.setActive(false);
        addChild(target);
    }


    @Override
    public void setAction(String action) {
        super.setAction(action);
        if(!action.equals("RUNNING_0")) {
            target.setActive(false);
            return;
        }
        target.LerpSize(1000, 200, 0.25f);
        target.setActive(true);

    }
}

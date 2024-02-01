package com.smith.netrunner.Corporation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.UI.ClickCallbackListener;

import java.util.ArrayList;

public class RunTarget extends BaseGameObject {
    public class Ice {
        public boolean isRevealed = false;
    }
    public ArrayList<Ice> installedIce;
    protected Image serverImage;
    protected Stage stage;
    public boolean showOutline = false;
    public ShapeRenderer shapeRenderer;
    protected ClickCallbackListener clickCallback;
    public RunTarget(RootApplication app, ClickCallbackListener clickCallback) {
        super(app);
        this.clickCallback = clickCallback;
        installedIce = new ArrayList<>();
        serverImage = new Image( new Texture("HardwareRig/emptySlot1x4.png"));
        this.stage = new Stage();
        stage.addActor(serverImage);
        shapeRenderer = new ShapeRenderer();
        setSize((int)serverImage.getWidth(), (int)serverImage.getHeight());
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        serverImage.setSize(width, height);
    }
    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        serverImage.setPosition(x, y);
    }

    @Override
    public void draw(float dt) {
        if (!isActive) return;
        app.batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (!isHovering) {
            shapeRenderer.setColor(0, 0, 0, 1);
        } else {
            shapeRenderer.setColor(1, 1, 1, 1);
        }
        if (showOutline)
            shapeRenderer.rect(this.x, this.y, this.width, this.height);
        shapeRenderer.end();
        app.batch.begin();

        stage.draw();

        super.draw(dt);
    }
    public void setAction(String action) {
        showOutline = action.equals("RUNNING");
    }

    @Override
    public void onClick() {
        super.onClick();
        clickCallback.onClick();
    }
}

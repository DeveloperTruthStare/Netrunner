package com.smith.netrunner.Corporation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.Target;
import com.smith.netrunner.UI.ClickCallbackListener;

import java.util.ArrayList;

public class RunTarget extends BaseGameObject {

    public enum ServerType {
        RND, BANK_RECORDS, HONEY_POT, KEY_DATABASE
    }
    public class Ice {
        public boolean isRevealed = false;
    }
    public ServerType serverType = ServerType.BANK_RECORDS;
    public ArrayList<Ice> installedIce;
    protected Image serverImage;
    protected Stage stage;
    public boolean showOutline = false;
    public ShapeRenderer shapeRenderer;
    protected ClickCallbackListener clickCallback;
    protected Target target;

    public RunTarget(RootApplication app, ClickCallbackListener clickCallback) {
        super(app);
        this.clickCallback = clickCallback;
        installedIce = new ArrayList<>();
        serverImage = new Image( new Texture("HardwareRig/emptySlot1x4.png"));
        this.stage = new Stage();
        stage.addActor(serverImage);
        shapeRenderer = new ShapeRenderer();
        setSize((int)serverImage.getWidth(), (int)serverImage.getHeight());

        target = new Target(app);
        target.setPosition(1320, 250);
        target.setActive(false);
        addChild(target);

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
    public void setShowOutline(boolean value) {
        showOutline = value;
    }
    private int currentIce = -1;
    public void targetServer() {
        showTarget();
        currentIce = 0;
    }
    public void unTarget() {
        target.setActive(false);
    }
    public Ice getActiveIce() {
        if (installedIce.size() > currentIce) {
            return installedIce.get(currentIce);
        }
        return null;
    }
    public boolean hasAccess() {
        return currentIce >= installedIce.size();
    }
    private void showTarget() {
        target.LerpSize(1000, 200, 0.25f);
        target.setActive(true);
    }
    @Override
    public void onClick() {
        super.onClick();
        clickCallback.onClick();
    }
}

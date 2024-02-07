package com.smith.netrunner.UI;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.GameData.Reward;
import com.smith.netrunner.RootApplication;

import java.util.ArrayList;

public class RewardWindow extends BaseGameObject {
    private MyShapeRenderer shapeRenderer;
    private ArrayList<Button> acceptRewardsButtons;
    public RewardWindow(RootApplication app, BaseGameObject parent) {
        super(app, parent);
        shapeRenderer = new MyShapeRenderer();
        acceptRewardsButtons = new ArrayList<>();
    }

    @Override
    public void draw(float dt) {
        if (!isActive) return;
        app.batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.1f, 0.1f, 0.3f, 1);
        shapeRenderer.roundedRect(this.x - this.width/2, this.y - this.height/2, this.width, this.height, 20);
        shapeRenderer.end();

        app.batch.begin();
        super.draw(dt);
        for (int i = 0; i < acceptRewardsButtons.size(); ++i) {
            acceptRewardsButtons.get(i).setPosition(this.x - (this.width-100)/2, this.y + (this.height/2) - (50 + 75 * (i+1)));
        }
    }
    public void addButton(Button button) {
        this.acceptRewardsButtons.add(button);
        addChild(button);
        button.setPosition(this.x - (this.width-100)/2, this.y + (this.height/2) - (50 + (75 * children.size())));
        button.setSize(this.width-100, 50);
    }
    public void removeButton(Button button) {
        this.acceptRewardsButtons.remove(button);
        removeChild(button);

    }
}

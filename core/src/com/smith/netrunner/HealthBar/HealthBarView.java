package com.smith.netrunner.HealthBar;

import com.badlogic.gdx.graphics.Texture;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.RootApplication;

public class HealthBarView extends BaseGameObject {
    private int health;
    private final Texture playerHealthBar;
    private final Texture heartIcon;
    private final Texture brokenHeartIcon;


    public HealthBarView(RootApplication app) {
        super(app);
        this.app = app;
        this.health = 10;
        heartIcon = new Texture("health_icon.png");
        brokenHeartIcon = new Texture("missing_health_icon.png");
        playerHealthBar = new Texture("playerHealthBar.png");
    }
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void draw(float dt) {
        app.batch.draw(playerHealthBar, 20, 1000);
        for(int i = 0; i < 10; ++i) {
            Texture toDraw = brokenHeartIcon;
            if (i >= 10 - health)
                app.batch.draw(heartIcon, 35 + (i*44 ), 1010, 40, 40);
            else
                app.batch.draw(heartIcon, 35 + (i*44 ), 1010, 40, 40);
        }

    }
}

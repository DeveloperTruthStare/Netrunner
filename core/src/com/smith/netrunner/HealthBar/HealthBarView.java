package com.smith.netrunner.HealthBar;

import com.badlogic.gdx.graphics.Texture;
import com.smith.netrunner.RootApplication;

public class HealthBarView {
    private RootApplication app;
    private int health;
    private Texture playerHealthBar;
    private Texture heartIcon;
    private Texture brokenHeartIcon;


    public HealthBarView(RootApplication app) {
        this.app = app;
        this.health = 10;
        heartIcon = new Texture("health_icon.png");
        brokenHeartIcon = new Texture("missing_health_icon.png");
        playerHealthBar = new Texture("playerHealthBar.png");
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public void draw(float dt) {
        app.batch.draw(playerHealthBar, 20, 1000);
        for(int i = 0; i < 10; ++i) {
            Texture toDraw = brokenHeartIcon;
            if (i >= 10 - health)
                toDraw = heartIcon;
            app.batch.draw(toDraw, 35 + (i*44 ), 1010, 40, 40);
        }

    }
}

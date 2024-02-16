package com.smith.netrunner.HealthBar;

import com.badlogic.gdx.graphics.Texture;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.GameData.Runner;
import com.smith.netrunner.GameData.World;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.Screens.GameScreen;

public class HealthBarView extends BaseGameObject {
    private final Texture playerHealthBar;
    private final Texture heartIcon;
    private final Texture brokenHeartIcon;
    private final GameScreen gameManager;

    public HealthBarView(RootApplication app, GameScreen parent) {
        super(app, parent);
        gameManager = parent;
        this.app = app;
        heartIcon = new Texture("health_icon.png");
        brokenHeartIcon = new Texture("missing_health_icon.png");
        playerHealthBar = new Texture("playerHealthBar.png");
    }

    @Override
    public void draw(float dt) {
        app.batch.draw(playerHealthBar, 20, 1000);
        for(int i = 0; i < 10; ++i) {
            if (i >= 10 - gameManager.world.player.health)
                app.batch.draw(heartIcon, 35 + (i*44 ), 1010, 40, 40);
            else
                app.batch.draw(brokenHeartIcon, 35 + (i*44 ), 1010, 40, 40);
        }

    }
}

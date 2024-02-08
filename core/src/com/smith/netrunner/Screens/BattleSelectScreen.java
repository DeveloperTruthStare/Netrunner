package com.smith.netrunner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.BattleSelectScreen.WorldView;
import com.smith.netrunner.GameData.Region;
import com.smith.netrunner.InfoWindow.BattleInfoWindow;
import com.smith.netrunner.Corporation.Corporation;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.UI.ClickCallbackListener;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class BattleSelectScreen extends BaseGameObject  {

    private final MyShapeRenderer shapeRenderer;
    private final GameScreen gameManager;
    private final BattleInfoWindow infoWindow;


    public BattleSelectScreen(RootApplication app, GameScreen gameScreen) {
        super(app, gameScreen);
        gameManager = gameScreen;

        WorldView worldView = new WorldView(app, gameManager);
        worldView.setPosition(100, 50);
        addChild(worldView);
        shapeRenderer = new MyShapeRenderer();

        infoWindow = new BattleInfoWindow(app, this, new ClickCallbackListener() {
            @Override
            public void onClick() {
                gameManager.attackSelected();
            }
        });
        addChild(infoWindow);
    }

    @Override
    public void draw(float delta) {
        if (!isActive) return;

        app.batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor((float)163/255, (float)168/255, (float)181/255, 1);
        shapeRenderer.roundedRect(75, 25, 715, 815, 20);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.roundedRect(85, 35, 695, 795, 10);
        shapeRenderer.end();
        app.batch.begin();
        super.draw(delta);
        drawText("Money: " + gameManager.world.player.money, 960, 1050, ALIGNMENT.CENTER);

    }
    @Override
    public void touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        if (!isActive) return;
        Region region = gameManager.world.getCurrentRegion();
        for(int i = 0; i < region.worldSize; ++i) {
            for(int j = 0; j < region.worldSize; ++j) {
                float xPos = 100 + 90*i;
                float bottomY = 100 + 100 * j;
                if (i % 2 == 1) bottomY -= 50;
                if (contains(screenX, screenY,(int) xPos, (int)bottomY)) {


                    if (region.corps[i][j].revealed) {
                        gameManager.setPlayerPosition(new Vector2(i, j));
                    }
                    infoWindow.setCorporation(region.corps[i][j]);
                }
            }
        }
    }

    public boolean contains(int screenX, int screenY, int x, int y) {
        screenY = 1080 - screenY;

        float left = x + 28;
        float right = x + 115 - 28;
        float bottom = y + 10;
        float top = y + 115 - 10;

        if (screenY > bottom && screenY < top) {

            if (screenX > left && screenX < right) {
                return true;
            }
            if (screenX > x + 5 && screenX < left) {
                float topLine = 2 * (screenX - x) + (y + 115/2);
                float bottomLine = -2 * (screenX - x) + (y + 115/2);
                return screenY > bottomLine && screenY < topLine;
            }
        else if (screenX > right && screenX < x + 115 - 5) {
            float bottomLine = 2 * (screenX - (x+115)) + (y + 115/2);
            float topLine = -2 * (screenX - (x + 115)) + (y + 115/2);
            return screenY > bottomLine && screenY < topLine;
        }
        }
        return false;
    }
}

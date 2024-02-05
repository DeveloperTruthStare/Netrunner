package com.smith.netrunner.Screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.BattleInfoWindow;
import com.smith.netrunner.Corporation.Corporation;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.UI.ClickCallbackListener;
import com.smith.netrunner.World;

import java.awt.Point;
import java.awt.geom.Point2D;

public class BattleSelectScreen extends BaseGameObject  {

    private final MyShapeRenderer shapeRenderer;
    private final GameScreen gameManager;
    private final BattleInfoWindow infoWindow;
    private final ClickCallbackListener attackClick = new ClickCallbackListener() {
        @Override
        public void onClick() {
            System.out.println("Attacking " + gameManager.world.playerPosition);
            gameManager.attackSelected();
        }
    };
    public BattleSelectScreen(RootApplication app, GameScreen gameScreen) {
        super(app, gameScreen);
        gameManager = gameScreen;
        shapeRenderer = new MyShapeRenderer();
        infoWindow = new BattleInfoWindow(app, this, attackClick);
        addChild(infoWindow);
    }

    @Override
    public void draw(float delta) {
        super.draw(delta);
        if (!isActive) return;
        Corporation[][] map = getMap();
        Point2D pos = gameManager.world.playerPosition;

        app.batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.roundedRect(50, 50, 975, 975, 10);


        for(int i = 0; i < gameManager.world.worldSize; ++i) {
            for(int j = 0; j < gameManager.world.worldSize; ++j) {
                shapeRenderer.setColor(0.2f, 0.2f, 0.2f, 1);
                if (i == (int) pos.getX() && j == (int) pos.getY()) {
                    shapeRenderer.setColor(1, 0, 0, 1);
                } else if (map[i][j].battled) {
                    shapeRenderer.setColor(0, 1, 0, 1);
                } else if (map[i][j].revealed) {
                    if (map[i][j].isBoss) {
                        shapeRenderer.setColor(1, 0, 1, 1);
                    } else {
                        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1);
                    }
                }
                shapeRenderer.roundedRect(i * 125+100, j * 125+100, 115, 115, 10);

                if (map[i][j].revealed && (!map[i][j].battled) && Corporation.images.containsKey(map[i][j].type)) {
                    shapeRenderer.end();
                    app.batch.begin();
                    app.batch.draw(Corporation.images.get(map[i][j].type), i * 125+100, j * 125 + 100);
                    app.batch.end();
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                }
            }
        }
        shapeRenderer.end();
        app.batch.begin();
    }

    @Override
    public void keyDown(int keycode) {
        super.keyDown(keycode);
        if (keycode == Input.Keys.W) {
            moveUp();
        } else if (keycode == Input.Keys.S) {
            moveDown();
        } else if (keycode == Input.Keys.A) {
            moveLeft();
        } else if (keycode == Input.Keys.D) {
            moveRight();
        }
    }
    private void moveUp() {
        if (gameManager.world.moveUp()) {
            infoWindow.setCorporation(gameManager.world.getCurrentCorporation());
        }
    }
    private void moveDown() {
        if (gameManager.world.moveDown()) {
            infoWindow.setCorporation(gameManager.world.getCurrentCorporation());
        }
    }
    private void moveLeft() {
        if (gameManager.world.moveLeft()) {
            infoWindow.setCorporation(gameManager.world.getCurrentCorporation());
        }
    }
    private void moveRight() {
        if (gameManager.world.moveRight()) {
            infoWindow.setCorporation(gameManager.world.getCurrentCorporation());
        }
    }
    public Corporation[][] getMap() {
        return gameManager.world.corps;
    }

    @Override
    public void touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        if (!isActive) return;
        int x = (screenX - 100) / 125;
        int y = ((1080 - screenY) - 100) / 125;
        if (x > gameManager.world.worldSize-1 || y > gameManager.world.worldSize-1 || x < 0 || y < 0) return;
        if (gameManager.world.corps[x][y].revealed) {
            gameManager.world.playerPosition = new Point(x, y);
        }
        // Set Display (x, y)
        infoWindow.setCorporation(gameManager.world.corps[x][y]);

    }
}

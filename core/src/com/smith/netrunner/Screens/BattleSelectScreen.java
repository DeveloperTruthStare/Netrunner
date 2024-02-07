package com.smith.netrunner.Screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.smith.netrunner.BaseGameObject;
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
    private Animation<TextureRegion> playerIndicatorAnim;
    private float stateTime = 0.0f;
    ArrayList<Texture> icons;
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

        Texture playerIndicator = new Texture("BattleSelect/playerIndicatorSpriteSheet.png");
        TextureRegion[][] tmp = TextureRegion.split(playerIndicator, 115, 115);
        TextureRegion[] sprites = new TextureRegion[16];
        int index = 0;
        for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 4; ++j) {
                sprites[index++] = tmp[i][j];
            }
        }
        playerIndicatorAnim = new Animation<>((float)1/24, sprites);

        addChild(infoWindow);
        icons = new ArrayList<>();
        icons.add(new Texture("BattleScreen/tiles/hexEnabled.png"));
        icons.add(new Texture("BattleScreen/tiles/hexDisabled.png"));
        icons.add(new Texture("BattleScreen/tiles/revealedFightHex.png"));
        icons.add(new Texture("BattleScreen/tiles/playerLocation.png"));
        icons.add(new Texture("BattleScreen/tiles/difficultEncounterIcon.png"));
        icons.add(new Texture("BattleScreen/tiles/eventIcon.png"));
    }

    @Override
    public void draw(float delta) {
        super.draw(delta);
        if (!isActive) return;

        stateTime += delta;

        app.batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.roundedRect(90, 40, 675, 815, 20);
        shapeRenderer.end();
        app.batch.begin();

        Corporation[][] map = getMap();
        Point2D pos = gameManager.world.playerPosition;

        for(int i = 0; i < gameManager.world.worldSize; ++i) {
            for(int j = 0; j < gameManager.world.worldSize; ++j) {


                int x = 100 + 90 * i;
                int y = 100 + 100 * j;
                if (i % 2 == 1)
                    y -= 50;

                Texture toDraw = icons.get(0);
                if (map[i][j].revealed) {
                    if (!map[i][j].battled) {
                        if (map[i][j].type == Corporation.CORPORATION_TYPE.EVENT) {
                            toDraw = icons.get(5);
                        } else {
                            switch(map[i][j].difficulty) {
                                case 0:
                                case 1:
                                    toDraw = icons.get(2);
                                    break;
                                case 2:
                                    toDraw = icons.get(4);
                                    break;
                            }
                        }
                    }
                } else {
                    toDraw = icons.get(1);
                }
                app.batch.draw(toDraw, x, y, 115, 115);
            }
        }
        int playerX = (int)pos.getX() * 90 + 100;
        int playerY = (int)pos.getY() * 100 + 100;
        if (pos.getX() % 2 == 0)
            playerY += 50;
        app.batch.draw(playerIndicatorAnim.getKeyFrame(stateTime, true), playerX, playerY);
        drawText("Money: " + gameManager.world.player.money, 960, 1050, ALIGNMENT.CENTER);
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
    @Override
    public void touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        if (!isActive) return;
        for(int i = 0; i < gameManager.world.worldSize; ++i) {
            for(int j = 0; j < gameManager.world.worldSize; ++j) {
                float xPos = 100 + 90*i;
                float bottomY = 100 + 100 * j;
                if (i % 2 == 1) bottomY -= 50;
                if (contains(screenX, screenY,(int) xPos, (int)bottomY)) {


                    if (gameManager.world.corps[i][j].revealed) {
                        gameManager.world.playerPosition = new Point(i, j);
                    }
                    infoWindow.setCorporation(gameManager.world.corps[i][j]);
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

    public Corporation[][] getMap() {
        return gameManager.world.corps;
    }
}

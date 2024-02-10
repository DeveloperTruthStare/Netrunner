package com.smith.netrunner.BattleSelectScreen;

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
import com.badlogic.gdx.math.Vector2;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.Corporation.Corporation;
import com.smith.netrunner.GameData.Region;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.Screens.GameScreen;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class WorldView extends BaseGameObject {

    public enum WorldState {
        IDLE, ZOOMING_OUT, MOVING_1, REVEALING, PLAYER_MOVE_ANIM, MOVING_2, ZOOMING_IN
    }
    public WorldState state;

    private final FrameBuffer frameBuffer;
    private final OrthographicCamera camera;
    private final SpriteBatch spriteBatch;
    private final static int VIEW_WIDTH = 665, VIEW_HEIGHT = 765;
    private final GameScreen gameManager;
    ArrayList<Texture> icons;
    private final Animation<TextureRegion> playerIndicatorAnim;
    private float stateTime = 0.0f;
    private final float cameraZoomSpeed = 1.5f;

    private Vector2 offSet;
    private final float moveSpeed = 200;
    private Vector2 targetPoint;


    public WorldView(RootApplication app, GameScreen gameManager) {
        super(app);
        this.gameManager = gameManager;

        this.state = WorldState.IDLE;

        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, VIEW_WIDTH, VIEW_HEIGHT, false);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEW_WIDTH, VIEW_HEIGHT);

        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.combined);

        Texture playerIndicator = new Texture("BattleSelect/playerIndicatorSpriteSheet.png");
        TextureRegion[][] tmp = TextureRegion.split(playerIndicator, 115, 115);
        TextureRegion[] sprites = new TextureRegion[16];
        int index = 0;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                sprites[index++] = tmp[i][j];
            }
        }
        playerIndicatorAnim = new Animation<>((float) 1 / 24, sprites);


        icons = new ArrayList<>();
        icons.add(new Texture("BattleScreen/tiles/hexEnabled.png"));
        icons.add(new Texture("BattleScreen/tiles/hexDisabled.png"));
        icons.add(new Texture("BattleScreen/tiles/hexFight.png"));
        icons.add(new Texture("BattleScreen/tiles/playerLocation.png"));
        icons.add(new Texture("BattleScreen/tiles/hexHard.png"));
        icons.add(new Texture("BattleScreen/tiles/hexEvent.png"));
        icons.add(new Texture("BattleScreen/tiles/unaccessableHex.png"));

        reset();
    }

    public void reset() {
        offSet = new Vector2(0, 0);
    }

    private float timeSinceStateChange = 0.0f;
    @Override
    public void draw(float delta) {
        super.draw(delta);
        if (!isActive) return;

        timeSinceStateChange += delta;
        stateTime += delta;

        app.batch.end();

        frameBuffer.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();

        switch(state) {
            case IDLE:
                idle(delta);
                break;
            case ZOOMING_OUT:
                zoomOut(delta);
                break;
            case MOVING_1:
                move1(delta);
                break;
            case REVEALING:
                revealArea(delta);
                break;
            case PLAYER_MOVE_ANIM:
                playerMoving(delta);
                break;
            case MOVING_2:
                move2(delta);
                break;
            case ZOOMING_IN:
                zoomIn(delta);
                break;
        }
        drawStatic(delta);

        spriteBatch.end();
        frameBuffer.end();
        app.batch.begin();
        app.batch.draw(frameBuffer.getColorBufferTexture(), this.x, this.y + VIEW_HEIGHT, VIEW_WIDTH, -VIEW_HEIGHT);
    }
    private void idle(float delta) {

    }
    private void zoomOut(float delta) {
        if (timeSinceStateChange > 1) {
            // Choose the target point
            targetPoint = new Vector2(90 * 5, 100 * 2);
            nextState();
        }
        camera.zoom += cameraZoomSpeed * delta;
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
    }
    private void move1(float delta) {
        if (Math.abs(targetPoint.x - offSet.x) < 10 && Math.abs(targetPoint.y - offSet.y) < 10) {
            offSet = targetPoint;
            // Choose next target point
            targetPoint = new Vector2(0, 0);
            nextState();
        }
        if (offSet.x < targetPoint.x)
            offSet.x += moveSpeed * delta;
        if (offSet.y < targetPoint.y)
            offSet.y += moveSpeed * delta;
    }
    private void revealArea(float delta) {
        nextState();
    }
    private void playerMoving(float delta) {
        nextState();
    }
    private void move2(float delta) {
        if (Math.abs(targetPoint.x - offSet.x) < 10 && Math.abs(targetPoint.y - offSet.y) < 10) {
            // Choose next target point
            nextState();
        }
        if (offSet.x > targetPoint.x)
            offSet.x -= moveSpeed * delta;
        if (offSet.y > targetPoint.y)
            offSet.y -= moveSpeed * delta;

    }
    private void zoomIn(float delta) {
        if (timeSinceStateChange > 1) {
            // Choose the target point
            targetPoint = new Vector2(90 * 5, 100 * 2);
            nextState();
        }
        camera.zoom -= cameraZoomSpeed * delta;
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

    }
    private void drawStatic(float delta) {
        Region region = gameManager.world.getCurrentRegion();
        Vector2 pos = region.playerPosition;
        Corporation[][] map = region.corps;

        int start = -2, end = 2;
        if (this.state != WorldState.IDLE) {
            start -= 20;
            end += 20;
        }

        for(int i = start; i < region.worldSize+end; ++i) {
            for(int j = start; j < region.worldSize+end; ++j) {
                int x = 90 * i;
                int y = 100 * j + 50;
                if (Math.abs(i) % 2 == 1)
                    y -= 50;
                Texture toDraw = icons.get(1);

                if (i >= 0 && i < region.worldSize && j >= 0 && j < region.worldSize) {

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
                        } else {
                            toDraw = icons.get(0);
                        }
                    } else {
                        toDraw = icons.get(6);
                    }
                } else {
                    if (this.state == WorldState.IDLE && (i < -1 || j < -1 || i > region.worldSize || j > region.worldSize))
                        continue;
                }
                spriteBatch.draw(toDraw, x + offSet.x, y + offSet.y, 115, 115);

            }
        }
        int playerX = (int)pos.x * 90;
        int playerY = (int)pos.y * 100 + 50;
        if (pos.x % 2 == 0)
            playerY += 50;
        spriteBatch.draw(playerIndicatorAnim.getKeyFrame(stateTime, true), playerX + offSet.x, playerY + offSet.y);
    }
    @Override
    public void keyDown(int keycode) {
        super.keyDown(keycode);
        if (keycode == Input.Keys.SPACE) {
            nextState();
        }
    }
    private void nextState() {
        timeSinceStateChange = 0;
        switch(this.state) {
            case ZOOMING_OUT:
                this.state = WorldState.MOVING_1;
                break;
            case MOVING_1:
                this.state = WorldState.REVEALING;
                break;
            case REVEALING:
                this.state = WorldState.PLAYER_MOVE_ANIM;
                break;
            case PLAYER_MOVE_ANIM:
                this.state = WorldState.MOVING_2;
                break;
            case MOVING_2:
                this.state = WorldState.ZOOMING_IN;
                break;
            case ZOOMING_IN:
                this.state = WorldState.IDLE;
                break;
            case IDLE:
                this.state = WorldState.ZOOMING_OUT;
                break;
        }
    }
}

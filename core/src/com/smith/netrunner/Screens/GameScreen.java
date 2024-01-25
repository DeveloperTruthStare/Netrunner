package com.smith.netrunner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.GameState;
import com.smith.netrunner.RootApplication;

public class GameScreen extends BaseGameObject implements Screen {
    private final Texture background;
    private GameState gameState;
    private final RootApplication root;
    public boolean drawConceptArt = false;
    private BattleScreen battleScreen;

    public GameScreen(RootApplication root, GameState gameState) {
        super(root);
        this.root = root;
        this.gameState = gameState;
        this.gameState = new GameState();
        battleScreen = new BattleScreen(root);
        this.addChild(battleScreen);

        background = new Texture("Concept_Art_Game_Board.png");
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.root.batch.begin();

        battleScreen.render(delta);

        if (drawConceptArt)
            root.batch.draw(background, 0, 0);

        root.batch.end();
    }

    @Override
    public void keyDown(int keycode) {
        super.keyDown(keycode);
        if (keycode == Input.Keys.F1)
            drawConceptArt = !drawConceptArt;
    }

    @Override
    public void show() {

    }
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

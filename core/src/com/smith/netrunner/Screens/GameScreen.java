package com.smith.netrunner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL32;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.GameState;
import com.smith.netrunner.HealthBar.HealthBarView;
import com.smith.netrunner.RootApplication;

import java.awt.Shape;

public class GameScreen implements Screen {
    private Texture background;
    private GameState gameState;
    private RootApplication root;
    public HealthBarView hpView;
    public GameScreen(RootApplication root, GameState gameState) {
        this.root = root;
        this.gameState = gameState;
        this.gameState = new GameState();
        background = new Texture("background4.png");
        hpView = new HealthBarView(root);
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        this.root.batch.begin();
        // Draw the background
        root.batch.draw(background, 0, 0);
        hpView.draw(delta);
        // Draw player cards
        //Texture cardTexture = new Texture(gameState.player.deck.cards.get(gameState.player.currentCardToDisplay).iconFilePath);
        //root.batch.draw(cardTexture, 970, 540);
        // Draw corporation cards
        root.batch.end();
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

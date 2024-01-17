package com.smith.netrunner.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.GameState;
import com.smith.netrunner.RootApplication;

public class GameScreen implements Screen {
    private GameState gameState;
    private RootApplication root;
    public GameScreen(RootApplication root, GameState gameState) {
        this.root = root;
        this.gameState = gameState;
        this.gameState = new GameState();
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
        // Draw player cards
        Texture cardTexture = new Texture(gameState.player.deck.cards.get(gameState.player.currentCardToDisplay).iconFilePath);
        root.batch.draw(cardTexture, 970, 540);
        // Draw corporation cards

        this.root.batch.end();
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

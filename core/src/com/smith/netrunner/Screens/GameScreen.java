package com.smith.netrunner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.GameData.IHoverableCallback;
import com.smith.netrunner.GameData.IceBreaker;
import com.smith.netrunner.GameData.IceBreakerView;
import com.smith.netrunner.HandDisplay.HandDisplay;
import com.smith.netrunner.InfoWindow.InfoWindow;
import com.smith.netrunner.GameState;
import com.smith.netrunner.HealthBar.HealthBarView;
import com.smith.netrunner.RootApplication;

import java.util.ArrayList;

public class GameScreen extends BaseGameObject implements Screen, IHoverableCallback {
    private Texture background;
    private GameState gameState;
    private RootApplication root;
    public HealthBarView hpView;
    public ArrayList<IceBreakerView> iceBreakers;
    public InfoWindow infoWindow;
    public HandDisplay handDisplay;
    public boolean drawConceptArt = false;

    public void onHover(String dataToDisplay, int x, int y) {
        infoWindow.setData(dataToDisplay);
        infoWindow.setVisible(true);
        infoWindow.setMousePosition(x, y);
    }
    public void onHoverEnd() {
        infoWindow.setVisible(false);
    }

    @Override
    public void touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        drawConceptArt = true;
    }

    @Override
    public void touchUp(int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY, pointer, button);
        drawConceptArt = false;
    }

    @Override
    public void mouseMoved(int screenX, int screenY) {
        super.mouseMoved(screenX, screenY);
        handDisplay.mouseMoved(screenX, screenY);
        for(IceBreakerView ibView : iceBreakers)
            ibView.mouseMoved(screenX, screenY);
    }

    @Override
    public void keyDown(int keycode) {
        super.keyDown(keycode);
        if (keycode == Input.Keys.SPACE) {
            // Add a new card
            handDisplay.addToHand(new Card());
        }
    }

    public GameScreen(RootApplication root, GameState gameState) {
        super(root);
        this.root = root;
        this.gameState = gameState;
        this.gameState = new GameState();
        background = new Texture("background4.png");

        hpView = new HealthBarView(root);

        iceBreakers = new ArrayList<>();
        for(int i = 0; i < 5; ++i)
            iceBreakers.add(new IceBreakerView(root, new IceBreaker(), this, i));
        infoWindow = new InfoWindow(root);
        handDisplay = new HandDisplay(root);
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.root.batch.begin();
        hpView.draw(delta);
        for(IceBreakerView view : iceBreakers)
            view.draw(delta);
        infoWindow.draw(delta);
        handDisplay.draw(delta);
        // Draw player cards
        //Texture cardTexture = new Texture(gameState.player.deck.cards.get(gameState.player.currentCardToDisplay).iconFilePath);
        //root.batch.draw(cardTexture, 970, 540);
        // Draw corporation cards
        // Draw the background
        if (drawConceptArt)
            root.batch.draw(background, 0, 0);

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

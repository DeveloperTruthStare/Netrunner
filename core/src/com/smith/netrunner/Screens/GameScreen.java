package com.smith.netrunner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.Corporation.Corporation;
import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.GameState;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.World;

import java.util.ArrayList;

public class GameScreen extends BaseGameObject implements Screen {
    public final BattleScreen battleScreen;
    private final BattleSelectScreen battleSelect;
    public final World world;
    public ArrayList<Card> deck;

    public GameScreen(RootApplication app) {
        super(app);
        battleScreen = new BattleScreen(app, this);
        battleSelect = new BattleSelectScreen(app, this);
        battleScreen.setActive(false);

        this.addChild(battleSelect);
        this.addChild(battleScreen);
        world = new World();
        deck = new ArrayList<>();
        loadDefaultDeck();
    }
    private void loadDefaultDeck() {
        deck.add(Card.GenerateEvent());
        deck.add(Card.GenerateEvent());
        deck.add(Card.GenerateEvent());
        deck.add(Card.GenerateEvent());
        deck.add(Card.GenerateConsole());
        deck.add(Card.GenerateConsole());
        deck.add(Card.GenerateIceBreaker());
        deck.add(Card.GenerateIceBreaker());

    }
    public void attackSelected() {
        Corporation selectedCorp = world.getCurrentCorporation();
        if (selectedCorp.type == Corporation.CORPORATION_TYPE.EVENT) {
            System.out.println("Doing Event");
            world.battle();
        } else {

            battleScreen.reset();
            battleSelect.setActive(false);
            battleScreen.setActive(true);
            battleScreen.setCorporation(world.getCurrentCorporation());

        }
    }
    public void quitBattle() {
        battleSelect.setActive(true);
        battleScreen.setActive(false);

        world.battle();
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.app.batch.begin();
        super.draw(delta);
        app.batch.end();
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

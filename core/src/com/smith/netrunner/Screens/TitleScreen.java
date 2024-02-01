package com.smith.netrunner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.Screens.utils.TitleMenuState;
import com.smith.netrunner.UI.Button;
import com.smith.netrunner.UI.ClickCallbackListener;

public class TitleScreen extends BaseGameObject implements Screen, ClickCallbackListener {

    public TitleMenuState state;
    private final Button startButton;
    private final ClickCallbackListener onStartClick = new ClickCallbackListener() {
        @Override
        public void onClick() {
            app.startGame();
        }
    };
    private final Button quitButton;
    private final ClickCallbackListener onQuitClick = new ClickCallbackListener() {
        @Override
        public void onClick() {

        }
    };
    private final Button settingsButton;
    private final ClickCallbackListener onSettingsClick = new ClickCallbackListener() {
        @Override
        public void onClick() {

        }
    };
    private final BaseGameObject mainMenu;
    public TitleScreen(RootApplication app) {
        super(app);
        state = TitleMenuState.MAIN_MENU;

        mainMenu = new BaseGameObject(app);

        startButton = new Button(app, onStartClick);
        startButton.setPosition(1670, 540);
        mainMenu.addChild(startButton);

        quitButton = new Button(app, onQuitClick);
        quitButton.setPosition(1670, 390);
        mainMenu.addChild(quitButton);

        settingsButton = new Button(app, onSettingsClick);
        settingsButton.setPosition(1670, 465);
        mainMenu.addChild(settingsButton);

        addChild(mainMenu);

        startButton.setText("Start Game");
        quitButton.setText("Quit Game");
        settingsButton.setText("Settings");
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.app.batch.begin();

        super.draw(delta);

        this.app.batch.end();
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

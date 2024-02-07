package com.smith.netrunner;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.smith.netrunner.Screens.GameScreen;
import com.smith.netrunner.Screens.TitleScreen;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.Vector;

public class RootApplication extends Game {
	public InputProcessor inputProcessor = new InputProcessor() {
		@Override
		public boolean keyDown(int keycode) {
			currentScreen.keyDown(keycode);
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			currentScreen.keyUp(keycode);
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			currentScreen.keyTyped(character);
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			currentScreen.touchDown(screenX, screenY, pointer, button);
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			currentScreen.touchUp(screenX, screenY, pointer, button);
			return false;
		}

		@Override
		public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
			currentScreen.touchCancelled(screenX, screenY, pointer, button);
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			currentScreen.touchDragged(screenX, screenY, pointer);
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			currentScreen.mouseMoved(screenX, 1080 - screenY);
			return false;
		}

		@Override
		public boolean scrolled(float amountX, float amountY) {
			currentScreen.scrolled(amountX, amountY);
			return false;
		}
	};

	public SpriteBatch batch;
	private TitleScreen titleScreen;
	private GameScreen gameScreen;
	private BaseGameObject currentScreen;
	private OrthographicCamera camera;
	private FitViewport fitViewport;
	public void create () {
		// Set the input processor
		Gdx.input.setInputProcessor(inputProcessor);
		// Create sprite batch to draw to screen
		batch = new SpriteBatch();

		// Create Screens
		gameScreen = new GameScreen(this);
		titleScreen = new TitleScreen(this);
		setScreen(titleScreen);
		currentScreen = titleScreen;

	}
	public void startGame() {
		setScreen(gameScreen);
		currentScreen = gameScreen;
	}
	@Override
	public void render() {
		super.render();
	}
	@Override
	public void dispose () {
		batch.dispose();
	}
}

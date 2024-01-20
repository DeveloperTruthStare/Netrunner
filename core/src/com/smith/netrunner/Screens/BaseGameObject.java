package com.smith.netrunner.Screens;

import com.badlogic.gdx.InputProcessor;
import com.smith.netrunner.RootApplication;

public class BaseGameObject {
    private RootApplication app;
    public BaseGameObject(RootApplication app) {
        this.app = app;
    }

    public void keyDown(int keycode) {
    }

    public void keyUp(int keycode) {
    }

    public void keyTyped(char character) {
    }

    public void touchDown(int screenX, int screenY, int pointer, int button) {
    }

    public void touchUp(int screenX, int screenY, int pointer, int button) {
    }

    public void touchCancelled(int screenX, int screenY, int pointer, int button) {
    }

    public void touchDragged(int screenX, int screenY, int pointer) {
    }

    public void mouseMoved(int screenX, int screenY) {
    }

    public void scrolled(float amountX, float amountY) {
    }
}

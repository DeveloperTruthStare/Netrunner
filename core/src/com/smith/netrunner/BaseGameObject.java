package com.smith.netrunner;

import java.util.ArrayList;

public class BaseGameObject {
    protected RootApplication app;
    protected ArrayList<BaseGameObject> children;
    public BaseGameObject(RootApplication app) {
        this.app = app;
        children = new ArrayList<>();
    }

    public void addListener(BaseGameObject child) {
        if (!this.children.contains(child))
            this.children.add(child);
    }

    public void keyDown(int keycode) {
        for(BaseGameObject gameObject : children)
            gameObject.keyDown(keycode);
    }

    public void keyUp(int keycode) {
        for(BaseGameObject gameObject : children)
            gameObject.keyUp(keycode);
    }

    public void keyTyped(char character) {
        for(BaseGameObject gameObject : children)
            gameObject.keyTyped(character);
    }

    public void touchDown(int screenX, int screenY, int pointer, int button) {
        for(BaseGameObject gameObject : children)
            gameObject.touchDown(screenX, screenY, pointer, button);
    }

    public void touchUp(int screenX, int screenY, int pointer, int button) {
        for(BaseGameObject gameObject : children)
            gameObject.touchUp(screenX, screenY, pointer, button);
    }

    public void touchCancelled(int screenX, int screenY, int pointer, int button) {
        for(BaseGameObject gameObject : children)
            gameObject.touchCancelled(screenX, screenY, pointer, button);
    }

    public void touchDragged(int screenX, int screenY, int pointer) {
        for(BaseGameObject gameObject : children)
            gameObject.touchDragged(screenX, screenY, pointer);
    }

    public void mouseMoved(int screenX, int screenY) {
        for(BaseGameObject gameObject : children)
            gameObject.mouseMoved(screenX, screenY);
    }

    public void scrolled(float amountX, float amountY) {
        for(BaseGameObject gameObject : children)
            gameObject.scrolled(amountX, amountY);
    }
}

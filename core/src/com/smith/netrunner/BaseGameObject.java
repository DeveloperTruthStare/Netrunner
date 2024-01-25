package com.smith.netrunner;

import java.util.ArrayList;

public class BaseGameObject {
    protected RootApplication app;
    protected ArrayList<BaseGameObject> children;
    protected int x = 0, y = 0, width = 0, height = 0;
    protected boolean isActive;
    public BaseGameObject(RootApplication app) {
        this.app = app;
        children = new ArrayList<>();
        isActive = true;
    }
    public void draw(float dt) {
        if (!isActive) return;
        for(BaseGameObject child : children) {
            child.draw(dt);
        }
    }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void setActive(boolean active) {
        this.isActive = active;
    }
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public void addChild(BaseGameObject child) {
        if (!this.children.contains(child))
            this.children.add(child);
    }
    public void removeChild(BaseGameObject child) {
        if (!this.children.contains(child)) {
            this.children.remove(child);
        }
    }

    public void keyDown(int keycode) {
        if (!isActive) return;
        for(BaseGameObject gameObject : children)
            gameObject.keyDown(keycode);
    }

    public void keyUp(int keycode) {
        if (!isActive) return;

        for(BaseGameObject gameObject : children)
            gameObject.keyUp(keycode);
    }

    public void keyTyped(char character) {
        if (!isActive) return;

        for(BaseGameObject gameObject : children)
            gameObject.keyTyped(character);
    }

    public void touchDown(int screenX, int screenY, int pointer, int button) {
        if (!isActive) return;

        for(BaseGameObject gameObject : children)
            gameObject.touchDown(screenX, screenY, pointer, button);
    }

    public void touchUp(int screenX, int screenY, int pointer, int button) {
        if (!isActive) return;

        for(BaseGameObject gameObject : children)
            gameObject.touchUp(screenX, screenY, pointer, button);
    }

    public void touchCancelled(int screenX, int screenY, int pointer, int button) {
        if (!isActive) return;

        for(BaseGameObject gameObject : children)
            gameObject.touchCancelled(screenX, screenY, pointer, button);
    }

    public void touchDragged(int screenX, int screenY, int pointer) {
        if (!isActive) return;

        for(BaseGameObject gameObject : children)
            gameObject.touchDragged(screenX, screenY, pointer);
    }

    public void mouseMoved(int screenX, int screenY) {
        if (!isActive) return;

        for(BaseGameObject gameObject : children)
            gameObject.mouseMoved(screenX, screenY);
    }

    public void scrolled(float amountX, float amountY) {
        if (!isActive) return;

        for(BaseGameObject gameObject : children)
            gameObject.scrolled(amountX, amountY);
    }
}

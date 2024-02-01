package com.smith.netrunner;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class BaseGameObject {
    public class MyShapeRenderer extends ShapeRenderer {
        /**
         * Draws a rectangle with rounded corners of the given radius.
         */
        public void roundedRect(float x, float y, float width, float height, float radius){
            // Central rectangle
            super.rect(x + radius, y + radius, width - 2*radius, height - 2*radius);

            // Four side rectangles, in clockwise order
            super.rect(x + radius, y, width - 2*radius, radius);
            super.rect(x + width - radius, y + radius, radius, height - 2*radius);
            super.rect(x + radius, y + height - radius, width - 2*radius, radius);
            super.rect(x, y + radius, radius, height - 2*radius);

            // Four arches, clockwise too
            super.arc(x + radius, y + radius, radius, 180f, 90f);
            super.arc(x + width - radius, y + radius, radius, 270f, 90f);
            super.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
            super.arc(x + radius, y + height - radius, radius, 90f, 90f);
        }
    }

    protected RootApplication app;
    protected ArrayList<BaseGameObject> children;
    protected int x = 0, y = 0, width = 0, height = 0;
    protected boolean isActive;
    protected boolean isHovering = false;
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
        if (screenX > this.x && (1080 - screenY) > this.y && screenX < this.x + this.width && (1080 - screenY) < this.y + this.height) {
            this.onClick();
        }
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

        if (screenX > this.x && screenY > this.y && screenX < this.x + this.width && screenY < this.y + this.height) {
            if (!isHovering) {
                onHoverEnter();
            }
        } else {
            if (isHovering){
                onHoverExit();
            }
        }
    }

    public void scrolled(float amountX, float amountY) {
        if (!isActive) return;

        for(BaseGameObject gameObject : children)
            gameObject.scrolled(amountX, amountY);
    }

    public void onHoverEnter() {
        isHovering = true;
    }
    public void onHoverExit() {
        isHovering = false;
    }
    public void onClick() {
        System.out.println("Clicked");
    }
}

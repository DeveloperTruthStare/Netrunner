package com.smith.netrunner.UI;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.Screens.TitleScreen;

public class Button extends BaseGameObject {
    public class MyShapeRenderer extends ShapeRenderer{
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
    private ClickCallbackListener callback;
    private Rectangle rect;
    private BitmapFont font;
    private MyShapeRenderer shape;
    private String text;
    private int id;
    public Button(RootApplication app, ClickCallbackListener callback, int id) {
        super(app);
        this.callback = callback;
        font = new BitmapFont();
        shape = new MyShapeRenderer();
        this.width = 200;
        this.height = 50;
        this.x = id * (this.width + 10);
        this.y = 1080 - this.height;
        this.id = id;
    }
    public void draw(float delta) {
        if (!isActive) return;
        super.draw(delta);
        app.batch.end();
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(0.2f, 0.2f, 0.2f, 0.9f);
        shape.roundedRect(this.x, this.y, this.width, this.height, 20);
        shape.end();
        app.batch.begin();
        font.draw(app.batch, text, this.x + 15, this.y+ (float) this.height /2 +5);
    }
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void touchDown(int screenX, int screenY, int pointer, int button) {
        if(!isActive) return;
        super.touchDown(screenX, screenY, pointer, button);
        if (screenX > this.x && screenX < this.x + this.width && (1080-screenY) > this.y && (1080-screenY) < this.y + this.height)
            callback.onClick(this.id);
    }
}

package com.smith.netrunner.InfoWindow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.UI.ClickCallbackListener;

public class InfoWindow extends BaseGameObject {
    private String data = "";
    BitmapFont font;
    MyShapeRenderer shapeRenderer;
    GlyphLayout layout;
    private ClickCallbackListener callback;
    public InfoWindow(RootApplication app, ClickCallbackListener callback) {
        super(app);
        this.callback = callback;
        font = new BitmapFont();
        shapeRenderer = new MyShapeRenderer();
        layout = new GlyphLayout(font, data);
    }
    public void setData(String data) {
        this.data = data;
        layout.setText(font, data);
    }
    @Override
    public void draw(float dt) {
        super.draw(dt);
        if (!this.isActive) return;

        // Draw Background window
        app.batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.5f);
        shapeRenderer.roundedRect(this.x, this.y, this.width, this.height, 20);
        shapeRenderer.end();
        app.batch.begin();
        // Draw text info
        font.draw(app.batch, layout, this.x + (this.width - layout.width)/2, this.y + (this.height + layout.height)/2);
    }

    @Override
    public void onClick() {
        super.onClick();
        callback.onClick();
    }
}

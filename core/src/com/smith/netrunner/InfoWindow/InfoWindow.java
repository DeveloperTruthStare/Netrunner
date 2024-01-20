package com.smith.netrunner.InfoWindow;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.smith.netrunner.RootApplication;

public class InfoWindow {
    private RootApplication app;
    private boolean visible = false;
    private int x, y;
    private String data;
    BitmapFont font;
    public InfoWindow(RootApplication app) {
        this.app = app;
        font = new BitmapFont();
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public void setData(String data) {
        this.data = data;
    }
    public void setMousePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void draw(float dt) {
        if (!this.visible) return;
        // Draw Background window

        // Draw text info
        font.draw(app.batch, this.data, this.x, this.y);
    }
}

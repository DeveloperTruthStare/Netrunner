package com.smith.netrunner;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.awt.geom.Point2D;

public class Target extends BaseGameObject {
    private Image targetImage;
    private boolean lerping = false;
    private float startSize, targetSize, timer, targetTime;
    private Point2D targetPosition;
    public Target(RootApplication app) {
        super(app);
        targetImage = new Image(new Texture("BattleScreen/target.png"));
    }
    @Override
    public void setPosition(int x, int y) {
        this.targetPosition = new Point2D.Float(x, y);
        super.setPosition((int)targetPosition.getX() - this.width/2, (int)targetPosition.getY() - this.height/2);
        targetImage.setPosition(this.x, this.y);
    }
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        targetImage.setSize(width, height);
        setPosition((int)targetPosition.getX(), (int)targetPosition.getY());
    }

    public void draw(float dt) {
        super.draw(dt);

        if (!isActive) return;

        if (lerping) {
            timer += dt;
            float size = lerp(startSize, targetSize, timer/targetTime);
            setSize((int)size, (int)size);
            if (size == targetSize) lerping = false;
        }
        targetImage.draw(app.batch, 1);
    }

    public void LerpSize(float startSize, float endSize, float time) {
        targetSize = endSize;
        this.startSize = startSize;
        lerping = true;
        targetTime = time;
        timer = 0;
    }
    private float lerp(float start, float end, float time) {
        if (time > 1) return end;
        return start + time * (end - start);
    }
}

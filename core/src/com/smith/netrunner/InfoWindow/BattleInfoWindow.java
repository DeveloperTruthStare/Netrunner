package com.smith.netrunner.InfoWindow;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.Corporation.Corporation;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.UI.Button;
import com.smith.netrunner.UI.ClickCallbackListener;

import java.awt.Point;
import java.awt.geom.Point2D;

public class BattleInfoWindow extends BaseGameObject {
    private Corporation corporation;
    private final MyShapeRenderer shapeRenderer;
    private final int WIDTH = 820, HEIGHT = 880;
    private final Point2D OFF_SET;

    private final Button attackButton;
    private Image mainIcon;

    private String corporationName = "";
    private String corporationTypeDisplayText = "";

    public BattleInfoWindow(RootApplication app, BaseGameObject parent, ClickCallbackListener onClick) {
        super(app, parent);
        this.corporation = Corporation.GenerateEmpty();
        shapeRenderer = new MyShapeRenderer();
        OFF_SET = new Point(1075, 100);

        font.getData().setScale(2);
        attackButton = new Button(app, onClick);
        attackButton.setPosition((int)OFF_SET.getX() + 100, (int)OFF_SET.getY() + 100);
        attackButton.setSize(WIDTH-200, 100);
        attackButton.setText("Attack");
        addChild(attackButton);
        mainIcon = new Image();
        mainIcon.setPosition((int)OFF_SET.getX() + (float) WIDTH /2 - 50, (int)OFF_SET.getY()+HEIGHT-70);
    }


    public void setCorporation(Corporation corp) {
        this.corporation = corp;
        if (corporation.revealed) {
            corporationName = "Corporation Name: " + corporation.corporationName;
            corporationTypeDisplayText = corporation.corporationType;
            if (!corporation.battled || corporation.isBoss) {
                attackButton.setActive(true);
            } else {
                attackButton.setActive(false);
            }
        } else {
            attackButton.setActive(false);
            corporationName = "Unable to access";
            corporationTypeDisplayText = "Signal Degraded";
        }

        if (corp.isBoss && corp.battled) {
            attackButton.setText("Go to next Region");
        } else if (corp.type == Corporation.CORPORATION_TYPE.EVENT) {
            attackButton.setText("Begin Event");
        } else {
            attackButton.setText("Attack");
        }
    }

    @Override
    public void draw(float dt) {
        if (!isActive) return;

        app.batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.roundedRect((int)OFF_SET.getX(), (int)OFF_SET.getY(), WIDTH, HEIGHT, 20);

        shapeRenderer.end();
        app.batch.begin();

        drawText(corporationName, (float) WIDTH /2, HEIGHT-50, ALIGNMENT.CENTER);
        drawText("Corporation Type: ", 10, HEIGHT-500, ALIGNMENT.LEFT);
        drawText(corporationTypeDisplayText, WIDTH-10, HEIGHT-500, ALIGNMENT.RIGHT);

        if (Corporation.images.containsKey(corporation.type)) {
            mainIcon.setDrawable(new SpriteDrawable(new Sprite(Corporation.images.get(corporation.type))));
        }
        super.draw(dt);
    }

    @Override
    protected void drawText(String text, float x, float y, ALIGNMENT align) {
        super.drawText(text, (int)OFF_SET.getX() + x, (int)OFF_SET.getY()+y, align);
    }
}

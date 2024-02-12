package com.smith.netrunner.HardwareRig;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.UIState;

public class HardwareView extends BaseGameObject {
    private Card card;

    private final Image defaultImage = new Image(new Texture("HardwareRig/emptySlot1x4.png"));
    private final Stage stage = new Stage();
    private final Animation<TextureRegion> playerIndicatorAnim;
    private float stateTime = 0.0f;

    public HardwareView(RootApplication app) {
        super(app);
        this.card = null;
        stage.addActor(defaultImage);

        Texture playerIndicator = new Texture("BattleSelect/playerIndicatorSpriteSheet.png");
        TextureRegion[][] tmp = TextureRegion.split(playerIndicator, 115, 115);
        TextureRegion[] sprites = new TextureRegion[16];
        int index = 0;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                sprites[index++] = tmp[i][j];
            }
        }
        playerIndicatorAnim = new Animation<>((float) 1 / 24, sprites);
    }
    public void setCard(Card card) {
        this.card = card;
        if (this.card.hardwareIconFilePath == null || this.card.hardwareIconFilePath.isEmpty())
            return;
        Texture cardTexture = new Texture(this.card.hardwareIconFilePath);
        defaultImage.setDrawable(new SpriteDrawable(new Sprite(cardTexture)));
        defaultImage.setSize(cardTexture.getWidth(), cardTexture.getHeight());

    }

    public void reset() {
        this.card = null;
    }
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        defaultImage.setSize(width, height);
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        defaultImage.setPosition(x, y);
    }
    public void setPositionConsole() {
        setPosition(x-40, y - 20);
    }

    @Override
    public void onDragEnter() {
        super.onDragEnter();
        UIState.hoveredHardware = this;
    }

    @Override
    public void onDragExit() {
        super.onDragExit();
        if (UIState.hoveredHardware == this) {
            UIState.hoveredHardware = null;
        }
    }

    public Card getInstalledHardware() {
        return this.card;
    }

    @Override
    public void draw(float delta) {
        super.draw(delta);
        if (!isActive) return;
        if (card != null)
            stage.draw();

        if (isDragging && this.card == null) {
            stateTime += delta;
            app.batch.draw(playerIndicatorAnim.getKeyFrame(stateTime, true), this.x + this.width/2, this.y + this.height/2);
        }
    }

    @Override
    public void onClick() {
        super.onClick();
        if (this.card == null) return;
        System.out.println(this.card.cardSubType);
        if (this.card.cardSubType == Card.CardSubType.ICE_BREAKER) {
            UIState.selectedIceBreaker = this;
        }
    }
}

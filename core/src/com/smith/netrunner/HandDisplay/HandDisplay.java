package com.smith.netrunner.HandDisplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.BaseGameObject;

import java.util.ArrayList;

public class HandDisplay extends BaseGameObject {
    public ArrayList<Card> cardsInHand;
    private final Texture cardTexture;
    private final ArrayList<Image> cardImages;
    private final Stage stage;
    private int cardBeingHovered = -1;
    public HandDisplay(RootApplication app) {
        super(app);
        cardsInHand = new ArrayList<>();
        stage = new Stage(new ScreenViewport());
        cardImages = new ArrayList<>();

        Pixmap original = new Pixmap(Gdx.files.internal("card_icons/melos.png"));
        Pixmap resized = new Pixmap(200, 300, original.getFormat());
        resized.drawPixmap(original, 0, 0, original.getWidth(), original.getHeight(), 0, 0, 200, 300);
        cardTexture = new Texture(resized);
    }

    @Override
    public void mouseMoved(int x, int y) {
        float width = Math.min(1620, 100 * cardsInHand.size());
        float wPerCard = width / Math.max(1, cardsInHand.size()-1);
        float minW = 885 - width/2;
        if (y > 260 || x < minW || x > minW + width + wPerCard) {
            cardBeingHovered = -1;
        } else {
            for(int i = 0; i < cardsInHand.size(); ++i) {
                if (x < (i+1) * wPerCard + minW) {
                    cardBeingHovered = i;
                    return;

                }
            }
        }
    }

    @Override
    public void touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);

    }

    public void addToHand(Card card) {
        // TODO Animate this
        cardsInHand.add(card);
        Image image = new Image(cardTexture);
        image.setSize(200, 300);
        image.setOrigin((float) cardTexture.getWidth() /2, (float) cardTexture.getHeight() /2);
        cardImages.add(image);
        stage.addActor(image);
    }

    public void draw(float delta) {
        float maxTotalRotation = 40;

        float minRotation = Math.min(maxTotalRotation, cardsInHand.size() * 5)/2;
        float maxRotation = -minRotation;
        float rotationPerCard = (maxRotation - minRotation) / Math.max(1, cardsInHand.size() - 1);

        float width = Math.min(1620, 100 * cardsInHand.size());
        float wPerCard = width / Math.max(1, cardsInHand.size()-1);
        float minW = 885 - width/2;

        int startingY = -(cardsInHand.size()-1)/2;
        int midPoint = -startingY;
        int yVal = startingY * 50;

        for(int i = 0; i < cardsInHand.size(); ++i) {
            yVal = Math.max(-50, yVal);
            yVal = Math.min(yVal, -10);

            cardImages.get(i).setPosition(minW + (i * wPerCard), yVal-20);
            cardImages.get(i).setSize(200, 300);
            cardImages.get(i).setZIndex(i);
            cardImages.get(i).setRotation(minRotation + (rotationPerCard * i));
            if (i < midPoint) {
                yVal += 10;
            } else if (i > midPoint || cardsInHand.size() % 2 == 1) {
                yVal -= 10;
            }
        }
        if (cardBeingHovered != -1) {
            cardImages.get(cardBeingHovered).setZIndex(100);
            cardImages.get(cardBeingHovered).setSize(300, 450);
            cardImages.get(cardBeingHovered).setPosition(minW + (cardBeingHovered * wPerCard), 0);
            cardImages.get(cardBeingHovered).setRotation(0);

        }
        stage.draw();
    }
}

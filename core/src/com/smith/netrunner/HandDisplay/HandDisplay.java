package com.smith.netrunner.HandDisplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.UIState;

import java.util.ArrayList;

public class HandDisplay extends BaseGameObject {
    public static final int MAX_WIDTH = 1300;
    public ArrayList<Card> cardsInHand;
    private Texture cardTexture;
    private final ArrayList<Image> cardImages;
    private final Stage stage;
    public HandDisplay(RootApplication app) {
        super(app);
        cardsInHand = new ArrayList<>();
        stage = new Stage(new ScreenViewport());
        cardImages = new ArrayList<>();
    }
    public void reset() {
        cardImages.clear();
        cardsInHand.clear();
        stage.clear();
    }

    public Card discardTopCard() {
        return removeCard(0);
    }
    @Override
    public void mouseMoved(int x, int y) {
        float width = Math.min(MAX_WIDTH, 100 * cardsInHand.size());
        float wPerCard = width / Math.max(1, cardsInHand.size()-1);
        float minW = 885 - width/2;
        if (y > 200 || x < minW || x > minW + width + wPerCard) {
            UIState.hoveredCard = null;
            UIState.hoveredCardIndex = -1;
            return;
        }

        for(int i = 0; i < cardsInHand.size(); ++i) {
            if (x < (i+1) * wPerCard + minW) {
                UIState.hoveredCard = cardsInHand.get(i);
                UIState.hoveredCardIndex = i;
                return;
            }
        }
    }

    public void addToHand(Card card) {
        // TODO Animate this
        cardsInHand.add(card);
        Pixmap original = new Pixmap(Gdx.files.internal(card.iconFilePath));
        Pixmap resized = new Pixmap(200, 300, original.getFormat());
        resized.drawPixmap(original, 0, 0, original.getWidth(), original.getHeight(), 0, 0, 200, 300);
        cardTexture = new Texture(resized);

        Image image = new Image(cardTexture);
        image.setSize(200, 300);
        image.setOrigin((float) cardTexture.getWidth() /2, (float) cardTexture.getHeight() /2);
        cardImages.add(image);
        stage.addActor(image);
    }
    public Card removeCard(int index) {
        if (index >= cardsInHand.size())
            return null;
        for(Actor actor : stage.getActors()) {
            if (cardImages.get(index) == actor) {
                actor.remove();
                cardImages.remove(index);
                return cardsInHand.remove(index);
            }
        }
        return null;
    }

    public void draw(float delta) {
        float maxTotalRotation = 40;

        float minRotation = Math.min(maxTotalRotation, cardsInHand.size() * 5)/2;
        float maxRotation = -minRotation;
        float rotationPerCard = (maxRotation - minRotation) / Math.max(1, cardsInHand.size() - 1);

        float width = Math.min(MAX_WIDTH, 125 * cardsInHand.size());
        float wPerCard = width / Math.max(1, cardsInHand.size());
        float minW = 885 - width/2;

        int heightDiff = 50;

        int halfCards = (cardsInHand.size()-1)/2;
        int step = heightDiff / Math.max(1, ((cardsInHand.size()+1)/2));
        int yVal = -heightDiff;
        int yOffset = -100;

        for(int i = 0; i < cardsInHand.size(); ++i) {
            cardImages.get(i).setPosition(minW + (i * wPerCard), yVal + yOffset);
            cardImages.get(i).setSize(200, 300);
            cardImages.get(i).setZIndex(i);
            cardImages.get(i).setRotation(minRotation + (rotationPerCard * i));
            if (i < halfCards) {
                yVal += step;
            } else if (i > halfCards || cardsInHand.size() % 2 == 1) {
                yVal -= step;
            }
        }
        if (UIState.hoveredCard != null && cardsInHand.contains(UIState.hoveredCard)) {
            int index = cardsInHand.indexOf(UIState.hoveredCard);
            cardImages.get(index).setZIndex(100);
            cardImages.get(index).setSize(300, 450);
            cardImages.get(index).setPosition(minW + (index * wPerCard), 0);
            cardImages.get(index).setRotation(0);
        }
        stage.draw();
    }
}

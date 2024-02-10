package com.smith.netrunner.GameData;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    public ArrayList<Card> deck;
    public ArrayList<Card> discard;
    public ArrayList<Card> trash;
    public Deck() {
        deck = new ArrayList<>();
        discard = new ArrayList<>();
        trash = new ArrayList<>();
    }
    public Card drawFromDeck() {
        if (deck.size() == 0)
            shuffle();
        if (deck.size() == 0)
            return null;
        return deck.remove(0);
    }
    public void loadDeck(ArrayList<Card> deck) {
        this.deck.addAll(deck);
        shuffle();
    }
    public void discardCard(Card card) {
        discard.add(card);
    }
    public void trashCard(Card card) {
        trash.add(card);
    }
    public void discardCards(ArrayList<Card> toDiscard) {
        discard.addAll(toDiscard);
    }
    private void shuffle() {
        while(discard.size() > 0) {
            deck.add(discard.remove(0));
        }
        Random r = new Random();
        for (int i = deck.size()-1; i > 0; --i) {
            int j = r.nextInt(i+1);
            Card temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
    }
}

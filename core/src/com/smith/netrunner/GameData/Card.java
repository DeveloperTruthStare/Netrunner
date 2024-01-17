package com.smith.netrunner.GameData;

import com.smith.netrunner.GameState;

public class Card {
    public enum CardType {
        PROGRAM, HARDWARE, RESOURCE, EVENT, IDENTITY
    }
    public String iconFilePath;
    public String cardName;
    public int cost;
    public CardType cardType;
    public String cardText;
    public Ability ability;
    public Card() {

    }
    public Card(String cardName, int cost, CardType cardType, String cardText, Ability ability) {
        this.cardName = cardName;
        this.cost = cost;
        this.cardType = cardType;
        this.cardText = cardText;
        this.ability = ability;
    }
}
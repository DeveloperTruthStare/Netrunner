package com.smith.netrunner.GameData;

import java.util.Random;

public class Card {
    public enum CardType {
        HARDWARE, RESOURCE, EVENT, IDENTITY
    }
    public enum CardSubType {
        ICE_BREAKER, CONSOLE, RESOURCE, RUN, ACTION
    }
    public String iconFilePath;
    public String hardwareIconFilePath;
    public String cardName;
    public int cost;
    public CardType cardType;
    public CardSubType cardSubType;
    public String cardText;
    public Ability ability;
    public int value;
    public static Card GenerateIceBreaker() {
        Card card = new Card();
        card.cardType = CardType.HARDWARE;
        card.cardSubType = CardSubType.ICE_BREAKER;
        card.cost = 1;
        card.iconFilePath = "card_icons/redCard.png";
        return card;
    }
    public static Card GenerateConsole() {
        Card card = new Card();
        card.cardType = CardType.HARDWARE;
        card.cardSubType = CardSubType.CONSOLE;
        card.cost = 1;
        card.iconFilePath = "card_icons/blueCard.png";
        card.hardwareIconFilePath = "HardwareRig/hardwareIcons/consoleExtension.png";
        return card;
    }
    public static Card GenerateEvent() {
        Card card = new Card();
        card.cardType = CardType.EVENT;
        card.cardSubType = CardSubType.ACTION;
        card.cost = 1;
        card.value = 5;
        card.iconFilePath = "card_icons/yellowCard.png";
        return card;
    }
    public static Card GenerateRunEvent() {
        Card card = new Card();
        card.cardType = CardType.EVENT;
        card.cardSubType = CardSubType.RUN;
        card.cost = 1;
        card.iconFilePath = "card_icons/greenCard.png";
        return card;
    }
    public static Card GenerateRandomCard(WorldLine worldline) {
        Random rand = new Random();
        int card = rand.nextInt(0, 3);
        switch(card) {
            case 0:
                System.out.println("Generating Console");
                return GenerateConsole();
            case 1:
                System.out.println("Generating Ice Breaker");
                return GenerateIceBreaker();
            case 2:
                System.out.println("Generating Event");
                return GenerateEvent();
            case 3:
                System.out.println("Generating Run");
                return GenerateRunEvent();
            default:
                return GenerateIceBreaker();
        }
    }
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
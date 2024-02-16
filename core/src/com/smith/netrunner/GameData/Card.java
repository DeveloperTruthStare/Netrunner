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
    public int attack;
    public enum IceBreakerDeveloper {
        DEVELOPER_1, DEVELOPER_2, DEVELOPER_3, SELF_MADE
    }
    public enum IceBreakerFunction {
        FUNCTION_1, FUNCTION_2, FUNCTION_3, FUNCTION_4
    }
    public IceBreakerDeveloper developer;
    public IceBreakerFunction function;
    public static Card GenerateIceBreaker() {
        Card card = new Card();
        card.cardType = CardType.HARDWARE;
        card.cardSubType = CardSubType.ICE_BREAKER;
        card.cost = 1;
        card.value = 2;
        card.attack = 2;
        card.developer = IceBreakerDeveloper.DEVELOPER_1;
        card.function = IceBreakerFunction.FUNCTION_1;
        card.iconFilePath = "card_icons/iceBreaker1.png";
        return card;
    }
    public static Card GenerateIceBreaker(int type) {
        Card card = new Card();
        card.cardType = CardType.HARDWARE;
        card.cardSubType = CardSubType.ICE_BREAKER;
        card.cost = 1;
        card.attack = 2;
        card.developer = IceBreakerDeveloper.SELF_MADE;
        switch(type) {
            case 0:
                card.function = IceBreakerFunction.FUNCTION_1;
                card.iconFilePath = "card_icons/iceBreaker1.png";
                break;
            case 1:
                card.function = IceBreakerFunction.FUNCTION_2;
                card.iconFilePath = "card_icons/iceBreaker2.png";
                break;
            case 2:
                card.function = IceBreakerFunction.FUNCTION_3;
                card.iconFilePath = "card_icons/iceBreaker3.png";
                break;
            case 3:
            default:
                card.function = IceBreakerFunction.FUNCTION_4;
                card.iconFilePath = "card_icons/iceBreaker4.png";
                break;
        }


        return card;
    }
    public static Card GenerateConsole() {
        Card card = new Card();
        card.cardType = CardType.HARDWARE;
        card.cardSubType = CardSubType.CONSOLE;
        card.cost = 1;
        card.iconFilePath = "card_icons/melos.png";
        card.hardwareIconFilePath = "BattleScreen/hardware/consoles/consoleExtensionperspective.png";
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
        card.iconFilePath = "card_icons/runCard.png";
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
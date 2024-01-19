package com.smith.netrunner.GameData;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Runner {
    public CardCollection deck;
    public CardCollection hand;
    public CardCollection discardPile;
    public ArrayList<Card> programs;
    public ArrayList<Card> hardware;
    public ArrayList<Card> resources;
    public Card identityCard;
    public int money;
    public int health;
    public Runner() {
        this.money = 50;
        this.health = 10;
    }
    public void loadCards() {
        try {
            String text = new String(Files.readAllBytes(Paths.get("startingDeck.json")), StandardCharsets.UTF_8);
            this.deck = new ObjectMapper().readValue(text, CardCollection.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void drawStartingHand() {

    }
}
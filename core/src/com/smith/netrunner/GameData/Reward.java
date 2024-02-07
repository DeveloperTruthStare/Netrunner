package com.smith.netrunner.GameData;

import com.smith.netrunner.Corporation.RunTarget;

import java.util.ArrayList;
import java.util.Random;

public class Reward {
    public static final int DEFAULT_MONEY_REWARD = 10;
    public static final int MIN_BANK_REWARD = 100;
    public static final int MAX_BANK_REWARD = 200;
    public static final int MIN_TRAP_REWARD = 100;
    public static final int MAX_TRAP_REWARD = 150;
    public static Reward GenerateBankReward(WorldLine worldline) {
        Random r = new Random();
        Reward reward = new Reward();
        reward.money = r.nextInt(MIN_BANK_REWARD, MAX_BANK_REWARD);
        return reward;
    }
    public static Reward GenerateTrapReward(WorldLine worldline) {
        Random r = new Random();
        Reward reward = new Reward();
        reward.money = r.nextInt(MIN_TRAP_REWARD, MAX_TRAP_REWARD);
        reward.trace = 1;
        return reward;
    }
    public static Reward GenerateProxyServer() {
        Reward reward = new Reward();
        reward.proxyServer = true;
        reward.money = DEFAULT_MONEY_REWARD;
        return reward;
    }
    public static Reward GenerateDecryptedKey() {
        Reward reward = new Reward();
        reward.decryptedKey = true;
        reward.money = DEFAULT_MONEY_REWARD;
        return reward;
    }
    public static Reward GenerateEncryptedKey() {
        Reward reward = new Reward();
        reward.encryptedKey = true;
        reward.money = DEFAULT_MONEY_REWARD;
        return reward;
    }
    public static Reward GenerateDecryptionAlgorithm() {
        Reward reward = new Reward();
        reward.decryptionAlgorithm = true;
        reward.money = DEFAULT_MONEY_REWARD;
        return reward;
    }
    public static Reward GenerateBossLocation() {
        Reward reward = new Reward();
        reward.bossLocation = true;
        reward.money = DEFAULT_MONEY_REWARD;
        return reward;
    }
    public static Reward GenerateRNDReward(WorldLine worldline) {
        Reward reward = new Reward();
        reward.money = DEFAULT_MONEY_REWARD;
        reward.cards = new ArrayList<>();

        reward.cards.add(Card.GenerateRandomCard(worldline));
        reward.cards.add(Card.GenerateRandomCard(worldline));
        reward.cards.add(Card.GenerateRandomCard(worldline));

        return reward;
    }
    public int money;
    public int trace;
    public boolean proxyServer = false;
    public boolean decryptedKey = false;
    public boolean encryptedKey = false;
    public boolean decryptionAlgorithm = false;
    public boolean bossLocation = false;
    public ArrayList<Card> cards;
    public Reward() {
        money = DEFAULT_MONEY_REWARD;
        trace = 0;
        cards = new ArrayList<>();
    }
    public void add(Reward reward) {
        this.money += reward.money;
        this.trace += reward.trace;
        this.proxyServer = this.proxyServer || reward.proxyServer;
        this.decryptedKey = this.decryptedKey || reward.decryptedKey;
        this.encryptedKey = this.encryptedKey || reward.encryptedKey;
        this.decryptionAlgorithm = this.decryptionAlgorithm || reward.decryptionAlgorithm;
        this.bossLocation = this.bossLocation || reward.bossLocation;
        this.cards.addAll(reward.cards);

    }
}

package com.smith.netrunner.GameData;

import com.smith.netrunner.Corporation.RunTarget;

import java.util.ArrayList;

public class Server {
    public static Server GenerateBankRecords(WorldLine worldline) {
        Server server = new Server();
        server.reward = Reward.GenerateBankReward(worldline);
        server.serverType = ServerType.BANK_RECORDS;
        return server;
    }
    public static Server GenerateHoneyPot(WorldLine worldline) {
        Server server = new Server();
        server.reward = Reward.GenerateTrapReward(worldline);
        server.serverType = ServerType.HONEY_POT;
        return server;
    }
    public static Server GenerateRND(WorldLine worldline) {
        Server server = new Server();
        server.serverType = ServerType.RND;
        server.reward = Reward.GenerateRNDReward(worldline);
        return server;
    }
    public static Server GenerateKeyServer(WorldLine worldline, int type) {
        Server server = new Server();
        server.serverType = ServerType.KEY_DATABASE;
        switch(type) {
            case 0:
                // Boss - Proxy Server Location
                server.reward = Reward.GenerateProxyServer();
                break;
            case 1:
                // Tech Startup - decrypted key
                server.reward = Reward.GenerateDecryptedKey();
                break;
            case 2:
                // Other - encrypted key
                server.reward = Reward.GenerateEncryptedKey();
                break;
            case 4:
                // Tech Giant - decryption algorithm
                server.reward = Reward.GenerateDecryptionAlgorithm();
                break;
            case 5:
                // ISP - reveal location of boss
                server.reward = Reward.GenerateBossLocation();
                break;
        }
        return server;
    }
    public ServerType serverType;
    public Reward reward;
    public ArrayList<Ice> installedIce;
    public boolean revealed;
    public boolean hacked;
    public Server() {
        revealed = false;
        hacked = false;
        installedIce = new ArrayList<>();
    }
}

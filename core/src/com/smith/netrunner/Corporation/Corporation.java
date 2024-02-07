package com.smith.netrunner.Corporation;

import com.badlogic.gdx.graphics.Texture;
import com.smith.netrunner.GameData.Server;
import com.smith.netrunner.GameData.WorldLine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Corporation {
    public static final Map<CORPORATION_TYPE, Texture> images;
    static {
        Map<CORPORATION_TYPE, Texture> aMap = new HashMap<>();
        aMap.put(CORPORATION_TYPE.BANK, new Texture("corporation/icons/bank.png"));
        aMap.put(CORPORATION_TYPE.EVENT, new Texture("corporation/icons/home.png"));

        images = Collections.unmodifiableMap(aMap);
    }
    public enum CORPORATION_TYPE {
        TECH_ESTABLISHED, TECH_STARTUP, BANK, INDUSTRY, ISP, SMALL_BUSINESS, EVENT, TECH_PARENT
    }
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = (new Random()).nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
    public boolean revealed = false;
    public boolean battled = false;
    public int difficulty;
    public boolean isBoss = false;
    public boolean hasDecryptedKey = false;
    public String corporationName = "";
    public String corporationType = "Bank";
    public CORPORATION_TYPE type;
    public ArrayList<Server> servers;

    public static Corporation GenerateBoss(WorldLine worldline, int region) {
        Corporation boss = new Corporation();
        boss.isBoss = true;
        boss.corporationType = "Boss";
        boss.servers = new ArrayList<>();
        boss.servers.add(Server.GenerateRND(worldline));
        boss.servers.add(Server.GenerateKeyServer(worldline, 0));
        Random r = new Random();
        int a = r.nextInt(2);
        if (a == 0) {
            boss.servers.add(Server.GenerateBankRecords(worldline));
        } else {
            boss.servers.add(Server.GenerateHoneyPot(worldline));
        }
        if (region == 1) {
            boss.type = CORPORATION_TYPE.TECH_STARTUP;
        } else if (region == 2) {
            boss.type = CORPORATION_TYPE.TECH_ESTABLISHED;
        } else if (region == 3) {
            boss.type = CORPORATION_TYPE.TECH_PARENT;
        }
        return boss;
    }
    public void addServer(Server server) {
        this.servers.add(server);
    }
    public static Corporation GenerateEmpty() {
        return new Corporation();
    }
    public static Corporation GenerateRandom(WorldLine worldline) {
        Corporation corp = new Corporation();
        Random random = new Random();
        corp.type = randomEnum(CORPORATION_TYPE.class);
        if (corp.type == CORPORATION_TYPE.TECH_PARENT) corp.type = CORPORATION_TYPE.SMALL_BUSINESS;
        corp.corporationType = corp.type.toString();

        if (corp.type == CORPORATION_TYPE.BANK) return GenerateBank(worldline);
        corp.servers = new ArrayList<>();
        corp.servers.add(Server.GenerateRND(worldline));
        corp.servers.add(Server.GenerateKeyServer(worldline, 5));
        corp.servers.add(Server.GenerateBankRecords(worldline));
        return corp;
    }
    public static Corporation GenerateBank(WorldLine worldline) {
        Corporation corp = new Corporation();
        corp.type = CORPORATION_TYPE.BANK;
        corp.servers = new ArrayList<>();
        // Add Servers
        corp.servers.add(Server.GenerateBankRecords(worldline));
        corp.servers.add(Server.GenerateHoneyPot(worldline));
        corp.servers.add(Server.GenerateRND(worldline));

        corp.corporationType = corp.type.toString();

        return corp;
    }
    public static Corporation GenerateStartup() {
        Corporation corp = new Corporation();
        corp.type = CORPORATION_TYPE.TECH_STARTUP;
        corp.corporationType = corp.type.toString().replace("_", "");


        return corp;
    }
    public static Corporation GenerateStartingNode() {
        Corporation corp = new Corporation();
        corp.revealed = true;
        corp.battled = true;
        corp.corporationName = "Entry Server";
        corp.corporationType = "Home";
        corp.type = CORPORATION_TYPE.EVENT;


        return corp;
    }
}

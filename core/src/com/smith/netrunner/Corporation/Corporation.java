package com.smith.netrunner.Corporation;

import com.badlogic.gdx.graphics.Texture;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Corporation {
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
    public static final Map<CORPORATION_TYPE, Texture> images;
    static {
        Map<CORPORATION_TYPE, Texture> aMap = new HashMap<>();
        aMap.put(CORPORATION_TYPE.BANK, new Texture("corporation/icons/bank.png"));
        aMap.put(CORPORATION_TYPE.EVENT, new Texture("corporation/icons/home.png"));

        images = Collections.unmodifiableMap(aMap);
    }

    public static Corporation GenerateBoss(int region) {
        Corporation boss = new Corporation();
        boss.isBoss = true;
        boss.corporationType = "Boss";
        if (region == 1) {
            boss.type = CORPORATION_TYPE.TECH_STARTUP;
        } else if (region == 2) {
            boss.type = CORPORATION_TYPE.TECH_ESTABLISHED;
        } else if (region == 3) {
            boss.type = CORPORATION_TYPE.TECH_PARENT;
        }
        return boss;
    }
    public static Corporation GenerateEmpty() {
        return new Corporation();
    }
    public static Corporation GenerateRandom() {
        Corporation corp = new Corporation();
        Random random = new Random();
        corp.type = randomEnum(CORPORATION_TYPE.class);
        if (corp.type == CORPORATION_TYPE.TECH_PARENT) corp.type = CORPORATION_TYPE.SMALL_BUSINESS;
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

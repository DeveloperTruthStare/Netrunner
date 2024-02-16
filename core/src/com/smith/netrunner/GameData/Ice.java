package com.smith.netrunner.GameData;

import com.smith.netrunner.Corporation.Corporation;

public class Ice {
    public enum IceDeveloper {
        DEVELOPER_1, DEVELOPER_2, DEVELOPER_3, SELF_MADE
    }
    public enum IceFunction {
        FUNCTION_1, FUNCTION_2, FUNCTION_3, FUNCTION_4
    }
    public boolean isRevealed = false;
    public int health = 4;
    public IceDeveloper developer;
    public IceFunction function;
    public String iconFilePath = "iceIcons/ice.png";
    public static Ice GenerateRandomIce(WorldLine worldline) {
        Ice ice = new Ice();
        ice.isRevealed = false;
        ice.health = 4;
        ice.developer = IceDeveloper.DEVELOPER_1;
        ice.function = Corporation.randomEnum(IceFunction.class);
        return ice;
    }
}

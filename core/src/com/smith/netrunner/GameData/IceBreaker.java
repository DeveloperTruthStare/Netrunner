package com.smith.netrunner.GameData;

import java.util.ArrayList;

public class IceBreaker extends Card {
    public int defaultStrength = 2;
    public int increasedStrength;
    public ArrayList<Ability> abilities;
    public IceBreaker() {
        abilities = new ArrayList<>();
        Ability increaseStrength = new Ability();
        increaseStrength.abilityType = Ability.AbilityType.ACTIVE;
        increaseStrength.abilityName = Ability.AbilityName.IncreaseStrength;
        increaseStrength.value = 1;
        increaseStrength.cost = 1;
        increaseStrength.uses = -1;

        Ability breakIce = new Ability();
        breakIce.abilityName = Ability.AbilityName.BypassIce;
        breakIce.abilityType = Ability.AbilityType.ACTIVE;
        breakIce.value = -1;
        breakIce.uses = 1;
        breakIce.cost = 1;

        abilities.add(increaseStrength);
        abilities.add(breakIce);
    }

    public ArrayList<Ability> getAbilities() {
        abilities.get(0).value = getStrength();
        return abilities;
    }
    public void setStrength(int strength) {
        this.increasedStrength = strength;
    }
    public int getStrength() {
        return defaultStrength + increasedStrength;
    }
}

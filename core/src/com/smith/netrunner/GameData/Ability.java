package com.smith.netrunner.GameData;

public class Ability {
    public enum AbilityName {
        GainMoney, BypassIce, Heal, Event, AccessCard, TrashCard, DrawCard, IncreaseStrength
    }
    public enum AbilityType {
        PASSIVE, ACTIVE
    }
    public enum Destination {
        HQ, RND, RemoteServer, Archives, Root
    }

    public AbilityType abilityType;
    public AbilityName abilityName;
    public int value;
    public int uses;
    public int cost;
    public Destination destination;

    public Ability onSuccess;
    public Ability onFail;
}

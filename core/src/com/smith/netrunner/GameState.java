package com.smith.netrunner;

import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.GameData.Runner;

public class GameState {
    public class Corporation {

    }

    public Runner player;
    public Corporation enemy;
    public GameState() {
        // Load Player Cards
        this.player = new Runner();
        player.loadCards();
    }
    public void CheckPassives() {
        for (Card card : player.hardware) {
            CheckPassive(card);
        }
        for (Card card : player.programs) {
            CheckPassive(card);
        }
        for (Card card : player.resources) {
            CheckPassive(card);
        }
    }
    public void CheckPassive(Card card) {

    }
    public void ActivateAbility(Card card) {
        switch (card.ability.abilityName) {
            case GainMoney:
                this.player.money += card.ability.value;

                break;
            case Heal:
                this.player.health += card.ability.value;
                break;
            case BypassIce:

                break;
        }
    }

}

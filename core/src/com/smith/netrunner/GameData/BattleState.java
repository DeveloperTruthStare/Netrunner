package com.smith.netrunner.GameData;

import com.smith.netrunner.Corporation.RunTarget;
import com.smith.netrunner.HardwareRig.HardwareView;
import com.smith.netrunner.Screens.GameScreen;

import java.util.ArrayList;

public class BattleState {
    public static BattleState Instance;
    public enum STATE {
        LOADING, DISCARD, DRAW, PLAYER_ACTION,
        ENEMY_TURN,
        SELECTING_ATTACKER, DEALING_WITH_ICE, RUNNING, ACCEPTING_REWARDS, POST_ACCEPT_REWARDS,
        FINALIZING_BATTLE
    }
    private STATE state = STATE.LOADING;
    private final int maxCycles = 3;
    public int curCycles = 3;
    public String infoToDisplay = "";
    public RunTarget selectedServer;
    public HardwareView selectedAttacker;
    public BattleState() {
        Instance = this;
    }
    public void nextState() {
        switch(this.state) {
            case LOADING:
                this.state = STATE.DISCARD;
                break;
            case DISCARD:
                this.state = STATE.DRAW;
                break;
            case DRAW:
                this.state = STATE.PLAYER_ACTION;
                break;
            case SELECTING_ATTACKER:
                this.state = STATE.DEALING_WITH_ICE;
                break;
            case PLAYER_ACTION:
                this.state = STATE.ENEMY_TURN;
                break;
            case DEALING_WITH_ICE:
                this.state = STATE.ACCEPTING_REWARDS;
                break;
            case ACCEPTING_REWARDS:
                this.state = STATE.POST_ACCEPT_REWARDS;
                break;
            case POST_ACCEPT_REWARDS:
                this.state = STATE.PLAYER_ACTION;
                break;

            case ENEMY_TURN:
                nextTurn();
                break;
        }
    }
    public void acceptRewards() {
        this.state = STATE.ACCEPTING_REWARDS;
    }
    public STATE getState() {
        return this.state;
    }
    public void jackOut() { // Called during combat
        this.state = STATE.PLAYER_ACTION;
        infoToDisplay = "";
    }
    public boolean canPerformAction(Card.CardType action, int cost) {
        if (cost > curCycles) return false;
        return  !(state == STATE.RUNNING ||
                state == STATE.SELECTING_ATTACKER || state == STATE.DEALING_WITH_ICE ||
                state == STATE.ACCEPTING_REWARDS || state == STATE.ENEMY_TURN);
    }

    public void startRun(RunTarget target) {
        if (state != STATE.PLAYER_ACTION) return;
        this.selectedServer = target;
        this.state = STATE.SELECTING_ATTACKER;
        infoToDisplay = "Select an Ice breaker to attack with";
    }
    public void selectAttacker(HardwareView attacker) {
        if (state != STATE.SELECTING_ATTACKER) return;
        this.selectedAttacker = attacker;
        nextState();
    }
    public void reset() {
        state = STATE.LOADING;
        curCycles = maxCycles;
    }
    public void nextTurn() {
        state = STATE.DISCARD;
        curCycles = maxCycles;
    }
    public void endBattle() {
        this.state = STATE.FINALIZING_BATTLE;
    }
}
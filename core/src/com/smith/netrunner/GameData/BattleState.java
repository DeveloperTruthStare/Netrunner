package com.smith.netrunner.GameData;

import com.smith.netrunner.Corporation.RunTarget;
import com.smith.netrunner.Screens.GameScreen;

import java.util.ArrayList;

public class BattleState {
    public enum STATE {
        LOADING, DISCARD, DRAW, PLAYER_ACTION, ENEMY_TURN, SELECTING_SERVER, DEALING_WITH_ICE, RUNNING, ACCEPTING_REWARDS
    }
    private STATE state = STATE.LOADING;
    private final int maxCycles = 3;
    public int curCycles = 3;
    public int currentServer = -1;
    public int turns = 0;
    public String infoToDisplay = "";
    public boolean accessedRND;
    public void nextState() {
        switch(this.state) {
            case LOADING:
                this.state = STATE.DISCARD;
                break;
            case DISCARD:
                this.state = STATE.DRAW;
                break;
            case DRAW:
            case ACCEPTING_REWARDS:
                this.state = STATE.PLAYER_ACTION;
                break;
            case PLAYER_ACTION:
                this.state = STATE.ENEMY_TURN;
                break;
            case DEALING_WITH_ICE:
                this.state = STATE.ACCEPTING_REWARDS;
                break;
            case ENEMY_TURN:
                nextTurn();
        }
        infoToDisplay = this.state.toString();
    }
    public STATE getState() {
        return this.state;
    }

    public boolean canPerformAction(Card.CardType action, int cost) {
        if (cost > curCycles) return false;
        if (state == STATE.RUNNING || state == STATE.SELECTING_SERVER ||
                state == STATE.DEALING_WITH_ICE || state == STATE.ACCEPTING_REWARDS ||
                state == STATE.ENEMY_TURN) {
            return false;
        }
        return true;
    }
    public void startRun() {
        state = STATE.SELECTING_SERVER;
    }
    public void selectServer(int server) {
        if (state != STATE.SELECTING_SERVER) return;
        this.currentServer = server;
        this.state = STATE.DEALING_WITH_ICE;
    }

    public void reset() {
        state = STATE.LOADING;
        curCycles = maxCycles;
        turns = 0;
        accessedRND = false;
    }
    public void nextTurn() {
        turns++;
        state = STATE.DISCARD;
        curCycles = maxCycles;
    }
}
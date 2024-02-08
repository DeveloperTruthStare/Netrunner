package com.smith.netrunner.GameData;

import com.badlogic.gdx.math.Vector2;
import com.smith.netrunner.Corporation.Corporation;
import com.smith.netrunner.GameData.WorldLine;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;

public class World {
    public static Region GenerateRegion(WorldLine worldline, int region) {

        return new Region(worldline);
    }
    public Runner player;
    public int currentRegion = 0;
    private final int MAX_REGIONS = 3;
    public Region[] regions = new Region[MAX_REGIONS];
    public World(WorldLine worldline) {
        for(int i = 0; i < MAX_REGIONS; ++i) {
            regions[i] = GenerateRegion(worldline, i);
        }

        regions[0].revealAroundPlayer();

        // Initialize player
        player = new Runner(worldline);
    }
    public Region getCurrentRegion() {
        return regions[currentRegion];
    }
    public void setPlayerPosition(Vector2 pos) {
        regions[currentRegion].playerPosition = pos;
    }
    public void revealBossLocation() {
        regions[currentRegion].revealBossLocation();
    }
    public Corporation getCurrentCorporation() {
        return regions[currentRegion].getCurrentCorporation();
    }
    public void battle() {
        regions[currentRegion].battle();
    }
}

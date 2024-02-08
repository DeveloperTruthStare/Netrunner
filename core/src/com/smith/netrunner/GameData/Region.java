package com.smith.netrunner.GameData;

import com.badlogic.gdx.math.Vector2;
import com.smith.netrunner.Corporation.Corporation;

import java.awt.Point;
import java.util.Random;

public class Region {
    public final int worldSize = 7;
    public Corporation[][] corps = new Corporation[worldSize][worldSize];
    public Vector2 playerPosition;
    public Vector2 bossPosition;
    private Random random;

    public Region(WorldLine worldline) {
        for(int i = 0; i < worldSize; ++i) {
            corps[i] = new Corporation[worldSize];
            for(int j = 0; j < worldSize; ++j) {
                if (i == 0 || i == worldSize-1 || j == 0 || j == worldSize-1) {
                    corps[i][j] = Corporation.GenerateStartup();
                    corps[i][j].difficulty = 2;
                } else if (i == 1 || i == worldSize-2 || j == 1 || j == worldSize-2) {
                    corps[i][j] = Corporation.GenerateRandom(worldline);
                    corps[i][j].difficulty = 1;

                } else {
                    corps[i][j] = Corporation.GenerateRandom(worldline);
                    corps[i][j].difficulty = 0;
                }

            }
        }
        corps[3][3] = Corporation.GenerateStartingNode();

        playerPosition = new Vector2(3, 3);
        revealAroundPlayer();

        random = new Random();
        // Set the boss somewhere on the middle row
        int side = random.nextInt(0, 4);
        int pos = random.nextInt(1, worldSize-2);

        switch (side) {
            case 0:
                corps[1][pos] = Corporation.GenerateBoss(worldline, 1);
                bossPosition = new Vector2(1, pos);
                break;
            case 1:
                corps[worldSize-2][pos] = Corporation.GenerateBoss(worldline, 1);
                bossPosition = new Vector2(worldSize-2, pos);
                break;
            case 2:
                corps[pos][1] = Corporation.GenerateBoss(worldline, 1);
                bossPosition = new Vector2(pos, 1);
                break;
            case 3:
                corps[pos][worldSize-2] = Corporation.GenerateBoss(worldline, 1);
                bossPosition = new Vector2(pos, worldSize-2);
                break;
        }

        side = random.nextInt(0, 4);
        pos = random.nextInt(0, worldSize-1);
        switch (side) {
            case 0:
                corps[0][pos].hasDecryptedKey = true;
                break;
            case 1:
                corps[worldSize-1][pos].hasDecryptedKey = true;
                break;
            case 2:
                corps[pos][0].hasDecryptedKey = true;
                break;
            case 3:
                corps[pos][worldSize-1].hasDecryptedKey = true;
                break;
        }
    }
    public void battle() {
        int px = (int)playerPosition.x;
        int py = (int)playerPosition.y;
        corps[px][py].battled = true;
        revealAroundPlayer();
    }

    public void revealBossLocation() {
        corps[(int)bossPosition.x][(int)bossPosition.y].revealed = true;
    }
    public Corporation getCurrentCorporation() {
        return corps[(int)playerPosition.x][(int)playerPosition.y];
    }

    public void revealAroundPlayer() {
        int px = (int)playerPosition.x;
        int py = (int)playerPosition.y;
        if (revealLeft(px, py)) {
            if (px % 2 == 0) {
                revealUp(px-1, py);
            } else {
                revealDown(px-1, py);
            }
        }
        if (revealRight(px, py)) {
            if (px % 2 == 0) {
                revealUp(px+1, py);
            } else {
                revealDown(px+1, py);
            }
        }
        revealUp(px, py);
        revealDown(px, py);
    }
    public boolean revealUp(int x, int y) {
        if (y < worldSize-1) {
            corps[x][y+1].revealed = true;
            return true;
        }
        return false;
    }
    public boolean revealDown(int x, int y) {
        if (y > 0) {
            corps[x][y-1].revealed = true;
            return true;
        }
        return false;
    }
    public boolean revealLeft(int x, int y) {
        if (x > 0) {
            corps[x-1][y].revealed = true;
            return true;
        }
        return false;
    }
    public boolean revealRight(int x, int y) {
        if (x < worldSize-1) {
            corps[x+1][y].revealed = true;
            return true;
        }
        return false;
    }

}

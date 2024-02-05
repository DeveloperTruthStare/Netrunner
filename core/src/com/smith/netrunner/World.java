package com.smith.netrunner;

import com.smith.netrunner.Corporation.Corporation;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;

public class World {
    public final int worldSize = 7;
    public Corporation[][] corps = new Corporation[worldSize][worldSize];
    public Point2D playerPosition = new Point();
    public Random random;
    public Point2D bossPosition;
    public World() {
        random = new Random();

        for (int i = 0; i < worldSize; ++i) {
            corps[i] = new Corporation[worldSize];
            for(int j = 0; j < worldSize; ++j) {
                if (i == 0 || i == worldSize-1 || j == 0 || j == worldSize-1) {
                    corps[i][j] = Corporation.GenerateStartup();
                    corps[i][j].difficulty = 2;
                } else if (i == 1 || i == worldSize-2 || j == 1 || j == worldSize-2) {
                    corps[i][j] = Corporation.GenerateRandom();
                    corps[i][j].difficulty = 1;

                } else {
                    corps[i][j] = Corporation.GenerateRandom();
                    corps[i][j].difficulty = 0;
                }
            }
        }


        corps[3][3] = Corporation.GenerateStartingNode();

        playerPosition = new Point(3, 3);
        revealAroundPlayer();

        // Set the boss somewhere on the middle row
        int side = random.nextInt(0, 4);
        int pos = random.nextInt(1, worldSize-2);

        switch (side) {
            case 0:
                corps[1][pos] = Corporation.GenerateBoss(1);
                bossPosition = new Point(1, pos);
                break;
            case 1:
                corps[worldSize-2][pos] = Corporation.GenerateBoss(1);
                bossPosition = new Point(worldSize-2, pos);
                break;
            case 2:
                corps[pos][1] = Corporation.GenerateBoss(1);
                bossPosition = new Point(pos, 1);
                break;
            case 3:
                corps[pos][worldSize-2] = Corporation.GenerateBoss(1);
                bossPosition = new Point(pos, worldSize-2);
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
    public Corporation getCurrentCorporation() {
        return corps[(int)playerPosition.getX()][(int)playerPosition.getY()];
    }
    public void battle() {
        int px = (int)playerPosition.getX();
        int py = (int)playerPosition.getY();
        corps[px][py].battled = true;
        revealAroundPlayer();
    }
    private void revealAroundPlayer() {
        int px = (int)playerPosition.getX();
        int py = (int)playerPosition.getY();
        if (px != 0) {
            corps[px-1][py].revealed = true;
        }
        if (px != worldSize-1) {
            corps[px+1][py].revealed = true;
        }
        if (py != 0) {
            corps[px][py-1].revealed = true;
        }
        if (py != worldSize-1) {
            corps[px][py+1].revealed = true;
        }
    }
    public boolean moveRight() {
        int px = (int)playerPosition.getX();
        int py = (int)playerPosition.getY();
        if (px == worldSize-1) return false;
        if (corps[px+1][py].revealed) {
            playerPosition = new Point(px+1, py);
            return true;
        }
        return false;
    }
    public boolean moveLeft() {
        int px = (int)playerPosition.getX();
        int py = (int)playerPosition.getY();
        if (px == 0) return false;
        if (corps[px-1][py].revealed) {
            playerPosition = new Point(px-1, py);
            return true;

        }
        return false;
    }
    public boolean moveUp() {
        int px = (int)playerPosition.getX();
        int py = (int)playerPosition.getY();
        if (py == worldSize-1) return false;
        if (corps[px][py+1].revealed) {
            playerPosition = new Point(px, py+1);
            return true;
        }
        return false;
    }
    public boolean moveDown() {
        int px = (int)playerPosition.getX();
        int py = (int)playerPosition.getY();
        if (py == 0) return false;
        if (corps[px][py-1].revealed) {
            playerPosition = new Point(px, py-1);
            return true;
        }
        return false;
    }
}

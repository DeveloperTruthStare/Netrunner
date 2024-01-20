package com.smith.netrunner.GameData;

public interface IHoverableCallback {
    public void onHover(String textToDisplay, int posX, int posY);
    public void onHoverEnd();
}

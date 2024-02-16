package com.smith.netrunner;

import com.smith.netrunner.Corporation.RunTarget;
import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.GameData.Server;
import com.smith.netrunner.HardwareRig.HardwareView;

public class UIState {
    public static HardwareView hoveredHardware;
    public static Card hoveredCard;
    public static int hoveredCardIndex;
    public static RunTarget hoveredServer;
    public static HardwareView selectedIceBreaker;

    public static RunTarget attackingServer;
    public static HardwareView attackingIceBreaker;

    public static void reset() {
        hoveredHardware = null;
        hoveredCardIndex = -1;
        hoveredCard = null;
        hoveredServer = null;
        selectedIceBreaker = null;
        attackingServer = null;
    }
}

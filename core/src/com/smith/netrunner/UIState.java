package com.smith.netrunner;

import com.smith.netrunner.GameData.Card;
import com.smith.netrunner.HardwareRig.HardwareView;

public class UIState {
    public static HardwareView hoveredHardware;
    public static Card hoveredCard;
    public static int hoveredCardIndex;

    public static void reset() {
        hoveredHardware = null;
        hoveredCardIndex = -1;
        hoveredCard = null;
    }
}

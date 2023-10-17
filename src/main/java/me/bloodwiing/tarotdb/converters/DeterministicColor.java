package me.bloodwiing.tarotdb.converters;

import javafx.scene.paint.Color;

public final class DeterministicColor {
    public static float hueFromObject(Object o) {
        int hash = o.hashCode();
        return hash % 360;
    }

    public static Color colorFromObject(Object o) {
        return Color.hsb(hueFromObject(o), 0.8, 1.0);
    }

    public static String colorToHSB(Color color) {
        return "hsb(" + color.getHue() + ", " + color.getSaturation() * 100.0 + "%, " + color.getBrightness() * 100.0 + "%)";
    }
}

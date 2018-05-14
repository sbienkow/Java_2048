package sbienkow.Game2048;

import java.awt.*;

/**
 * Helper class to store the value of the tile and it's colors based on it.
 *
 * @author Szymon Bieńkowski
 */
public class Tile {

    int value;

    Tile() {
        this(0);
    }

    Tile(int num) {
        value = num;
    }

    public boolean isEmpty() {
        return value == 0;
    }

    public Color getForeground() {
        return value < 16
                ? new Color(0x777e66)
                : new Color(0xf8f7f0);
    }

    public Color getBackground() {
        switch (value) {
            case 2:
                return new Color(0xefe5db);
            case 4:
                return new Color(0xeee2c9);
            case 8:
                return new Color(0xf2b179);
            case 16:
                return new Color(0xf59563);
            case 32:
                return new Color(0xf67c5f);
            case 64:
                return new Color(0xf65e3b);
            case 128:
                return new Color(0xedcf72);
            case 256:
                return new Color(0xedcc61);
            case 512:
                return new Color(0xedc850);
            case 1024:
                return new Color(0xedc53f);
            case 2048:
                return new Color(0xedc22e);
            default:
                return new Color(0xcdc1b4);
        }
    }

    @Override
    public String toString() {
        return "Tile(value=" + value + ")";
    }
}

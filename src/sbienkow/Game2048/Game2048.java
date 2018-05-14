package sbienkow.Game2048;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Controlls the game logic.
 *
 * @author Szymon Bieńkowski
 *
 */
public class Game2048 {

    public Tile[] getMyTiles() {
        return myTiles;
    }

    private Tile[] myTiles;
    private int score = 0;

    public int getScore() {
        return score;
    }

    public boolean isFinished() {
        return !canMove();
    }

    /**
     * Checks if 2 lines are equal.
     * @param line1 Tile[] to compare to line2.
     * @param line2 Tile[] to compare to line1.
     */
    private boolean areLinesEqual(Tile[] line1, Tile[] line2) {
        if (line1 == line2) {
            return true;
        } else if (line1.length != line2.length) {
            return false;
        }

        for (int i = 0; i < line1.length; i++) {
            if (line1[i].value != line2[i].value) {
                return false;
            }
        }
        return true;
    }

    /**
     * Moves the board to the left, merging tiles, if possible.
     */
    public void moveLeft() {
        boolean needAddTile = false;
        for (int i = 0; i < 4; i++) {
            Tile[] line = getLine(i);
            Tile[] merged = moveLine(mergeLine(moveLine(line)));
            setLine(i, merged);
            if (!areLinesEqual(line, merged)) {
                needAddTile = true;
            }
        }

        if (needAddTile) {
            addTile();
        }
    }

    /**
     * Moves the board to the right, merging tiles, if possible.
     */
    public void moveRight() {
        myTiles = rotate(180);
        moveLeft();
        myTiles = rotate(180);
    }

    /**
     * Moves the board up, merging tiles, if possible.
     */
    public void moveUp() {
        myTiles = rotate(270);
        moveLeft();
        myTiles = rotate(90);
    }

    /**
     * Moves the board down, merging tiles, if possible.
     */
    public void moveDown() {
        myTiles = rotate(90);
        moveLeft();
        myTiles = rotate(270);
    }

    private Tile[] moveLine(Tile[] oldLine) {
        LinkedList<Tile> l = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            if (!oldLine[i].isEmpty())
                l.addLast(oldLine[i]);
        }
        if (l.size() == 0) {
            return oldLine;
        } else {
            Tile[] newLine = new Tile[4];
            ensureSize(l, 4);
            for (int i = 0; i < 4; i++) {
                newLine[i] = l.removeFirst();
            }
            return newLine;
        }
    }


    private Tile[] mergeLine(Tile[] oldLine) {

        LinkedList<Tile> list = new LinkedList<>();
        for (int i = 0; i < 4; ++i) {
            int num = oldLine[i].value;
            if (i < 3 && oldLine[i].value == oldLine[i + 1].value) {
                num *= 2;
                score += num;
                oldLine[i + 1].value = 0;
            }
            list.add(new Tile(num));
        }
        if (list.size() == 0) {
            return oldLine;
        } else {
            ensureSize(list, 4);
            return list.toArray(new Tile[4]);
        }
    }

    /**
     * Ensures that the List l has at least s Tiles.
     * @param l the list to ensure the size of which.
     * @param s the minimal number of Tiles the list should contain.
     */
    private static void ensureSize(List<Tile> l, int s) {
        while (l.size() < s) {
            l.add(new Tile());
        }
    }

    /**
     * Extracts line of Tiles from the board.
     * @param index the index to extract the line from.
     * @return Tile[] containing tiles from given index.
     */
    private Tile[] getLine(int index) {
        Tile[] result = new Tile[4];
        for (int i = 0; i < 4; i++) {
            result[i] = tileAt(i, index);
        }
        return result;
    }

    /**
     * Sets line of Tiles to the board.
     * @param index the starting index to set the line to.
     * @param re Tile[] to replace the tiles in game board.
     */
    private void setLine(int index, Tile[] re) {
        System.arraycopy(re, 0, myTiles, index * 4, 4);
    }

    /**
     * Rotates the game board.
     * @param angle by which to rotate the game board.
     * @return new Tile[] game board.
     */
    private Tile[] rotate(int angle) {
        assert angle == 0 || angle == 90 || angle == 180 || angle == 270;
        Tile[] newTiles = new Tile[4 * 4];
        int offsetX = 3, offsetY = 3;
        if (angle == 90) {
            offsetY = 0;
        } else if (angle == 270) {
            offsetX = 0;
        }

        double rad = Math.toRadians(angle);
        int cos = (int) Math.cos(rad);
        int sin = (int) Math.sin(rad);
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                int newX = (x * cos) - (y * sin) + offsetX;
                int newY = (x * sin) + (y * cos) + offsetY;
                newTiles[(newX) + (newY) * 4] = tileAt(x, y);
            }
        }
        return newTiles;
    }

    public void resetGame() {
        score = 0;
        myTiles = new Tile[4 * 4];
        for (int i = 0; i < myTiles.length; i++) {
            myTiles[i] = new Tile();
        }
        addTile();
        addTile();
    }

    private List<Tile> availableSpace() {
        final List<Tile> list = new ArrayList<>(16);
        for (Tile t : myTiles) {
            if (t.isEmpty()) {
                list.add(t);
            }
        }
        return list;
    }

    private boolean isFull() {
        return availableSpace().isEmpty();
    }

    boolean canMove() {
        if (!isFull()) {
            return true;
        }
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Tile t = tileAt(x, y);
                if ((x < 3 && t.value == tileAt(x + 1, y).value)
                        || ((y < 3) && t.value == tileAt(x, y + 1).value)) {
                    return true;
                }
            }
        }
        return false;
    }


    private Tile tileAt(int x, int y) {
        return myTiles[x + y * 4];
    }

    private void addTile() {
        List<Tile> list = availableSpace();
        if (!availableSpace().isEmpty()) {
            int index = (int) (Math.random() * list.size()) % list.size();
            Tile emptyTile = list.get(index);
            emptyTile.value = Math.random() < 0.9 ? 2 : 4;
        }
    }


}

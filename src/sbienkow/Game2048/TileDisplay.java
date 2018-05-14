package sbienkow.Game2048;



import javax.swing.*;
import java.awt.*;

/**
 * Displays the game board to the player.
 *
 * @author Szymon Bieńkowski
 */
public class TileDisplay extends JPanel{
    private static final Color BG_COLOR = new Color(0xbdaea8);
    private static final String FONT_NAME = "Arial";
    private static final int TILE_SIZE = 64;
    private static final int TILES_MARGIN = 16;
    public static final DisplaySize PREFERRED_SIZE = new DisplaySize(340, 400);

    public void setMyTiles(Tile[] myTiles) {
        this.myTiles = myTiles;
    }

    private Tile myTiles[];

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    private boolean isFinished = false;

    public void setScore(int score) {
        this.score = score;
    }

    private int score = 0;

    public static final class DisplaySize {
        final int width;
        final int height;
        DisplaySize(int width, int height) {this.width=width; this.height=height;}
    }

    TileDisplay(Tile[] tiles) {
        myTiles = tiles;
        setPreferredSize(new Dimension(PREFERRED_SIZE.width, PREFERRED_SIZE.height));
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                drawTile(g, myTiles[x + y * 4], x, y);
            }
        }

        if (isFinished) {
            g.setColor(new Color(255, 255, 255, 30));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(new Color(78, 139, 202));
            g.setFont(new Font(FONT_NAME, Font.BOLD, 48));

            g.drawString("Game over!", 20, 130);
            g.drawString("You lose!", 60, 200);

            g.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
            g.setColor(new Color(128, 128, 128, 192));
            g.drawString("Press ESC to play again", 80, getHeight() - 40);

        }
        g.setColor(new Color(64, 64, 64));
        g.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
        g.drawString("Score: " + score, 200, 365);
    }


    private void drawTile(Graphics g2, Tile tile, int x, int y) {
        Graphics2D g = ((Graphics2D) g2);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

        int value = tile.value;
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y);

        g.setColor(tile.getBackground());
        g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, 14, 14);

        if (value != 0 ) {
            g.setColor(tile.getForeground());

            final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
            final Font font = new Font(FONT_NAME, Font.BOLD, size);
            g.setFont(font);

            String s = String.valueOf(value);
            final FontMetrics fm = getFontMetrics(font);

            final int w = fm.stringWidth(s);
            final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];
            g.drawString(s, xOffset + (TILE_SIZE - w) / 2, yOffset + TILE_SIZE - (TILE_SIZE - h) / 2 - 2);
        }
    }

    private static int offsetCoors(int arg) {
        return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN;
    }
}


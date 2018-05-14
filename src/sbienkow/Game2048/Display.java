package sbienkow.Game2048;

import javax.swing.*;


/**
 * Takes care of displaying the game window to the player.
 *
 * @author Szymon Bieńkowski
 */
public class Display {
    public TileDisplay tileDisplay;
    Display(Tile[] tiles)
    {
        tileDisplay = new TileDisplay(tiles);

        JFrame game = new JFrame();
        game.setTitle("2048 Game");
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setSize(TileDisplay.PREFERRED_SIZE.width, TileDisplay.PREFERRED_SIZE.height);
        game.setResizable(false);

        game.add(tileDisplay);

        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }

    public void repaint() {tileDisplay.repaint();}
}

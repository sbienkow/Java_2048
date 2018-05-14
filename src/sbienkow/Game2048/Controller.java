package sbienkow.Game2048;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Controls the input, connects the game and display together.
 *
 *  @author Szymon Bieńkowski
 */
public class Controller {

    private Game2048 game = new Game2048();
    private Display display;

    private Controller(){
        this.display = new Display(game.getMyTiles());
        this.display.tileDisplay.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    game.resetGame();
                }


                if (!game.isFinished()) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            game.moveLeft();
                            break;
                        case KeyEvent.VK_RIGHT:
                            game.moveRight();
                            break;
                        case KeyEvent.VK_DOWN:
                            game.moveDown();
                            break;
                        case KeyEvent.VK_UP:
                            game.moveUp();
                            break;
                    }
                }
                updateDisplay();
                display.repaint();
            }
        });
        resetGame();
    }

    private void resetGame(){
        game.resetGame();
        updateDisplay();
    }

    /**
     * Moves needed data from the game to the display.
     */
    private void updateDisplay() {
        display.tileDisplay.setMyTiles(game.getMyTiles());
        display.tileDisplay.setScore(game.getScore());
        display.tileDisplay.setFinished(game.isFinished());
    }


    public static void main(String[] args) {
        new Controller();
    }

}

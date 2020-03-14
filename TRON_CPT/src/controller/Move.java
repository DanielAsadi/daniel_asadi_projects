package controller;

import model.Pixel;
import model.Player;

public class Move {

    /**
     * Players movements
     */
    public void player(Player p) {
        for (int i = 999; i > 0; i--) {

            // Moves the trace of the lightcycles after it
            p.setPlayerX(i, p.getPlayerX(i - 1));
            p.setPlayerY(i, p.getPlayerY(i - 1));
        }

        // Moves to the left
        if (p.isMovingLeft()) {
            p.setPlayerX(0, p.getPlayerX(0) - Pixel.getPixel());
        }
        // To the right
        if (p.isMovingRight()) {
            p.setPlayerX(0, p.getPlayerX(0) + Pixel.getPixel());
        }
        // Down
        if (p.isMovingDown()) {
            p.setPlayerY(0, p.getPlayerY(0) + Pixel.getPixel());
        }
        // Up
        if (p.isMovingUp()) {
            p.setPlayerY(0, p.getPlayerY(0) - Pixel.getPixel());
        }
    }
}
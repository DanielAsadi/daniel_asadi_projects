package model;

import main.Game;

import java.io.IOException;

public class Grid extends Game {

    // The total of pixels the game could possibly have
    private static int playGrid = (width * height) / (Pixel.getPixel() * Pixel.getPixel());

    /**
     * Launch our JPanel window and start the game loop with " initializeGame(); "
     */
    public Grid() throws IOException {
    }

    /**
     * Used to set the total of pixel we could have
     */
    public static int getGrid() {
        return playGrid;
    }

}
package model;

import java.io.IOException;

public class Pixel  {

    // Define the pixel size of our lightcycle
    private static int pixel = 5;

    /**
     * Launch our JPanel window and start the game loop with " initializeGame(); "
     */
    public Pixel() throws IOException {
    }

    /**
     * Used to get Pixel size of our lightcycles
     */
    public static int getPixel() {
        return pixel;
    }

}
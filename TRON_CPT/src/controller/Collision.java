package controller;

import model.Player;

public class Collision {

    /**
     * Check the collisions between our lightcycles and itself and updating the status variable accordingly
     */
    public String checkCollisions(Player red, Player blue) {

        String status = "noCollision";

        for (int i = 999; i > 0; i--) {
            // Lightcycle can't intersect with itself
            //Checking if beginning of lightcycle hits any point in the middle of its own lightcycle
            if ((red.getPlayerX(0) == red.getPlayerX(i) && (red.getPlayerY(0) == red.getPlayerY(i)))) {
                status = "blueW";

            }
            // Lightcycle can't intersect with p2
            //Checking if beginning of lightcycle hits any point in the middle of the other player's lightcycle
            if ((red.getPlayerX(0) == blue.getPlayerX(i) && (red.getPlayerY(0) == blue.getPlayerY(i)))) {
                status = "blueW";

            }
        }
        // Same check for red
        for (int i = 999; i > 0; i--) {
            // Lightcycle can't intersect with itself
            //Checking if beginning of lightcycle hits any point in the middle of its own lightcycle
            if ((blue.getPlayerX(0) == blue.getPlayerX(i) && (blue.getPlayerY(0) == blue.getPlayerY(i)))) {
                status = "redW";

            }
            // Lightcycle can't intersect with p2
            //Checking if beginning of lightcycle hits any point in the middle of the other player's lightcycle
            if ((blue.getPlayerX(0) == red.getPlayerX(i) && (blue.getPlayerY(0) == red.getPlayerY(i)))) {
                status = "redW";

            }
        }
        if ((blue.getPlayerX(0) == red.getPlayerX(0) && (blue.getPlayerY(0) == red.getPlayerY(0)))) {
            status = "tie";

        }
        return status;
    }

}
package controller;

import main.Game;
import model.Player;

public class Edge {

    /**
     * If the lightcycle intersects with the board edges, the lightcycle continues at the opposite edge
     */
    public void checkEdge(Player p1, Player p2) {
        if (p1.getPlayerY(0) >= Game.height) {
            p1.setPlayerY(0, 0);
        }

        if (p1.getPlayerY(0) < 0) {
            p1.setPlayerY(0, Game.height);
        }

        if (p1.getPlayerX(0) >= Game.width) {
            p1.setPlayerX(0, 0);
        }

        if (p1.getPlayerX(0) < 0) {
            p1.setPlayerX(0, Game.width);
        }

        if (p2.getPlayerY(0) >= Game.height) {
            p2.setPlayerY(0, 0);
        }

        if (p2.getPlayerY(0) < 0) {
            p2.setPlayerY(0, Game.height);
        }

        if (p2.getPlayerX(0) >= Game.width) {
            p2.setPlayerX(0, 0);
        }

        if (p2.getPlayerX(0) < 0) {
            p2.setPlayerX(0, Game.width);
        }
    }
}

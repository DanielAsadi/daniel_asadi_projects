package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *
 */
public class Player {

    // Stores the traces locations for the lightcycles
    private final int[] x = new int[Grid.getGrid()];
    private final int[] y = new int[Grid.getGrid()];

    // Stores direction of the lightcycles
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private BufferedImage p1 = ImageIO.read(new File("res/redL.png"));
    private BufferedImage p2 = ImageIO.read(new File("res/blueR.png"));

    // Set numbers of dots
    private int size = 0;

    public Player() throws IOException {
    }

    /**
     * Get the coordinate X,Y for our Players
     */
    public int getPlayerX(int index) {
        return x[index];
    }

    public int getPlayerY(int index) {
        return y[index];
    }

    /**
     * Set the coordinate X,Y for our Players
     */
    public void setPlayerX(int index, int i) {
        x[index] = i;
    }

    public void setPlayerY(int index, int i) {
        y[index] = i;
    }

    /**
     * Set the movement of our players
     */
    public boolean isMovingLeft() {
        return left;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.left = movingLeft;
    }

    public boolean isMovingRight() {
        return right;
    }

    public void setMovingRight(boolean movingRight) {
        this.right = movingRight;
    }

    public boolean isMovingUp() {
        return up;
    }

    public void setMovingUp(boolean movingUp) {
        this.up = movingUp;
    }

    public boolean isMovingDown() {
        return down;
    }

    public void setMovingDown(boolean movingDown) {
        this.down = movingDown;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int j) {
        size = j;
    }


    public BufferedImage getP1() {
        return p1;
    }

    public void setP1(BufferedImage p1) {
        this.p1 = p1;
    }

    public BufferedImage getP2() {
        return p2;
    }

    public void setP2(BufferedImage p2) {
        this.p2 = p2;
    }
}
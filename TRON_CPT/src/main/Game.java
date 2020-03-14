package main;

import controller.Collision;
import controller.Edge;
import controller.Move;
import model.Pixel;
import model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Game extends JPanel implements ActionListener {

    // Define the width and the height of the window
    public final static int width = 1600;
    public final static int height = 900;
    private static final long serialVersionUID = 1L;
    public static String winner;
    // Variables for time
    public static double chrono = 0;
    public static double chrono2 = 0;
    public static double temps;
    public int scoreR = 0;
    public int scoreB = 0;
    public int roundCount = 0;
    // Offsets for the tron bike images
    public int xOffset1 = 0;
    public int yOffset1 = -10;
    public int xOffset2 = -75;
    public int yOffset2 = -10;

    // Initializing background images
    public Image gBackG = ImageIO.read(new File("res/backG.jpg"));
    public Image gBackG2 = gBackG.getScaledInstance(1600, 900, Image.SCALE_DEFAULT);
    public Image eBackG = ImageIO.read(new File("res/eBackG.gif"));
    public Image eBackG2 = eBackG.getScaledInstance(1600, 900, Image.SCALE_DEFAULT);

    // Check if the game is running or not
    protected boolean isRunning = true;
    Move move = new Move();
    Collision col = new Collision();
    Edge edge = new Edge();
    String status;

    // Record the tick time to make the lightcycles moving
    private Timer timer;

    // Set the game speed
    private int speed = 15;

    // Instances of the 2 players
    private Player player1 = new Player(); // Red player
    private Player player2 = new Player(); // Blue player

    // Initializing player 1 images
    private BufferedImage imgRLeft = ImageIO.read(new File("res/redL.png"));
    private BufferedImage imgRUp = ImageIO.read(new File("res/redU.png"));
    private BufferedImage imgRRight = ImageIO.read(new File("res/redR.png"));
    private BufferedImage imgRDown = ImageIO.read(new File("res/redD.png"));

    // Initializing player 2 images
    private BufferedImage imgBLeft = ImageIO.read(new File("res/blueL.png"));
    private BufferedImage imgBUp = ImageIO.read(new File("res/bLueU.png"));
    private BufferedImage imgBRight = ImageIO.read(new File("res/bLueR.png"));
    private BufferedImage imgBDown = ImageIO.read(new File("res/bLueD.png"));

    /**
     * Launch our JPanel window and start the game loop with " initializeGame(); "
     */
    public Game() throws IOException {
        chrono = java.lang.System.currentTimeMillis();
        addKeyListener(new Keys());
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(width, height));
        initializeGame();
    }

    /**
     * Used to paint our components to the screen
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Drawing background
        g.drawImage(gBackG2, 0, 0, null);
        draw(g);
    }

    /**
     * Start the principal game loop
     */
    public void initializeGame() {

        // Set the lightcycle trail size
        player1.setSize(999);
        player2.setSize(999);

        // Create our player 1 lightcycle
        for (int i = 0; i < player1.getSize(); i++) {
            // Set the initial position of the lightcycle
            player1.setPlayerX(0, 4 * width / 5);
            player1.setPlayerY(0, 4 * height / 5);
        }

        // Create our player 2 lightcycle
        for (int i = 0; i < player2.getSize(); i++) {
            // Set the initial position of the lightcycle
            player2.setPlayerX(0, width / 5);
            player2.setPlayerY(0, height / 5);
        }
        // Set the initial movement of the lightcycle 1 and 2
        player1.setMovingLeft(true);
        player2.setMovingRight(true);

        // Timer to make the game move
        timer = new Timer(speed, this);
        timer.start();
    }

    /**
     * Draw our lightcycles  and  tron bikes called on repaint()
     */
    void draw(Graphics g) {

        // Only draw if the game is running
        if (isRunning == true) {
            // Draw our first lightcycle.
            for (int i = 0; i < player1.getSize(); i++) {
                // Lightcycle color
                g.setColor(Color.RED);
                // Draw our lightcycle with initial position and Rectangular (g.fillRect)
                g.fillRect(player1.getPlayerX(i), player1.getPlayerY(i), Pixel.getPixel(), Pixel.getPixel());
            }
            //Drawing  red player
            g.drawImage(player1.getP1(), player1.getPlayerX(0) + xOffset1, player1.getPlayerY(0) + yOffset1, this);
            // Draw our second lightcycle.
            for (int i = 0; i < player2.getSize(); i++) {
                g.setColor(Color.BLUE);
                g.fillRect(player2.getPlayerX(i), player2.getPlayerY(i), Pixel.getPixel(), Pixel.getPixel());
            }
            //Drawing  blue player
            g.drawImage(player2.getP2(), player2.getPlayerX(0) + xOffset2, player2.getPlayerY(0) + yOffset2, this);
            // Synchronizing the graphic
            Toolkit.getDefaultToolkit().sync();
        } else {
            // End the game if players loose or win
            chrono2 = java.lang.System.currentTimeMillis();
            temps = (chrono2 - chrono) / 1000;
            // End the game with Game over Screen + winner
            //endGame(g);
            inBetweenRound(g);
        }
    }

    void inBetweenRound(Graphics g) {
        roundCount++;
        Font font = new Font("Impact", Font.PLAIN, 200);
        FontMetrics metrics = getFontMetrics(font);
        g.drawImage(eBackG2, 0, 0, null);
        g.setColor(Color.red);
        String message = "ROUND " + roundCount + " STARTING...";
        g.drawString(message, ((width / 2) - (metrics.stringWidth(message) / 2)), 200);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * If the game end, draw an end screen with winner + Game over
     */
    void endGame(Graphics g) {
        // Create a new font instance
        Font font = new Font("Impact", Font.PLAIN, 60);
        FontMetrics metrics = getFontMetrics(font);

        if (status.equals("redW")) {
            g.setColor(Color.red);
            scoreR++;
        } else if (status.equals("blueW")) {
            g.setColor(Color.blue);
            scoreB++;
        } else {
            g.setColor(Color.GREEN);
        }
        g.setFont(font);

        // Create message for game over and winner
        g.drawImage(eBackG2, 0, 0, this);
        String message = winner;
        String message2 = "Game Time : " + temps + " s";
        String message3 = "Red : " + scoreR + "  |  Blue: " + scoreB;

        // Draw the message to the board
        g.drawString(message, ((width / 2) - (metrics.stringWidth(message) / 2)), 80);
        g.drawString(message2, ((width / 2) - (metrics.stringWidth(message2) / 2)), 140);
        g.drawString(message3, ((width / 2) - (metrics.stringWidth(message3) / 2)), 200);
        System.out.println("END");
    }

    /**
     * Run constantly as long as we're in game.
     */
    public void actionPerformed(ActionEvent e) {
        if (isRunning == true) {
            // Getting the status
            status = col.checkCollisions(player1, player2);

            switch (status) {
                case "redW":
                    // Set the winner for the specific interaction
                    winner = "RED WON";
                    // The game end
                    isRunning = false;
                    break;
                case "blueW":
                    winner = "BLUE WON";
                    isRunning = false;
                    break;
                case "tie":
                    winner = "TIE";
                    isRunning = false;
                    break;
            }
            edge.checkEdge(player1, player2);
            move.player(player1);
            move.player(player2);
            // If the game end, stop our timer
            if (!isRunning) {
                timer.stop();
            }
        }
        // Repaint our screen
        repaint();
    }

    /**
     * Set our keys for playing and changing image orientation and offsets if direction changes
     */
    private class Keys extends KeyAdapter {

        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!player1.isMovingRight())) {
                // Changing image
                player1.setP1(imgRLeft);
                // Changing offsets
                yOffset1 = -10;
                xOffset1 = 0;
                // Changing direction
                player1.setMovingLeft(true);
                player1.setMovingUp(false);
                player1.setMovingDown(false);
            }

            if ((key == KeyEvent.VK_RIGHT) && (!player1.isMovingLeft())) {
                // Changing image
                player1.setP1(imgRRight);
                // Changing offsets
                yOffset1 = -10;
                xOffset1 = -75;
                // Changing direction
                player1.setMovingRight(true);
                player1.setMovingUp(false);
                player1.setMovingDown(false);
            }

            if ((key == KeyEvent.VK_UP) && (!player1.isMovingDown())) {
                // Changing image
                player1.setP1(imgRUp);
                // Changing offsets
                xOffset1 = -10;
                yOffset1 = 0;
                // Changing direction
                player1.setMovingUp(true);
                player1.setMovingRight(false);
                player1.setMovingLeft(false);
            }

            if ((key == KeyEvent.VK_DOWN) && (!player1.isMovingUp())) {
                // Changing image
                player1.setP1(imgRDown);
                // Changing offsets
                xOffset1 = -10;
                yOffset1 = -75;
                // Changing direction
                player1.setMovingDown(true);
                player1.setMovingRight(false);
                player1.setMovingLeft(false);
            }

            if ((key == KeyEvent.VK_A) && (!player2.isMovingRight())) {
                // Changing image
                player2.setP2(imgBLeft);
                // Changing offsets
                yOffset2 = -10;
                xOffset2 = 0;
                // Changing direction
                player2.setMovingLeft(true);
                player2.setMovingUp(false);
                player2.setMovingDown(false);
            }

            if ((key == KeyEvent.VK_D) && (!player2.isMovingLeft())) {
                // Changing image
                player2.setP2(imgBRight);
                // Changing offsets
                yOffset2 = -10;
                xOffset2 = -75;
                // Changing direction
                player2.setMovingRight(true);
                player2.setMovingUp(false);
                player2.setMovingDown(false);
            }

            if ((key == KeyEvent.VK_W) && (!player2.isMovingDown())) {
                // Changing image
                player2.setP2(imgBUp);
                // Changing offsets
                xOffset2 = -10;
                yOffset2 = 0;
                // Changing direction
                player2.setMovingUp(true);
                player2.setMovingRight(false);
                player2.setMovingLeft(false);
            }

            if ((key == KeyEvent.VK_S) && (!player2.isMovingUp())) {
                // Changing image
                player2.setP2(imgBDown);
                // Changing offsets
                xOffset2 = -10;
                yOffset2 = -75;
                // Changing direction
                player2.setMovingDown(true);
                player2.setMovingRight(false);
                player2.setMovingLeft(false);
            }

            if (key == KeyEvent.VK_ESCAPE) {
                // Exit game
                System.exit(0);
            }
        }
    }
}
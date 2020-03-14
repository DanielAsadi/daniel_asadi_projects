package main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Our Main class with JFrame
 */
public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor for our GUI
     */
    Main() throws IOException {
        //new Menu();
        // Launch the game by calling Game and so initializeGame();
        add(new Game());
        // Window not resizable
        setResizable(false);
        pack();
        // Title of our Window
        setTitle("TRON");
        setLocationRelativeTo(null);
        // Close the Java process when the Frame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        // Creates a new thread so interface can process itself
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = null;
                try {
                    frame = new Main();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                frame.setVisible(true);
            }
        });
    }
}
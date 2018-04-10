/*
 * Author: Sydney Norman
 * Project: Minesweeper
 * Date: October 1st, 2017
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuView extends JPanel {

    // Labels to display game info
    private JLabel bombLabel, timerLabel;

    // Record Keeping Counts
    private int gameTime = 0;

    // Smiley Button to restart game
    private JButton smileyButton;

    private Bombs.SmileyHandler smileyHandler;

    // Resource loader
    private ClassLoader loader = getClass().getClassLoader();

    // Smiley Icons
    private Icon smileIcon = new ImageIcon(loader.getResource("res/Smile.jpg"));
    private Icon frownIcon = new ImageIcon(loader.getResource("res/Frown.jpg"));
    private Icon coolIcon = new ImageIcon(loader.getResource("res/Cool.jpg"));

    // Game Timer: Will be configured to trigger an event every second
    private Timer gameTimer;

    /*
     * Constructor for menu view.
     *
     * @param   smileyHandler       The handler for the smiley button
     */
    MenuView(Bombs.SmileyHandler smileyHandler) {
        this.smileyHandler = smileyHandler;
    }

    /*
     * Initializes the menu view.
     *
     * @param   bombsLeft       The number of bombs left
     */
    public void initialSetup(int bombsLeft) {
        setBorder(BorderFactory.createLoweredBevelBorder());

        // Set Up the Timer
        gameTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameTime++;
                timerLabel.setText(Integer.toString(gameTime));
            }
        });

        smileyButton = new JButton();
        smileyButton.setIcon(smileIcon);
        smileyButton.setPreferredSize(new Dimension(30, 30));
        smileyButton.addActionListener(smileyHandler);

        bombLabel = new JLabel(Integer.toString(bombsLeft));
        bombLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bombLabel.setPreferredSize(new Dimension(30, 30));
        timerLabel = new JLabel("0");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setPreferredSize(new Dimension(30, 30));
    }

    /*
     * Fills the menu view.
     */
    public void fillView() {
        // Add interface elements to menu view
        setLayout(new FlowLayout());

        // Add interface elements to score view
        add(bombLabel);
        add(smileyButton);
        add(timerLabel);
    }

    /*
     * Resets the menu view for a reset game.
     *
     * @param   bombsLeft       The number of bombs left
     */
    public void resetGame(int bombsLeft) {
        // Reset Smiley Button
        smileyButton.setIcon(smileIcon);

        gameTime = 0;
        timerLabel.setText("0");

        bombLabel.setText(Integer.toString(bombsLeft));

        resetTimer();
    }

    /*
     * Starts the timer.
     */
    public void startTimer() {
        gameTimer.start();
    }

    /*
     * Stops the timer.
     */
    public void stopTimer() {
        gameTimer.stop();
    }

    /*
     * Resets the timer.
     */
    public void resetTimer() {
        gameTimer.restart();
        gameTimer.stop();
    }

    /*
     * Checks if the timer is on.
     *
     * @return      Whether or not the timer is running
     */
    public boolean isTimerOn() {
        return gameTimer.isRunning();
    }

    /*
     * Changes the bomb label
     *
     * @param   bombsLeft       The number of bombs left on the board
     */
    public void changeBoard(int bombsLeft) {
        bombLabel.setText(Integer.toString(bombsLeft));
        removeAll();
        fillView();
    }

    /*
     * Updates the menu view for a game won.
     */
    public void gameWon() {

        // Stop the Timer
        stopTimer();

        // Make Smiley Button Sunglasses Face
        smileyButton.setIcon(coolIcon);
    }

    /*
     * Updates the menu view for a game lost.
     */
    public void gameLost() {
        // Stop the Timer
        stopTimer();

        // Make Smiley Button a Frowny Face
        smileyButton.setIcon(frownIcon);
    }

    /*
     * Retrieves the game time.
     *
     * @return          The game time
     */
    public int getTime() {
        return gameTime;
    }

    /*
     * Updates the bomb label with the given bomb count.
     *
     * @param   bombsLeft       The number of bombs left on the board
     */
    public void updateBombLabel(int bombsLeft) {
        bombLabel.setText(Integer.toString(bombsLeft));
    }
}

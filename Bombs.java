/*
 * Author: Sydney Norman
 * Project: Minesweeper
 * Date: October 1st, 2017
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Bombs extends JFrame {

    // Board Size Constants
    private final static int MAX_CELL_LENGTH = 10;
    private final static int BEGINNER_LENGTH = 5;
    private final static int INTERMEDIATE_LENGTH = 8;
    private final static int EXPERT_LENGTH = 10;

    // Bomb Count Constants
    private final static int MAX_BOMBS = 50;
    private final static int BEGINNER_BOMBS = 5;
    private final static int INTERMEDIATE_BOMBS = 15;
    private final static int EXPERT_BOMBS = 30;

    // Help Menu Text
    private final static String HELP_TEXT = "************ HOW TO PLAY ************"
            + "\n\n"
            + "** Control **"
            + "\n" + "- Left click on a cell to reveal what is under."
            + "\n" + "- Right click on a cell to flag it as a bomb."
            + "\n" + "- Click on a number to reveal all surrounding tiles"
            + "\n" + "    if the number matches the number of surrounding flags."
            + "\n\n"
            + "** Goal **"
            + "\n" + "- Uncover all the cells without touching a bomb."
            + "\n" + "- A cell hides a bomb, number, or empty cell."
            + "\n" + "- The numbers tell you how many mines are in the"
            + "\n" + "    surrounding cells"
            + "\n\n"
            + "** Levels **"
            + "\n" + "- Beginner: " + BEGINNER_LENGTH + " x " + BEGINNER_LENGTH + ", " + BEGINNER_BOMBS + " Bombs"
            + "\n" + "- Intermediate: " + INTERMEDIATE_LENGTH + " x " + INTERMEDIATE_LENGTH + ", " + INTERMEDIATE_BOMBS + " Bombs"
            + "\n" + "- Expert: " + EXPERT_LENGTH + " x " + EXPERT_LENGTH + ", " + EXPERT_BOMBS + " Bombs"
            + "\n" + "- Custom: Up to " + MAX_CELL_LENGTH + " x " + MAX_CELL_LENGTH + ", " + MAX_BOMBS + " Bombs";

    // Window Size Constants
    private final static int CELL_WIDTH = 31;
    private final static int HEADER_HEIGHT = 75;

    // Menu Option Constants
    private final static String EXIT = "Exit";
    private final static String HELP = "Help";
    private final static String NEW = "New";
    private final static String BEGINNER = "Beginner";
    private final static String INTERMEDIATE = "Intermediate";
    private final static String EXPERT = "Expert";
    private final static String CUSTOM = "Custom";

    private static int length = INTERMEDIATE_LENGTH;
    private static int width = INTERMEDIATE_LENGTH;
    private static int bombCount = INTERMEDIATE_BOMBS;
    private static int bombsLeft = bombCount;

    // Layout Objects: Views of the board and the label area
    private static MenuView menuView;
    private static GameView gameView;
    private static MenuBar menuBar;

    // Record Keeping Counts
    private int gameTime = 0;
    private int cellsCleared = 0;

    private boolean gameStarted = false;
    private CustomBombs customBombs;

    /*
     * Constructor for Bombs Game
     */
    public Bombs() {
        // Call the base class constructor
        super("Bombs");

        // ------- MENU BAR -------
        // Setup the Menu Bar
        // Create Menu Handler for Button Pushes
        MenuHandler MH = new MenuHandler();
        SetupMenuHandler SMH = new SetupMenuHandler();
        menuBar = new MenuBar(new MenuHandler(), new SetupMenuHandler());
        this.setJMenuBar(menuBar);
        //setupMenuBar();

        // ------- MENU VIEW -------
        SmileyHandler smileyHandler = new SmileyHandler();

        // Allocate the panel to hold menu interface
        menuView = new MenuView(smileyHandler);    // Used to hold score and timer
        menuView.initialSetup(bombsLeft);
        menuView.fillView();

        // ------- GAME VIEW -------
        gameView = new GameView(new GameBoard(length, width, bombCount, new GameMouseListener()));         // Used to hold main game board
        gameView.initialSetup();

        // ------- CONTAINER -------
        // Get the content pane, onto which everything is added
        Container c = getContentPane();

        // Add panels to the container
        c.add(menuView, BorderLayout.NORTH);
        c.add(gameView, BorderLayout.CENTER);

        // ------- PANEL -------
        setSize(INTERMEDIATE_LENGTH * CELL_WIDTH, INTERMEDIATE_LENGTH * CELL_WIDTH + HEADER_HEIGHT);
        setResizable(false);
        setVisible(true);

    }

    /*
     * Responds to a winning game over.
     */
    private void gameWon() {

        // Stop the Timer and Make Smiley Button Sunglasses Face
        menuView.gameWon();

        // Reveal all the bombs
        gameView.revealBoard();

        // User wins!
        JOptionPane.showMessageDialog(Bombs.this, "You Won After " + gameTime + " Seconds!");
    }

    /*
     * Responds to a losing game over.
     */
    private void gameLost() {
        // Stop the Timer and Make Smiley Button a Frowny Face
        menuView.gameLost();

        // Reveal All the Bombs
        gameView.revealBoard();

        JOptionPane.showMessageDialog(Bombs.this, "You Lost After " + menuView.getTime() + " Seconds!");
    }

    /*
     * Resets the entire game.
     */
    private void resetGame() {
        System.out.println("Resetting Game");

        gameStarted = false;

        // Reset Game Time
        gameTime = 0;

        // Reset cellsCleared
        cellsCleared = 0;

        // Reset bombs left
        bombsLeft = bombCount;

        menuView.resetGame(bombsLeft);

        // Clear and reset cells
        gameView.resetGame();

        revalidate();
        repaint();
    }

    /*
     * Changes the board to match the specs.
     *
     * @param   length      The desired length of the board
     * @param   width       The desired width of the board
     * @paaram  bombCount   The desired bomb count for the board
     */
    private void changeBoard(int length, int width, int bombCount) {

        this.length = length;
        this.width = width;
        this.bombCount = bombCount;
        bombsLeft = bombCount;

        //menuView.changeBoard(bombCount);
        menuView.updateBombLabel(bombCount);

        setSize(width * 31, length * 31 + 75);
        gameView.refresh(length, width, bombCount);

        revalidate();
        repaint();
    }

    /*
     * Opens the custom board menu.
     */
    private void customBoard() {

        // Pause the timer while outside the game
        boolean isTimerOn = false;
        if (menuView.isTimerOn()) {
            isTimerOn = true;
            menuView.stopTimer();
        }

        customBombs = new CustomBombs(new JFrame("Custom Bomb"), new CustomBombHandler());

        if (isTimerOn) {
            menuView.startTimer();
        }
    }

    /*
     * Shows the help menu.
     */
    private void showHelpMenu() {

        // Pause the timer while outside the game
        boolean isTimerOn = false;
        if (menuView.isTimerOn()) {
            isTimerOn = true;
            menuView.stopTimer();
        }

        JOptionPane.showMessageDialog(Bombs.this, HELP_TEXT);

        if (isTimerOn) {
            menuView.startTimer();
        }

    }

    // Inner Class for Menu Handler
    public class MenuHandler implements ActionListener {

        // Handle button events
        public void actionPerformed(ActionEvent event) {
             JMenuItem curMenuItem = (JMenuItem)event.getSource();

             if (curMenuItem.getText().equals(NEW)) {
                 resetGame();
             }
             else if (curMenuItem.getText().equals(EXIT)) {
                 System.exit(0);
             }
             else if (curMenuItem.getText().equals(HELP)) {
                 showHelpMenu();
             }
        }
    }

    // Inner Class for Submenu Handler
    public class SetupMenuHandler implements ActionListener {

        // Handle button events
        public void actionPerformed(ActionEvent event) {
            JRadioButtonMenuItem curMenuItem = (JRadioButtonMenuItem)event.getSource();

            resetGame();

            if (curMenuItem.getText().equals(BEGINNER)) {
                changeBoard(BEGINNER_LENGTH, BEGINNER_LENGTH, BEGINNER_BOMBS);
            }
            else if (curMenuItem.getText().equals(INTERMEDIATE)) {
                changeBoard(INTERMEDIATE_LENGTH, INTERMEDIATE_LENGTH, INTERMEDIATE_BOMBS);
            }
            else if (curMenuItem.getText().equals(EXPERT)) {
                changeBoard(EXPERT_LENGTH, EXPERT_LENGTH, EXPERT_BOMBS);
            }
            else if (curMenuItem.getText().equals(CUSTOM)) {
                customBoard();
            }
        }
    }

    // Performs main game play
    public class GameMouseListener implements MouseListener {
        public void mouseExited(MouseEvent e){}
        public void mouseEntered(MouseEvent e){}
        public void mouseReleased(MouseEvent e){}
        public void mousePressed(MouseEvent e){}
        public void mouseClicked(MouseEvent e){

            // Get the cell clicked on
            Cell curCell = (Cell)e.getSource();

            // Start the Game Timer and Setup the Board
            if (!gameStarted) {
                cellsCleared = 0;
                menuView.startTimer();
                gameView.setup(curCell);
                gameStarted = true;
            }

            // Check if the user right clicked for flag
            if (SwingUtilities.isRightMouseButton(e)) {
                if (curCell.isPressed()) {
                    return;
                }
                gameView.toggleFlag(curCell);
                if (curCell.isFlagged()) {
                    // Decrease bomb count by one
                    bombsLeft--;
                }
                else {
                    bombsLeft++;
                }
                menuView.updateBombLabel(bombsLeft);
            }
            else { // Left Clicked

                // Clear neighboring cells if number cell is clicked
                int neighboringBombCount = curCell.getNeighboringBombCount();
                if ((neighboringBombCount > 0) && (curCell.isCleared()) && (neighboringBombCount == gameView.getNeighboringFlagCount(curCell))) {
                    int neighboringCells = gameView.revealNeighboringCells(curCell);

                    // Check if one of the cleared cells are bombs
                    if (neighboringCells == -1) {
                        // Explode the bomb
                        gameLost();
                        return;
                    }
                    cellsCleared += neighboringCells;
                }
                else if (curCell.isPressed() || curCell.isFlagged()) {
                    // Otherwise, Don't let a user click on a cell that is already pressed
                    return;
                }
                else { // Else, normal, unclicked cell is clicked

                    // Show the Pressed Icon
                    curCell.showIcon();

                    // Check if the cell is a bomb
                    if (curCell.isBomb()) {

                        // Explode the bomb
                        gameLost();

                    } else if (curCell.isNeighbor()) {

                        // If the cell is an ordinary number cell, just reveal it
                        cellsCleared += gameView.clearCell(curCell);

                    } else {

                        // Turn over all Neighboring non-bomb cells
                        int i = gameView.clearBombPath(curCell);
                        cellsCleared += i;
                    }
                }

                if (cellsCleared >= (length * width - bombCount)) {
                    gameWon();
                }

                revalidate();
                repaint();
            }
        }
    };

    // Inner Class for Smiley Button Handler
    public class SmileyHandler implements ActionListener {

        // Handle button event
        public void actionPerformed(ActionEvent event) {
            resetGame();
        }
    }

    // Inner Class for Custom Bombs Handler
    public class CustomBombHandler implements ActionListener {

        // Handle button event
        public void actionPerformed(ActionEvent event) {

            changeBoard(customBombs.getLength(), customBombs.getWidth(), customBombs.getBombCount());

            // Close the Window
            customBombs.close();
        }
    }

    /*
     * Creates the Minesweeper Game
     */
    public static void main(String arg[]) {
        Bombs B = new Bombs();
        B.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

}

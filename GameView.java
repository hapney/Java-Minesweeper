/*
 * Author: Sydney Norman
 * Project: Minesweeper
 * Date: October 1st, 2017
 */

import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {

    // Board Size Constants
    private final static int BEGINNER_LENGTH = 5;
    private final static int INTERMEDIATE_LENGTH = 8;
    private final static int EXPERT_LENGTH = 10;

    // Bomb Count Constants
    private final static int BEGINNER_BOMBS = 5;
    private final static int INTERMEDIATE_BOMBS = 15;
    private final static int EXPERT_BOMBS = 30;

    // Default Value Constants
    private final static int DEFAULT_LENGTH = INTERMEDIATE_LENGTH;
    private final static int DEFAULT_BOMBS = INTERMEDIATE_BOMBS;

    // Core game play objects
    private static GameBoard gameBoard;


    private static int length, width, bombCount;

    /*
     * Constructor for Game View
     *
     * @param   gameBoard       The game board to add to view
     */
    GameView(GameBoard gameBoard) {
        super();
        this.gameBoard = gameBoard;

    }

    /*
     * Sets up the game view board.
     */
    public void initialSetup() {
        // Add the game board to the board layout area
        setLayout(new GridLayout(DEFAULT_LENGTH, DEFAULT_LENGTH, 0, 0));
        gameBoard.fillBoardView(this);
    }

    /*
     * Refreshes the game with new metadata.
     *
     * @param   length      The new desired length
     * @param   width       The new desired width
     * @param   bombCount   The new desired bomb count
     */
    public void refresh(int length, int width, int bombCount) {
        removeAll();
        setLayout(new GridLayout(length, width, 0, 0));
        gameBoard.changeBoard(length, width, bombCount);
        gameBoard.fillBoardView(this);
    }

    /*
     * Clea the bomb path surrounding the current cell.
     *
     * @param   curCell     The cell to clear a path for
     */
    public int clearBombPath(Cell curCell) {

        // Make all non-bomb neighboring cells unclickable
        return gameBoard.clearBombPath(curCell);

    }

    /*
     * Clear the given cell.
     *
     * @param   curCell     The cell to clear
     */
    public int clearCell(Cell curCell) {

        // Clear current cell
        return gameBoard.clearCell(curCell);
    }

    /*
     * Reveal the entire board.
     */
    public void revealBoard() {
        // Reveal all the bombs
        gameBoard.revealBoard();
    }

    /*
     * Reset the game board.
     */
    public void resetGame() {
        // Clear and reset cells
        gameBoard.resetGame();
    }

    /*
     * Setup the game board with the starting cell
     *
     * @param   curCell     The starting cell for the game
     */
    public void setup(Cell curCell) {
        gameBoard.setup(curCell);
    }

    /*
     * Toggle the flag at the current cell.
     *
     * @param   curCell     The cell to toggle
     */
    public void toggleFlag(Cell curCell) {
        gameBoard.toggleFlag(curCell);
    }

    /*
     * Reveal the neighboring cells from the current cell
     *
     * @param   curCell     The current cell to reveal neighboring cells to
     */
    public int revealNeighboringCells(Cell curCell) {
        return gameBoard.revealNeighboringCells(curCell);
    }

    /*
     * Retrieve the count of all neighboring flag cells.
     *
     * @param   curCell     The current cell to count from
     */
    public int getNeighboringFlagCount(Cell curCell) {
        return gameBoard.getNeighboringFlagCount(curCell);
    }

}

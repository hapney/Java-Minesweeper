/*
 * Author: Sydney Norman
 * Project: Minesweeper
 * Date: October 1st, 2017
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.LinkedList;

/*
 * The game board for the Bombs game.
 */
public class GameBoard {

    private final static int MAX_CELL_LENGTH = 10;

    // Metadata
    private int length;
    private int width;
    private int bombCount;

    // Constants: Neighboring cell relative coordinates
    private static final int R_NEIGHBOR_COORDINATES[] = {1, 1, 1, 0, -1, -1, -1, 0};
    private static final int C_NEIGHBOR_COORDINATES[] = {-1, 0, 1, 1, 1, 0, -1, -1};

    // Game Board Array
    private Cell cells[][] = new Cell[MAX_CELL_LENGTH][MAX_CELL_LENGTH];

    // Icon Constants
    private static final int BOMB_ICON = -1;
    private static final int ICON_0 = 0;

    /*
     * Constructor for GameBoard
     *
     * @param   length      The length of the GameBoard
     * @param   width       The width of the GameBoard
     */
    public GameBoard(int length, int width, int totalBombCount, Bombs.GameMouseListener GML) {

        // Fill in the metadata
        this.length = length;
        this.width = width;
        this.bombCount = totalBombCount;

        // Fill the Cell array
        Cell cell;
        for (int r = 0; r < MAX_CELL_LENGTH; r++) {
            for (int c = 0; c < MAX_CELL_LENGTH; c++) {

                cell = new Cell();

                // Set Up Button
                cell.addMouseListener(GML);
                cell.setRow(r);
                cell.setCol(c);

                // Strip Unnecessary Border
                cell.setBorder(new EmptyBorder(0, 0, 0, 0));

                cells[r][c] = cell;
            }
        }
    }

    /*
     * Set up the game board from around the starting cell.
     *
     * @param   cell        The current cell to set up from
     */
    public void setup(Cell cell) {

        // Fill Cells With Bombs
        fillCellsWithBombs(cell.getRow(), cell.getCol());

        // Randomize Cells
        randomizeCells(cell.getRow(), cell.getCol());

        // Fill in Count for All Non-Bomb Cells
        fillNeighborCount();
    }

    /*
     * Fill the entire board with the appropriate bomb neighbor counts.
     */
    private void fillNeighborCount() {

        for (int r = 0; r < length; r++) {
            for (int c = 0; c < width; c++) {
                Cell curCell = cells[r][c];

                if (curCell.isBomb()) {

                    // Increase all the neighbor's bomb counts by 1
                    for (int n = 0; n < R_NEIGHBOR_COORDINATES.length; n++) {
                        int newR = r + R_NEIGHBOR_COORDINATES[n];
                        int newC = c + C_NEIGHBOR_COORDINATES[n];

                        // Check to make sure cell is not out of bounds or already a bomb
                        if (newR >= 0 && newR < length && newC >= 0 && newC < width
                                && !cells[newR][newC].isBomb()) {

                            // Add a bomb to the neighbor
                            cells[newR][newC].addBomb();
                            //cells[newR][newC].setText(Integer.toString(cells[newR][newC].getNeighboringBombCount()));
                            cells[newR][newC].setHiddenIcon(cells[newR][newC].getNeighboringBombCount());
                        }
                    }
                }
            }
        }
    }

    /*
     * Fills the board view with cells
     *
     * @param   view        The view to fill with cells
     */
    public void fillBoardView(JPanel view) {
        for (int r = 0; r < length; r++) {
            for (int c = 0; c < width; c++) {
                view.add(cells[r][c]);
            }
        }
    }

    /*
     * Checks if the selected cell position is neighboring the cell position to avoid.
     *
     * @param   rowToAvoid      The row of the cell position to avoid
     * @param   colToAvoid      The col of the cell position to avoid
     * @param   row             The row of the current cell position
     * @param   col             The col of the current cell position
     * @returns                 Whether or not the cell position is neighboring the cell to avoid
     */
    private boolean isAroundClicked(int rowToAvoid, int colToAvoid, int row, int col) {
        for (int i = 0; i < R_NEIGHBOR_COORDINATES.length; i++) {
            if ((row == (rowToAvoid + R_NEIGHBOR_COORDINATES[i])) && (col == (colToAvoid + C_NEIGHBOR_COORDINATES[i]))) {
                return true;
            }
        }
        if (row == rowToAvoid && col == colToAvoid) {
            return true;
        }
        return false;
    }

    /*
     * Fills the cells with bombs, avoiding the first cell clicked on.
     *
     * @param   rowToAvoid      The row of the cell to avoid
     * @param   colToAvoid      The column of the cell to avoid
     */
    private void fillCellsWithBombs(int rowToAvoid, int colToAvoid) {

        int bombCounter = 0;

        // Iterate through cells, filling the first cells with bombs
        for (int r = 0; r < length; r++) {
            for (int c = 0; c < width; c++) {
                if (bombCounter < bombCount
                        && !isAroundClicked(rowToAvoid, colToAvoid, r, c)) {
                    cells[r][c].setBomb(true);
                    bombCounter++;
                    cells[r][c].setHiddenIcon(BOMB_ICON);
                }
                else {
                    cells[r][c].setHiddenIcon(ICON_0);
                }
            }
        }
    }

    /*
     * Randomize the cell order, avoiding placing a bomb near the first cell clicked on.
     *
     * @param   rowToAvoid      The row of the cell to avoid
     * @param   colToAvoid      The column of the cell to avoid
     */
    private void randomizeCells(int rowToAvoid, int colToAvoid) {

        // Shuffle the cells
        for (int r = 0; r < length; r++) {
            for (int c = 0; c < width; c++) {
                if (!isAroundClicked(rowToAvoid, colToAvoid, r, c)) {
                    int randRIndex, randCIndex;
                    do {
                        randRIndex = (int) (Math.random() * length);
                        randCIndex = (int) (Math.random() * width);
                    } while (isAroundClicked(rowToAvoid, colToAvoid, randRIndex, randCIndex));

                    boolean tempIsBomb = cells[r][c].isBomb();
                    cells[r][c].setBomb(cells[randRIndex][randCIndex].isBomb());
                    cells[randRIndex][randCIndex].setBomb(tempIsBomb);

                    if (cells[r][c].isBomb()) {
                        cells[r][c].setHiddenIcon(BOMB_ICON);
                    } else {
                        cells[r][c].setHiddenIcon(ICON_0);
                    }

                    if (cells[randRIndex][randCIndex].isBomb()) {
                        cells[randRIndex][randCIndex].setHiddenIcon(BOMB_ICON);
                    } else {
                        cells[randRIndex][randCIndex].setHiddenIcon(ICON_0);
                    }
                }
            }
        }
    }

    /*
     * Make Entire Board Unlickable and Visible
     */
    public void revealBoard() {

        // Make Every Cell Unlickable and Visible
        for (int r = 0; r < length; r++) {
            for (int c = 0; c < length; c++) {
                //cells[r][c].setEnabled(false);
                cells[r][c].press();
                cells[r][c].showIcon();
            }
        }
    }

    /*
     * Clear the bomb path for a given cell.
     *
     * @param   cell        The cell to clear a path for
     * @return              The number of cells cleared
     */
    public int clearBombPath(Cell cell) {

        int cellsCleared = 0;

        // Clear cell clicked
        cell.clear();
        cellsCleared++;

        // Add cell to adjacency list
        LinkedList<Cell> adj = new LinkedList();
        adj.add(cell);

        while (adj.size() != 0) {
            Cell tempCell = adj.getFirst();
            adj.removeFirst();

            for (int i = 0; i < C_NEIGHBOR_COORDINATES.length; i++) {
                int newR = tempCell.getRow() + R_NEIGHBOR_COORDINATES[i];
                int newC = tempCell.getCol() + C_NEIGHBOR_COORDINATES[i];

                if (newR >= 0 && newR < length && newC >= 0 && newC < width
                        && !cells[newR][newC].isBomb()
                        && !cells[newR][newC].isCleared()) {
                    if (!cells[newR][newC].isNeighbor()) {
                        adj.add(cells[newR][newC]);
                    }
                    cells[newR][newC].clear();
                    cellsCleared++;
                }
            }
        }

        return cellsCleared;

    }

    /*
     * Make a given cell unclickable and clear it.
     *
     * @param   cell        The cell to clear
     * @return              The number of cells cleared (0 or 1)
     */
    public int clearCell(Cell cell) {
        if (cell.isCleared()) {
            return 0;
        }
        cell.clear();
        return 1;
    }

    /*
     * Reset the game board for a new game.
     */
    public void resetGame() {

        // Reset Cells
        for (int r = 0; r < length; r++) {
            for (int c = 0; c < width; c++) {
                cells[r][c].reset();
            }
        }

    }

    /*
     * Change the board with to given specs.
     *
     * @param   length      The new desired length
     * @param   width       The new desired width
     * @param   bombCount   The new desired bomb count
     */
    public void changeBoard(int length, int width, int bombCount) {

        // Update Metadata
        this.length = length;
        this.width = width;
        this.bombCount = bombCount;
    }

    /*
     * Toggle the flad for the given cell
     *
     * @param   curCell     The cell to toggle flag
     */
    public void toggleFlag(Cell curCell) {
        // Set flag on cell
        if (curCell.isFlagged()) {
            curCell.setFlag(false);
        }
        else {
            curCell.setFlag(true);
        }
    }

    /*
     * Retrieves the number of neighboring cells with flags
     *
     * @param   curCell     The cell to count around
     * @return              The number of neighboring flag cells
     */
    public int getNeighboringFlagCount(Cell curCell) {
        int flagCount = 0;
        for (int i = 0; i < C_NEIGHBOR_COORDINATES.length; i++) {
            int tempRow = curCell.getRow() + R_NEIGHBOR_COORDINATES[i];
            int tempCol = curCell.getCol() + C_NEIGHBOR_COORDINATES[i];
            if (tempRow >= 0 && tempRow < length && tempCol >= 0 && tempCol < width) {
                Cell neighborCell = cells[tempRow][tempCol];

                if (neighborCell.isFlagged()) {
                    flagCount++;
                }
            }
        }
        return flagCount;
    }

    /*
     * Reveals the neighboring cells without flags.
     *
     * @param   curCell     The current cell to reveal around
     * @return              The number of cells revealed, or -1 if a bomb is revealed
     */
    public int revealNeighboringCells(Cell curCell) {
        int clearCount = 0;
        for (int i = 0; i < C_NEIGHBOR_COORDINATES.length; i++) {
            int tempRow = curCell.getRow() + R_NEIGHBOR_COORDINATES[i];
            int tempCol = curCell.getCol() + C_NEIGHBOR_COORDINATES[i];
            if (tempRow >= 0 && tempRow < length && tempCol >= 0 && tempCol < width) {
                Cell neighborCell = cells[tempRow][tempCol];
                if (!neighborCell.isCleared() && !neighborCell.isFlagged()) {
                    clearCount++;
                    if (neighborCell.isBomb()) {
                        return -1;
                    }
                    neighborCell.clear();
                    if (neighborCell.getNeighboringBombCount() == 0) {
                        clearCount += clearBombPath(neighborCell) - 1;
                    }
                }
            }
        }
        return clearCount;
    }

    public void printBoard() {
        System.out.println("**********************************");
        System.out.print("\t");
        for (int c = 0; c < width; c++) {
            System.out.print(c + "\t");
        }
        System.out.print("\n");
        for (int r = 0; r < length; r++) {
            System.out.print(r + "\t");
            for (int c = 0; c < width; c++) {
                if (cells[r][c].isBomb()) {
                    System.out.print("X\t");
                }
                else {
                    System.out.print("-\t");
                }
            }
            System.out.print("\n");
        }
        System.out.println("**********************************");
    }

}

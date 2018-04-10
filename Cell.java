/*
 * Author: Sydney Norman
 * Project: Minesweeper
 * Date: October 1st, 2017
 */

import javax.swing.*;

public class Cell extends JButton {

    private boolean isBomb;
    private int neighboringBombCount = 0;

    private int row, col;

    private boolean isPressed = false;

    // Resource loader
    private ClassLoader loader = getClass().getClassLoader();

    // Icon Constants
    private static final int ICON_0 = 0;
    private static final int ICON_1 = 1;
    private static final int ICON_2 = 2;
    private static final int ICON_3 = 3;
    private static final int ICON_4 = 4;
    private static final int ICON_5 = 5;
    private static final int ICON_6 = 6;
    private static final int ICON_7 = 7;
    private static final int ICON_8 = 8;
    private static final int BOMB_ICON = -1;

    // Icons
    private Icon cellIcon = new ImageIcon(loader.getResource("res/Cell.jpg"));
    private Icon bombIcon = new ImageIcon(loader.getResource("res/Bomb.jpg"));
    private Icon flagIcon = new ImageIcon(loader.getResource("res/Flag.jpg"));
    private Icon cell0Icon = new ImageIcon(loader.getResource("res/0.jpg"));
    private Icon cell1Icon = new ImageIcon(loader.getResource("res/1.jpg"));
    private Icon cell2Icon = new ImageIcon(loader.getResource("res/2.jpg"));
    private Icon cell3Icon = new ImageIcon(loader.getResource("res/3.jpg"));
    private Icon cell4Icon = new ImageIcon(loader.getResource("res/4.jpg"));
    private Icon cell5Icon = new ImageIcon(loader.getResource("res/5.jpg"));
    private Icon cell6Icon = new ImageIcon(loader.getResource("res/6.jpg"));
    private Icon cell7Icon = new ImageIcon(loader.getResource("res/7.jpg"));
    private Icon cell8Icon = new ImageIcon(loader.getResource("res/8.jpg"));

    private Icon hiddenIcon;

    private boolean isCleared = false;
    private boolean isFlagged = false;

    /*
     * Default Constructor
     */
    public Cell() {
        super();
        super.setIcon(cellIcon);
    }

    /*
     * Constructor with operator initialization
     */
    public Cell(boolean isBomb) {
        super();
        this.isBomb = isBomb;
        super.setIcon(cellIcon);
    }

    public boolean isPressed() {
        return isPressed;
    }

    /*
     * Adds a bomb to the neighboring bomb count
     */
    public void addBomb() {
        this.neighboringBombCount++;
    }

    /*
     * Checks if the current cell is a bomb
     *
     * @return          Whether or not the cell is a bomb
     */
    public boolean isBomb() {
        return this.isBomb;
    }

    /*
     * Gets the neighboring bomb cell count for the given cell
     *
     * @return          The number of neighboring bomb cells
     */
    public int getNeighboringBombCount() {
        return this.neighboringBombCount;
    }

    /*
     * Sets the row for the given cell.
     *
     * @param   row     The row number to set for the cell
     */
    public void setRow(int row) {
        this.row = row;
    }

    /*
     * Sets the column for the given cell.
     *
     * @param   col     The column number to set for the cell
     */
    public void setCol(int col) {
        this.col = col;
    }

    /*
     * Gets the row number for the given cell.
     *
     * @return          The row number for the cell
     */
    public int getRow() {
        return row;
    }

    /*
     * Gets the column number for the given cell.
     *
     * @return          The column number for the cell
     */
    public int getCol() {
        return col;
    }

    /*
     * Gets the cleared value for the given cell.
     *
     * @return          Whether or not the cell is cleared
     */
    public boolean isCleared() {
        return this.isCleared;
    }

    /*
     * Sets whether or not the given cell has a bomb.
     *
     * @param   isBomb  Whether or not the cell has a bomb
     */
    public void setBomb(boolean isBomb) {
        this.isBomb = isBomb;
    }

    /*
     * Clears the given cell.
     */
    public void clear() {
        showIcon();
        isCleared = true;
        isPressed = true;
    }

    public void showIcon() {
        setIcon(hiddenIcon);
    }

    /*
     * Gets whether or not the given cell is the neighbor to a bomb.
     *
     * @return          Whether or not the cell is neighboring a bomb
     */
    public boolean isNeighbor() {
        return (neighboringBombCount > 0);
    }

    /*
     * Press the cell button.
     */
    public void press() {
        isPressed = true;
    }

    /*
     * Resets the given cell to default values.
     */
    public void reset() {
        isPressed = false;
        isCleared = false;
        isFlagged = false;
        neighboringBombCount = 0;
        isBomb = false;

        // Reset Cells
        setIcon(cellIcon);
        setHiddenIcon(ICON_0);
    }

    /*
     * Set the hidden icon for the cell.
     *
     * @param   iconConst       The constant to determine the icon type
     */
    public void setHiddenIcon(int iconConst) {
        switch (iconConst) {
            case ICON_0:
                hiddenIcon = cell0Icon;
                break;
            case ICON_1:
                hiddenIcon = cell1Icon;
                break;
            case ICON_2:
                hiddenIcon = cell2Icon;
                break;
            case ICON_3:
                hiddenIcon = cell3Icon;
                break;
            case ICON_4:
                hiddenIcon = cell4Icon;
                break;
            case ICON_5:
                hiddenIcon = cell5Icon;
                break;
            case ICON_6:
                hiddenIcon = cell6Icon;
                break;
            case ICON_7:
                hiddenIcon = cell7Icon;
                break;
            case ICON_8:
                hiddenIcon = cell8Icon;
                break;
            case BOMB_ICON:
                hiddenIcon = bombIcon;
                break;
            default:
                hiddenIcon = cellIcon;
        }
    }

    /*
     * Set the flag to the provided boolean.
     *
     * @param   isFlag      Whether or not to set the flag
     */
    public void setFlag(boolean isFlag) {
        this.isFlagged = isFlag;
        if (isFlag) {
            setIcon(flagIcon);
        }
        else {
            setIcon(cellIcon);
        }
    }

    /*
     * Checks if the flag is set for the given cell.
     *
     * @return          Whether or not the flag is set
     */
    public boolean isFlagged() {
        return isFlagged;
    }
}

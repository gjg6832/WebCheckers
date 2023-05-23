package com.webcheckers.model;

import java.util.Objects;

/**
 * Position.java
 *
 * @author Greg Godlewski
 */
public class Position {

    private int row;
    private int cell;

    /**
     * Creates a position
     *
     * @param row - row number
     * @param column - col number
     */
    public Position(int row, int column){
        this.row = row;
        this.cell = column;
    }

    /**
     * Returns the row number of the Postion
     *
     * @return int row number
     */
    public int getRow(){
        return row;
    }

    /**
     * Returns the column number of the Postion
     *
     * @return int column number
     */
    public int getCell(){
        return cell;
    }

    /**
     * Checks if the positions are the same
     *
     * @param o the second position
     * @return if o is in the same position as this object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return row == position.row &&
                cell == position.cell;
    }
}

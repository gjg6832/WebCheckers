package com.webcheckers.model;


/**
 * Class that contains the change in a piece's position when move is made
 *
 * @author Shrif Rai
 * @author Greg Godlewski
 */
public class Move {
    // position before and after a move
    private Position start, end;

    /**
     * Creates a move with a start pos and end pos
     *
     * @param start - start pos
     * @param end - end pos
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }


    /**
     * gets the start pos
     *
     * @return - start
     */
    public Position getStart() {
        return this.start;
    }

    /**
     * gets the end pos
     *
     * @return end
     */
    public Position getEnd() {
        return this.end;
    }

    /**
     * Checks if moves are the same
     *
     */
    public boolean equals(Object obj) {
        boolean isPosition = obj instanceof Position;
        if(isPosition) {
            Move move = (Move) obj;
            return this.start.equals(move.start) && this.end.equals(move.end);

        }
        return false;
    }

}

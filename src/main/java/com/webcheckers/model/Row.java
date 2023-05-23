package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The row iterates over spaces
 *
 * @author Ethan Yaccarino-Mims
 * @author Shrif Rai
 * @author Greg Godlewski
 */
public class Row implements Iterable<Space> {

    private final int NUM_SPACES = 8;
    private int row;

    //starts at 1, row 1 has white pieces.
    private List<Space> spaces;


    /**
     * Check if space should be initialized with a piece
     *
     * @param rowNumber row number
     * @param colNumber column number
     * @return true or false
     */
    public boolean hasPiece(int rowNumber, int colNumber) {
        if(rowNumber == 3 || rowNumber == 4) {
            return false;
        }
        else if(rowNumber % 2 == 0 && colNumber % 2 != 0) {
            return true;
        }
        else {
            return rowNumber % 2 != 0 && colNumber % 2 == 0;
        }
    }



    /**
     * Overlaods constructor
     *
     * @param index - row number
     * @param spaces - row
     */
    public Row(int index, ArrayList<Space> spaces){
        this.row = index;
        this.spaces = spaces;
    }


    public Row( int indexRow ) {
        this.row = indexRow;
        this.spaces = new ArrayList<>();

        for( int i = 0; i <= 7; i++ ) {
            if( indexRow == 0 && i % 2 == 1 ) {
                spaces.add( new Space(new Piece( Piece.PieceColor.WHITE,
                        Piece.PieceType.SINGLE ), Space.SpaceColor.BLACK, i ) );
            } else if( indexRow == 1 && i % 2 == 0 ) {
                spaces.add( new Space( new Piece( Piece.PieceColor.WHITE,
                        Piece.PieceType.SINGLE ), Space.SpaceColor.BLACK, i ) );
            } else if( indexRow == 2 && i % 2 == 1 ) {
                spaces.add( new Space(new Piece( Piece.PieceColor.WHITE ,
                        Piece.PieceType.SINGLE ), Space.SpaceColor.BLACK, i ) );
            } else if( indexRow == 3 && i % 2 == 0 ) {
                spaces.add( new Space( null, Space.SpaceColor.BLACK, i) );
            } else if( indexRow == 4 && i % 2 == 1 ) {
                spaces.add( new Space(null, Space.SpaceColor.BLACK,i) );
            } else if( indexRow == 5 && i % 2 == 0 ) {
                spaces.add( new Space(new Piece( Piece.PieceColor.RED,
                        Piece.PieceType.SINGLE ), Space.SpaceColor.BLACK, i ) );
            } else if( indexRow == 6 && i % 2 == 1 ) {
                spaces.add( new Space(new Piece( Piece.PieceColor.RED,
                        Piece.PieceType.SINGLE ), Space.SpaceColor.BLACK, i) );
            } else if( indexRow == 7 && i % 2 == 0 ) {
                spaces.add( new Space(new Piece( Piece.PieceColor.RED,
                        Piece.PieceType.SINGLE ), Space.SpaceColor.BLACK,i ) );
            } else {
                spaces.add( new Space( null, Space.SpaceColor.WHITE, i ) );
            }
        }
    }


    /**
     * Get a space at col
     * @param col column
     * @return Space at column
     */
    public Space getSpace(int col) {
        return spaces.get(col);
    }
    /**
     * Returns the row
     *
     * @return row
     */
    public List<Space> getSpaces() {
        return spaces;
    }


    /**
     * Returns the index of the row
     *
     * @return the index of the row
     */
    public int getIndex() {
        return row;
    }

    /**
     * Gets the piece type
     *
     * @param colNum - col number
     * @return - the piece type
     */
    public Piece.PieceType getPieceType( int colNum ) {
        Space space = spaces.get( colNum );
        return space.getPiece().getType();
    }

    /**
     * Gets the piece color
     *
     * @param colNum - col number
     * @return - the pieces color
     */
    public Piece.PieceColor getPieceColor( int colNum ) {
        Space space = spaces.get( colNum );
        return space.getPiece().getColor();
    }

    /**
     * Places a piece
     *
     * @param col - col index
     * @param type - piece type
     * @param color - piece color
     */
    public void placePiece(int col,Piece.PieceType type, Piece.PieceColor color){
        spaces.get(col).placePiece(type, color);
    }

    /**
     * removes the piece
     *
     * @param col - the col index
     */
    public void removePiece( int col ){
        spaces.get(col).emptySpace();
    }

    /**
     * if the space is empty or not
     *
     * @param colNum - the col number
     * @return - if the space is empty or not
     */
    public boolean isEmptySpace(int colNum) {
        Space space = spaces.get(colNum);
        return space.isEmptySpace();
    }


    /**
     * Returns the Iterator of Spaces
     *
     * @return the Iterator of Spaces
     */
    public Iterator<Space> iterator() {
        return spaces.iterator();
    }

    /**
     * Reverses a row
     *
     * @param row - the existing row
     * @return the reversed row
     */
    public static Row reversedRow(Row row ) {
        int index = row.getIndex();
        List<Space> oldSquares = row.getSpaces();
        ArrayList<Space> squares = new ArrayList<>();

        for( int i = 7; i >= 0; --i ) {
            squares.add( oldSquares.get( i ) );
        }
        return new Row( index, squares );
    }

}

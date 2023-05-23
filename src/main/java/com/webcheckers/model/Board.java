package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * The board iterates over rows
 *
 * @author <a href='mailto:eay4074@rit.edu'>Ethan Yaccarino-Mims</a>
 * @author Greg Godlewski
 */
public class Board implements Iterable<Row> {

    private final int NUM_ROWS = 8;
    private final int NUM_COLS = 8;


    //starts at 1
    private List<Row> rows = new ArrayList<Row>();

    /**
     * Create the board to handle the rows
     */
    public Board() {
        //creates the new rows to add into the board
        for(int i = 0; i < NUM_ROWS; i++) {
            rows.add(new Row(i));
        }
    }

    /**
     * Return the rows within the board
     *
     * @return list of rows
     */
    public List<Row> getRows() {
        return this.rows;
    }

    /**
     * Get position of all pieces of a certain color on the board
     *
     * @param color color of the pieces
     * @return list of position of pieces of a single color
     */
    public List<Position> getAllPieceLocations(Piece.PieceColor color) {
        List<Position> posList = new ArrayList<>();

        for(Row row: rows) {
            for(Space space: row.getSpaces()) {
                Piece newPiece = space.getPiece();
                if(newPiece != null && newPiece.getColor() == color)
                    posList.add(new Position(row.getIndex(), space.getCellIdx()));
            }
        }
        return posList;
    }

    /**
     * returns the Iterator of rows
     *
     * @return the Iterator of rows
     */
    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }

    /**
     * Gets the piece type
     *
     * @param pos - the position
     * @return return the piecetype
     */
    public Piece.PieceType getPieceType( Position pos ) {
        int colPos = pos.getCell();
        int rowPos = pos.getRow();
        Row row = rows.get( rowPos );
        return row.getPieceType( colPos );
    }

    /**
     *
     * @param pos - the position
     * @return - the piece color
     */
    public Piece.PieceColor getPieceColor( Position pos ) {
        int colPos = pos.getCell();
        int rowPos = pos.getRow();
        Row row = rows.get(rowPos);
        return row.getPieceColor( colPos );
    }

    /**
     * Whether position is off the board
     *
     * @param pos - the position
     * @return - true or false
     */
    public boolean isOffBoard(Position pos){
        if( pos.getRow() < 0){
            return true;
        } else if(pos.getCell() < 0){
            return true;
        } else if(pos.getRow() >= NUM_ROWS){
            return true;
        } else if(pos.getCell() >= NUM_COLS){
            return true;
        } else {
            return false;
        }
    }

    /**
     * determines a empty space
     *
     * @param pos - the position
     * @return - true or false
     */
    public boolean isEmptySpace(Position pos){
        int rowPos = pos.getRow();
        int colPos = pos.getCell();
        Row row = rows.get(rowPos);
        return row.isEmptySpace(colPos);
    }

    /**
     * gets piece
     *
     * @param position - the position
     * @return the piece
     */
    public Piece getPiece(Position position){
        int rowPos = position.getRow();
        int colPos = position.getCell();
        Row row = rows.get(rowPos);
        Space space = row.getSpace(colPos);
        return space.getPiece();
    }

    /**
     *  Reverses the board
     * @param board the board to be reversed
     * @return - the reversed board
     */
    public static Board getReversedBoard(Board board )
            throws AssertionError {
        assert board != null;
        List<Row> oldRows = board.getRows();
        Board invertedBoard = new Board();
        List<Row> newRows = new ArrayList<>();
        for( int row = 8 - 1; row >= 0; --row ) {
            Row invertedRow = Row.reversedRow( oldRows.get( row ) );
            newRows.add( invertedRow );
        }
        invertedBoard.setBoardRows( newRows );
        return invertedBoard;
    }


    /**
     * Links a new List of rows to the board
     *
     * @param rows rows to be set
     */
    public void setBoardRows( List<Row> rows ) {
        this.rows = rows;
    }

    /**
     * Updates piece position and changes it's space
     *
     * @param start Position of piece at start of turn
     * @param end   Position of piece at end of turn
     */
    public void movePiece( Position start, Position end ) {
        Piece.PieceType type = getPieceType( start );
        Piece.PieceColor color = getPieceColor( start );


        if( type == Piece.PieceType.SINGLE &&
                ( ( color == Piece.PieceColor.RED && end.getRow() == 0 ) ||
                        ( color == Piece.PieceColor.WHITE && end.getRow() == 7 ) ) ) {
            type = Piece.PieceType.KING;
        }

        rows.get( start.getRow() ).removePiece( start.getCell() );
        rows.get( end.getRow() ).placePiece( end.getCell(), type, color );
    }

    /**
     * Get space at position p
     *
     * @param p position of space
     * @return space at p
     */
    public Space getSpace(Position p) {
        return rows.get(p.getRow()).getSpace(p.getCell());
    }

}

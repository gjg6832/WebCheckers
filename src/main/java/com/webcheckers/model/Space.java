package com.webcheckers.model;
import com.webcheckers.model.Piece;

/**
 * The space object represents a space on the board.
 * The space can contain a piece and is either black or white.
 *
 * @author Greg Godlewski, Shrif Rai,
 * @author Ethan Yaccarino-Mims
 *
 */
public class Space {

    /**
     * Color of the space: white or black
     */
    public enum SpaceColor {
        WHITE, BLACK
    }

    // Attributes for Space object
    private Piece piece;
    private final SpaceColor color;
    private final int cellId;

    /**
     * Creates a space on the board with a piece
     *
     * @param piece - the piece on that space
     * @param color - the color of the space
     */
    public Space(Piece piece, SpaceColor color, int cellId){
        this.piece = piece;
        this.color = color;
        this.cellId = cellId;
    }

    /**
     * Returns the index of this space in its' row
     *
     * @return the index of this space
     */
    public int getCellIdx() {
        return cellId;
    }

    /**
     * Returns if there is a piece on this space
     *
     * @return if piece is not null
     */
    public boolean hasPiece() {
        return piece != null;
    }

    /**
     * Returns the Piece on the space
     *
     * @return piece on the space
     */
    public Piece getPiece() {
        return piece;
    }



    /**
     * Makes the piece on this space a king if a piece is there
     */
    public void makePieceKing() {
        if(piece != null){
            piece.makeKing();
        }
    }

    /**
     * Sets the piece on the space
     *
     * @param piece - the piece to be placed
     */
    public void setPiece(Piece piece){
        this.piece = piece;
    }

    /**
     * Checks if the space is black and empty (has no pieces)
     * so that the space is a valid move
     *
     * @return if a piece can move to this space
     */
    public boolean isValid() {
        return  piece == null && color == SpaceColor.BLACK;
    }

    /**
     * makes the space empty
     */
    public void emptySpace() {
        piece = null;
    }

    /**
     * Checks is space is empty
     * @return true or false
     */
    public boolean isEmptySpace(){
        if(piece == null){
            return true;
        }
        return false;
    }

    /**
     * spaces a piece
     *
     * @param type - the type
     * @param color - the color
     */
    public void placePiece(Piece.PieceType type, Piece.PieceColor color){
        this.piece = new Piece(color, type);
    }

    /**
     * Gets color of the space
     *
     * @return color of this space
     */
    public SpaceColor getColor() {
        return this.color;
    }
}

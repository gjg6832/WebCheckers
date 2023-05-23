package com.webcheckers.model;

/**
 * Piece object
 *
 * @author Shrif Rai
 */
public class Piece {

    /**
     * Enums for the colors of pieces on the board
     */
    public enum PieceColor {
        RED, WHITE
    }

    /**
     * Enums for the types of pieces on the board
     */
    public enum PieceType {
        SINGLE, KING
    }

    // color and type attributes
    private PieceColor color;
    private PieceType type;

    /**
     * Create a piece given color and type
     *
     * @param color The color of the piece
     * @param type The type of the piece
     */
    public Piece(PieceColor color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    /**
     * Get the color of the piece
     *
     * @return color of the piece: red or white
     */
    public PieceColor getColor() {
        return this.color;
    }

    /**
     * Get the type of the piece
     *
     * @return type of the piece: single or king
     */
    public PieceType getType() {
        return this.type;
    }

    /**
     * Set the type of the piece. Used when piece becomes king by reaching the
     * last row near oppisite color
     * @param type The new type the piece is set to
     */
    public void PieceType(PieceType type) {
        this.type = type;
    }

    /**
     * makes a piece a king
     */
    public void makeKing(){
        if(type != PieceType.KING){
            type = PieceType.KING;
        }
    }
}

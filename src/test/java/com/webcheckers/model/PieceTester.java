package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import com.webcheckers.model.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


/**
 * The unit test suite for the {@link Piece} component.
 *
 * @author Shrif Rai
 */
@Tag("Model-tier")
public class PieceTester {

    private Piece.PieceColor pieceColor = Piece.PieceColor.RED;
    private Piece.PieceType pieceType = Piece.PieceType.SINGLE;
    private Piece CuT;

    // test if a piece object is constructed correctly
    @Test
    public void construction(){
        Piece p = new Piece(Piece.PieceColor.RED, Piece.PieceType.SINGLE);
        assertNotNull(p);
    }

    // test if a piece's color is assigned and returned correctly
    @Test
    public void testPieceColor(){
        // create a white and red type pieces
        Piece p1 = new Piece(Piece.PieceColor.WHITE, Piece.PieceType.KING);
        Piece p2 = new Piece(Piece.PieceColor.RED, Piece.PieceType.SINGLE);

        // confirm that white and red type pieces are made correctly
        assertEquals(Piece.PieceColor.WHITE, p1.getColor());
        assertEquals(Piece.PieceColor.RED, p2.getColor());
    }

    // test if a piece's type is assigned and returned correctly
    @Test
    public void testPieceType(){
        // create a king and single type pieces
        Piece p1 = new Piece(Piece.PieceColor.WHITE, Piece.PieceType.KING);
        Piece p2 = new Piece(Piece.PieceColor.RED, Piece.PieceType.SINGLE);

        // confirm that king and single type pieces are made correctly
        assertEquals(Piece.PieceType.KING, p1.getType());
        assertEquals(Piece.PieceType.SINGLE, p2.getType());
    }

    @BeforeEach
    public void  setupTest() {
        CuT = new Piece(pieceColor,pieceType);
    }



    /**
     * Test the getColor method
     */
    @Test
    public void getColorTest(){
        assertEquals(CuT.getColor(), Piece.PieceColor.RED);
    }

    /**
     * Test the getType method
     */
    @Test
    public void getTypeTest(){
        assertEquals(CuT.getType(), Piece.PieceType.SINGLE);
    }

    /**
     * Test the PieceType method
     */
    @Test
    public void PieceTypeTest(){
        CuT.PieceType(Piece.PieceType.SINGLE);
        assertEquals(CuT.getType(), Piece.PieceType.SINGLE);
    }

    /**
     * Test the makeKing method
     */
    @Test
    public void makeKingTest(){
        CuT.makeKing();
        assertEquals(CuT.getType(), Piece.PieceType.KING);
    }
}

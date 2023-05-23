package com.webcheckers.model;


import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The unit test suite for the {@link Space} component.
 *
 * @author Hayden Liang
 * @author Greg Godlewski
 */
@Tag("Model-tier")
public class SpaceTester {

    private Space CuT;
    private Space CuT2;
    private Piece piece;

    @BeforeEach
    /**
     * Set up a space
     */
    public void setup(){
        CuT = new Space(null, Space.SpaceColor.BLACK, 0 );
        CuT2 = new Space(null, Space.SpaceColor.WHITE, 0);
        piece = mock(Piece.class);
        when( piece.getType() ).thenReturn(Piece.PieceType.SINGLE);
        when( piece.getColor() ).thenReturn( Piece.PieceColor.RED );
    }

    /**
     * test gets and sets
     */
    @Test
    public void testGetsAndSets(){
        assertNull( CuT.getPiece() );
        CuT.setPiece(  piece );
        assertEquals( piece, CuT.getPiece());
        assertEquals( Piece.PieceColor.RED, CuT.getPiece().getColor());
        assertEquals( Piece.PieceType.SINGLE, CuT.getPiece().getType() );
    }

    /**
     * Tests the get for getCell
     */
    @Test
    public void testCell() {
        assertEquals( 0, CuT.getCellIdx() );
    }

    /**
     * Test king
     */
    @Test
    public void testKing(){
        CuT.placePiece(piece.getType(),piece.getColor());
        assertEquals(Piece.PieceType.SINGLE, CuT.getPiece().getType());
        CuT.makePieceKing();
        when( piece.getType()).thenReturn(Piece.PieceType.KING);
        assertEquals(Piece.PieceType.KING, CuT.getPiece().getType());
    }

    /**
     * Tests has piece
     */
    @Test
    public void testHasPiece() {
        assertFalse(CuT.hasPiece());
        CuT.placePiece(piece.getType(),piece.getColor());
        assertTrue(CuT.hasPiece());
    }

    /**
     * test isValid
     */
    @Test
    public void testIsValid(){
        assertTrue(CuT.isValid());
        assertFalse(CuT2.isValid());
    }

    /**
     * test empty space
     */
    @Test
    public void testEmptySpace(){
        assertTrue( CuT.isEmptySpace() );
        CuT.emptySpace();
        assertNull( CuT.getPiece() );
        assertTrue( CuT.isEmptySpace() );
    }





}

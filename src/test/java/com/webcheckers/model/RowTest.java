package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.*;
import java.util.*;


import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests row class
 * @author Greg Godlewski
 */
@Tag("Model-tier")
public class RowTest {

    private Row CuT;
    private int index = 1;

    /**
     * Ensures that conditions are the same before every test
     */
    @BeforeEach
    public void setUp() {
        index = 1;
        CuT = new Row(index);
    }

    /**
     * Test reversedRow
     */
    @Test
    public void reversedRowTest(){
        Row reversed = Row.reversedRow(CuT);
        List<Space> spaces = CuT.getSpaces();
        List<Space> rSpaces = reversed.getSpaces();
        for ( int col = 0; col < 8; ++col ) {
            assertEquals(spaces.get(col), rSpaces.get(7 - col));
        }
    }


    /**
     * Iterator test
     */
    @Test
    public void iteratorTest() {
        assertTrue(CuT.iterator() instanceof Iterator);
    }


    /**
     * Tests getIndex
     */
    @Test
    public void getIndexTest() {
        assertEquals(index, CuT.getIndex());
    }

    /**
     * Tests haspiece
     */
    @Test
    public void testHasPiece(){
        assertFalse(CuT.hasPiece(0,0));
        assertFalse(CuT.hasPiece(3,3));
        assertTrue(CuT.hasPiece(2,1));
        assertTrue(CuT.hasPiece(1,2));
        assertFalse(CuT.hasPiece(4,1));
    }

    @Test
    public void gets(){
        CuT.placePiece(3, Piece.PieceType.KING, Piece.PieceColor.WHITE);
        CuT.removePiece(3);
        CuT.isEmptySpace(3);
    }







}

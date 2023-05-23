package com.webcheckers.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test for Position
 * @author Greg Godlewski
 */
@Tag("Model-tier")
public class PositionTest {

    private final int row = 1;
    private final int col = 2;
    private Position CuT;

    @BeforeEach
    public void setupTest(){
        CuT = new Position(row,col);
    }

    @Test
    public void testGetRow() {
        assertEquals(1, CuT.getRow());
    }

    @Test
    public void testGetCol() {
        assertEquals(2, CuT.getCell());
    }

    @Test
    public void testConstructor(){
        Position pos = new Position(1,2);
        assertNotNull(pos);
    }

    @Test
    public void testEquals(){
        CuT.equals(CuT);
        CuT.equals(new Position(0, 0));
        CuT.equals(new Player("Kobe"));
    }




}

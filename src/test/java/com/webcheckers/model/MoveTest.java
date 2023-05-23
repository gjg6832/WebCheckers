package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * MoveTest
 * @author Greg Godlewski
 */
@Tag("Model-Tier")
public class MoveTest {

    private Move CuT;
    private Position start= new Position(0,0);
    private Position end = new Position(1,1);

    /**
     * SetuP
     */
    @BeforeEach
    public void setUp(){
        CuT = new Move(start,end);
    }

    /**
     * test equals
     */
    @Test
    public void testEquals(){
        CuT.equals(null);
    }
}

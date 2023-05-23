package com.webcheckers.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests Player class
 * @author Greg Godlewski
 */
@Tag("Model-Tier")
public class PlayerTest {
    Player CuT;

    /**
     * Set a new player for each test
     */
    @BeforeEach
    public void setup(){
        CuT = new Player("P1");
    }

    /**
     * Tests getName
     */
    @Test
    public void testGetName() {
        assertEquals("P1", CuT.getName());
    }

    /**
     * Tests toString
     */
    @Test
    public void testToString(){
        String toString = "P1";
        assertEquals(toString, CuT.toString());
    }
}

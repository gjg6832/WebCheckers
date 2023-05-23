package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Piece;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The unit test suite for the {@link CheckersGame} component.
 *
 * @author <a href='mailto:eay4074@rit.edu'>Ethan Yaccarino-Mims</a>
 */
@Tag("Model-tier")
public class CheckersGameTest {

    /**
     * The component under the test
     */
    private CheckersGame CuT;

    private Player p1;
    private Player p2;
    private MoveList moveList;

    /**
     * Sets up the before each test
     */
    @BeforeEach
    public void setup() {
        p1 = new Player("p1");
        p2 = new Player("p2");
        moveList = mock(MoveList.class);

        CuT = new CheckersGame(p1, p2);
    }

    /**
     * Tests the getColor method
     */
    @Test
    public void getColorTest() {
        assertEquals(CuT.getColor(p1), Piece.PieceColor.RED);
        assertEquals(CuT.getColor(p2), Piece.PieceColor.WHITE);
    }

    /**
     * Tests the getRedPlayer method
     */
    @Test
    public void getRedPlayerTest() {
        assertEquals(CuT.getRedPlayer(), p1);
        assertNotEquals(CuT.getRedPlayer(), p2);
    }

    /**
     * Tests the isPlayersTurn method
     */
    @Test
    public void isPlayersTurnTest() {
        assertTrue(CuT.isPlayersTurn(p1.getName()));
        assertFalse(CuT.isPlayersTurn(p2.getName()));
    }

    /**
     * Tests the getGameBoard method
     */
    @Test
    public void getGameBoardTest() {
        assertNotNull(CuT.getGameBoard(Piece.PieceColor.WHITE));
    }

    /**
     * Tests the nextTurn method
     */
    @Test
    public void nextTurnTest() {
        CuT.nextTurn();
        assertTrue(CuT.isPlayersTurn(p2.getName()));
        assertFalse(CuT.isPlayersTurn(p1.getName()));
        CuT.nextTurn();
    }

    @Test
    public void testRandom(){
        CuT.backupMove();
        CuT.hasMove();
        CuT.getWhitePlayer();
        CuT.makeMove();
        CuT.validateMove(new Move(new Position(0,1),new Position(1,3)));
        CuT.getActiveColor();
    }

}

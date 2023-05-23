package com.webcheckers.appl;


import com.webcheckers.model.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The unit test suite for the {@link GameCenter} component.
 *
 * @author Hayden Liang
 * @author Greg Godlewski
 */
@Tag("Application-tier")
public class GameCenterTester {
    private GameCenter  CuT;
    private CheckersGame game1,game2;
    private Player p1,p2,p3;

    /**
     * setup
     */
    @BeforeEach
    public void setup(){
        p1 = mock( Player.class );
        when(p1.getName()).thenReturn("Greg");
        p2 = mock( Player.class );
        when(p2.getName()).thenReturn("Kobe");
        p3 = mock( Player.class );
        when(p3.getName()).thenReturn("Lebron");

        game1 = mock( CheckersGame.class );

        CuT = new GameCenter();
        when(game1.getRedPlayer()).thenReturn(p1);
        when(game1.getWhitePlayer()).thenReturn(p2);
        CuT.addGame( game1 );
    }


    /**
     * Tests creating a lobby
     */
    @Test
    void testGetLobby() {
        assertNotNull(CuT.getLobby());
    }

    /**
     * test isInAGame
     */
    @Test
    void testIsInAGame(){
        assertTrue(CuT.isInAGame(p1));
    }

    /**
     * test a game with no player in it
     */
    @Test
    void testRetrieveGame(){
        assertEquals(game1,CuT.retrieveGame(p1));
    }

    /**
     *
     */
    @Test
    void testEndGame(){
        assertNotNull(game1);
        assertEquals(game1.getRedPlayer(), p1);
        assertEquals(game1.getWhitePlayer(), p2);
        game2 = CuT.endGame(game1);
        assertNull(game2);
    }

    @Test
    public void addGameTest() {
        Player p4 = new Player("p4");
        Player p5 = new Player("p5");
        CheckersGame game = new CheckersGame(p4, p5);
        int numPlayers = CuT.numPlayers();
        CuT.addGame(game);
        assertEquals(CuT.numPlayers(), numPlayers);
    }

}

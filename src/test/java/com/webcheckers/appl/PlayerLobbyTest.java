package com.webcheckers.appl;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests PlayerLobby
 */
@Tag("Application-Tier")
public class PlayerLobbyTest {
    private PlayerLobby CuT;

    /**
     *  SetsUp
     */
    @BeforeEach
    public void testSetUp() {
        CuT = new PlayerLobby();
    }

    /**
     * Tests adding a player
     */
    @Test
    public void testAdd() {
        CuT.add_player( "Greg" );
        assertEquals( 1, CuT.getAvailblePlayers().size() );
    }

    /**
     * Tests get player
     */
    @Test
    public void testGetPlayer() {
        CuT.add_player("Greg");
        Player player = CuT.getPlayer("Greg");
        assertEquals(player.getName(), "Greg");
    }

    /**
     * Test
     */
    @Test
    public void testSignOut() {
        CuT.add_player("p1");
        CuT.signOut("p1");
        assertEquals(CuT.getUsernamesList().contains("p1"),false );
    }

    /**
     * test usernameAvailability
     */
    @Test
    public void removePlayer(){
        CuT.add_player("p1");
        CuT.removePlayer("p1");
        assertFalse(CuT.getAvailblePlayers().contains("p1"));
    }

    /**
     * TestMakeAvailable
     */
    @Test
    public void testMakeAvailable(){
        Player p1 = new Player("Greg");
        CuT.makeAvailable(p1);
        assertTrue(CuT.getAvailblePlayers().contains(p1));
    }

    /**
     *  Test getPlayerRemove
     */
    @Test
    public void testGetPlayerRemove(){
        CuT.add_player("Greg");
        Player p = CuT.getPlayerRemove("Greg");
        assertEquals(p.getName(), "Greg");
    }




}

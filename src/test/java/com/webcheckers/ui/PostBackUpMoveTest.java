package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.*;
import com.webcheckers.model.*;
import org.junit.jupiter.api.*;
import spark.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the PostBackUpRoute Class
 *
 * @author Greg Godlewski
 */
@Tag("UI-tier")
public class PostBackUpMoveTest {

    private PostBackUpRoute CuT;
    private Gson gson;
    private Request request;
    private Session session;
    private Response response;
    private GameCenter gameCenter;
    private CheckersGame game;
    private PlayerLobby lobby;



    @BeforeEach
    void setUp() {
        gson = new Gson();
        request = mock( Request.class );
        session = mock( Session.class );
        when( request.session() ).thenReturn( session );
        response = mock( Response.class );
        gameCenter = mock( GameCenter.class );
        lobby = mock( PlayerLobby.class );
        game = mock( CheckersGame.class );
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        when( gameCenter.getGame(p1,p2) ).thenReturn( game );
        when(lobby.getPlayer("P1")).thenReturn(p1);
        when(lobby.getPlayer("P2")).thenReturn(p2);
        when(gameCenter.retrieveGame(p1)).thenReturn(game);
        when(gameCenter.retrieveGame(p2)).thenReturn(game);
        when(p1.getName()).thenReturn("P1");
        when(p1.getName()).thenReturn("P2");
        gameCenter.addGame( game );

        when(session.attribute(GetHomeRoute.PLAYER)).thenReturn(p1);

        CuT = new PostBackUpRoute(gameCenter, gson);
    }

    /**
     * test constructor
     */
    @Test
    void constructorTest() {
        new PostBackUpRoute(gameCenter, gson);
    }

    /**
     * test handle
     */
    @Test
    void testHandle(){
        CuT.handle(request, response);
    }

    /**
     * Test if game == null statement
     */
    @Test
    void testGameNull(){
        GameCenter gc = new GameCenter();
        PostBackUpRoute CuT2 = new PostBackUpRoute(gc, gson);
        CuT2.handle(request,response);
    }






}

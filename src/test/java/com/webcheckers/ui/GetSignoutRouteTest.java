package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.*;
import com.webcheckers.model.*;
import org.junit.jupiter.api.*;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Test GetSignOut
 * @author Greg Godlewski
 */
public class GetSignoutRouteTest {

    private GetSignoutRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private GameCenter gameCenter;
    private PlayerLobby lobby;
    private Player p1;

    /**
     * Create mock objects that each player
     */
    @BeforeEach
    void setUp () {
        Gson gson = new Gson();
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gameCenter = mock(GameCenter.class);
        lobby = mock(PlayerLobby.class);
        p1 = new Player("Greg");
        when(lobby.add_player(p1.getName())).thenReturn(PlayerLobby.UsernameAvailability.AVAILABLE);;

        CuT = new GetSignoutRoute(gameCenter, gson );
    }

    /**
     * Test the GetSignoutRoute does not equal null
     */
    @Test
    public void testNull(){
        assertNotNull(CuT);
    }

    @Test
    public void testHandle(){
       // when(request.session().attribute(GetHomeRoute.PLAYER)).thenReturn(p1);
       //CuT.handle(request, response);
    }







}

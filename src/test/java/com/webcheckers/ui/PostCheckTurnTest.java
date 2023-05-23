package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.*;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.*;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test for PostCheckTurnRoute
 * @author Greg Godlewski
 */
@Tag("UI-tier")
public class PostCheckTurnTest {
    private PostCheckTurnRoute CuT;

    private Gson gson;

    private Request request;
    private CheckersGame checkersGame;
    private PlayerLobby playerLobby;
    private Player player1;
    private Player player2;
    private Session session;
    private Response response;
    private TemplateEngine templateEngine;
    private GameCenter gameCenter;

    /**
     * test
     */
    @BeforeEach
    public void setup() {
        gson = new Gson();
        request = mock( Request.class );
        session = mock( Session.class );
        response = mock( Response.class );
        when( request.session() ).thenReturn( session );
        templateEngine = mock( TemplateEngine.class );
        playerLobby = mock( PlayerLobby.class );
        gameCenter = mock(GameCenter.class);


        // create a unique CuT for each test call
        CuT = new PostCheckTurnRoute(gson, gameCenter);
    }

    /**
     * test if not null
     */
    @Test
    public void testNonNull(){
        assertNotNull(CuT);
    }

    /**
     * Test Handle
     */
    @Test
    public void testHandle(){
        CuT.handle(request,response);
    }
}

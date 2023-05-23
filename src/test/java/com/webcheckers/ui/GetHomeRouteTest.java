package com.webcheckers.ui;
import com.webcheckers.appl.*;
import com.webcheckers.model.*;
import org.junit.jupiter.api.*;
import spark.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Test for GetHomeRoute
 * @author Greg Godlewski
 */
public class GetHomeRouteTest {

    private GetHomeRoute CuT;
    private Response response;
    private Session session;
    private PlayerLobby lobby;
    private Player player;
    private GameCenter gameCenter;
    private TemplateEngine engine;
    private Request request;

    /**
     * Sets up the require features for the GetHomeRoute
     */
    @BeforeEach
    public void setup() {
        request = mock( Request.class );
        response = mock( Response.class );
        session = mock( Session.class );
        gameCenter = mock( GameCenter.class );
        engine = mock( TemplateEngine.class );
        lobby = mock( PlayerLobby.class );
        player = mock( Player.class );
        when( player.getName() ).thenReturn( "Test" );
        when( gameCenter.getLobby() ).thenReturn( lobby );
        when( request.session() ).thenReturn( session );

        CuT = new GetHomeRoute( engine, gameCenter );
    }

    /**
     * Test the handle
     */
    @Test
    public void testHandle() {
        assertNull( CuT.handle( request, response ) );
    }



}

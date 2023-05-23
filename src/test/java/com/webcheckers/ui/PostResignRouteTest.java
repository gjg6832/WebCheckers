package com.webcheckers.ui;
import com.google.gson.Gson;
import com.webcheckers.appl.*;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.*;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("UI-Tier")
/**
 * Test the PostResignRoute Class
 *
 * @author Greg Godlewski
 */
public class PostResignRouteTest {

    private PostResignRoute CuT;
    Gson gson;

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private GameCenter gameCenter;
    private CheckersGame game;

    /**
     * Set up the required things to test resigning
     */
    @BeforeEach
    public void setup() {
        gson = new Gson();
        request = mock( Request.class );
        session = mock( Session.class );
        when( request.session() ).thenReturn( session );
        response = mock( Response.class );
        engine = mock( TemplateEngine.class );
        gameCenter = mock( GameCenter.class );
        game = mock( CheckersGame.class );
        gameCenter.addGame( game );

        CuT = new PostResignRoute( gameCenter, gson );
    }

    @Test
    public void testResignConstructor() {
        assertNotEquals( null, CuT );
    }

    /**
     * Tests Player
     */
    @Test
    public void testResignForPlayer() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when( engine.render( any( ModelAndView.class ) ) ).thenAnswer( testHelper.makeAnswer() );

        assertEquals( gson.toJson( new Message("You have resigned the game", Message.Type.INFO) ),
                CuT.handle( request, response ) );
    }

}

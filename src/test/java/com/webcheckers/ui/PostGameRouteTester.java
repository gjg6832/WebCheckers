package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import spark.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * The unit test suite for the {@link PostGameRoute} component.
 *
 * @author Shrif Rai
 */
@Tag("UI-tier")
public class PostGameRouteTester {


    private PostGameRoute newRoute;

    private static final String OPPONENT_NAME = "Joe";
    private static final String CURRENT_USER = "Jotaro";

    /**
     * The component-under-test (newRoute).
     */
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    private GameCenter gameCenter;
    private PlayerLobby playerLobby;
    private CheckersGame game;
    private Player p1;
    private Player p2;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        gameCenter = mock(GameCenter.class);
        game = mock(CheckersGame.class);
        playerLobby = mock(PlayerLobby.class);
        p1 = mock(Player.class);
        p2 = mock(Player.class);

        when(p1.getName()).thenReturn(CURRENT_USER);
        when(p2.getName()).thenReturn(OPPONENT_NAME);

        when(request.queryParams("otherUser")).thenReturn(OPPONENT_NAME);

        when(session.attribute(GetHomeRoute.PLAYER)).thenReturn(p1);

        newRoute = new PostGameRoute(gameCenter, engine);
    }

    // test if a piece object is constructed correctly
    @Test
    public void construction(){
        PostGameRoute pgr = new PostGameRoute(gameCenter, engine);
        assertNotNull(pgr);
    }

    /**
     * Test case where p1 is not available
     */
    @Test
    public void p1_Unavailable(){
        when(gameCenter.isInAGame(p1)).thenReturn(true);

        try {
            newRoute.handle(request, response);
        }catch(Exception e){}
        verify(response).redirect(WebServer.HOME_URL);
    }

    /**
     * Test case where p1 is not available
     */
    @Test
    public void p2_Unavailable(){
        when(gameCenter.isInAGame(p1)).thenReturn(true);

        try {
            newRoute.handle(request, response);
        }catch(Exception e){}
        verify(response).redirect(WebServer.HOME_URL);
    }
}

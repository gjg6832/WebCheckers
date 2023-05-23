package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tester for PostMoveRoute
 *
 * @author Shrif Rai
 */
public class PostMoveRouteTest {
    private static final Message VALID = Message.info("Move done");
    private CheckersGame game;
    private GameCenter gameCenter;
    private Session session;
    private Request request;
    private Response response;
    private Player p1;
    private Gson gson;
    private PostMoveRoute CuT;

    private final String actionData = "actionData=%7B%22start%22%3A%7B%22row%22%3A2%2C%22cell%22%3A5%7D%2C%22end%22%3A%7B%22row%22%3A3%2C%22cell%22%3A4%7D%7D";

    public static final Message INVALID_SPACE_MOVE = Message.error("Invalid move: Piece cannot move here.");
    @BeforeEach
    public void setup(){
        response = mock(Response.class);
        request = mock(Request.class);
        session = mock(Session.class);

        p1 = mock(Player.class);
        gameCenter = mock(GameCenter.class);
        game = mock(CheckersGame.class);

        when(session.attribute(GetHomeRoute.PLAYER)).thenReturn(p1);
        when(request.session()).thenReturn(session);
        when(gameCenter.retrieveGame(p1)).thenReturn(game);
        when(request.body()).thenReturn(actionData);

        gson = new Gson();
        CuT = new PostMoveRoute(gameCenter);
    }

    @Test
    public void validMoveTest() throws Exception {
        when(game.validateMove(any())).thenReturn(VALID);
        String expectedResult = gson.toJson(VALID);
        String actualResult = null;
        try {
            actualResult = (String)CuT.handle(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(expectedResult,actualResult);

        GameCenter gameCenter2 = new GameCenter();
        PostMoveRoute CuT2 = new PostMoveRoute(gameCenter2);
        CuT2.handle(request,response);

    }

    @Test
    public void invalidMoveTest(){
        when(game.validateMove(any())).thenReturn(INVALID_SPACE_MOVE);
        String expectedResult = gson.toJson(INVALID_SPACE_MOVE);
        String actualResult = null;
        try {
            actualResult = (String)CuT.handle(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(expectedResult,actualResult);
    }
}

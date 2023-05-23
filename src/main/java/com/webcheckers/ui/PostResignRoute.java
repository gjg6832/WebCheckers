package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.Objects;

/**
 *  Takes care of a Resign
 *
 * @Greg Godlewski
 */
public class PostResignRoute implements Route {

    private final Gson gson;
    private GameCenter gameCenter;

    /**
     * Resign Route
     */
    public PostResignRoute(GameCenter gameCenter, final Gson gson){
        Objects.requireNonNull( gameCenter, "gameCenter must not be null" );
        this.gameCenter = gameCenter;
        Objects.requireNonNull( gson, "gson must not be null" );
        this.gson = gson;
    }

    /**
     * resigns a game
     *
     * @param request - the HTTP request
     * @param response - the HTTP response
     * @return - message
     */
    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();

        Player p1 = httpSession.attribute(GetHomeRoute.PLAYER);
        CheckersGame game = gameCenter.retrieveGame(p1);

        if(p1.getName().equals(game.getRedPlayer().getName())) {
            gameCenter.setMessage(game.getRedPlayer().getName(), "You have resigned from the last game.");
            gameCenter.setMessage(game.getWhitePlayer().getName(), "Your opponent resigned from the last game.");
        } else {
            gameCenter.setMessage(game.getWhitePlayer().getName(), "You have resigned from the last game.");
            gameCenter.setMessage(game.getRedPlayer().getName(), "Your opponent resigned from the last game.");
        }
        gameCenter.endGame(game);

        return gson.toJson( new Message( "You have resigned the game", Message.Type.INFO ) );
    }
}

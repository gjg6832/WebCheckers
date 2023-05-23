package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;

/**
 * Checks if it is the players turn or not
 *
 * @author Greg Godlewski
 */
public class PostCheckTurnRoute implements Route {

    final Gson gson;
    final GameCenter gameCenter;


    public PostCheckTurnRoute(final Gson gson, final GameCenter gameCenter){
        Objects.requireNonNull(gson, "templateEngine must not be null");
        this.gson = gson;
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        this.gameCenter = gameCenter;
    }


    /**
     * Checks the turn
     *
     * @param request - the http request
     * @param response - the http response
     */
    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();

        Player p1 = httpSession.attribute(GetHomeRoute.PLAYER);
        CheckersGame game = gameCenter.retrieveGame(p1);

        if( game == null ) {
            return gson.toJson( new Message( "true", Message.Type.INFO ) );
        }

        if(game.isPlayersTurn(p1.getName())){
            return gson.toJson(new Message("true", Message.Type.INFO));
        }
        return gson.toJson(new Message("false", Message.Type.INFO));
    }
}

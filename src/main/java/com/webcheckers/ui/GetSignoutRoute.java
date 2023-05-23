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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Implements signing out of the lobby and redirecting if in a game
 *
 * @author Greg Godlewski
 * @author Ethan Yaccarino-Mims
 */
public class GetSignoutRoute implements Route {


    private final GameCenter gameCenter;
    private final Gson gson;




    public GetSignoutRoute(final GameCenter gameCenter, final Gson gson){
        Objects.requireNonNull(gameCenter, "Game Center must be not be null");
        this.gameCenter = gameCenter;
        Objects.requireNonNull(gson, "Gson must be note be null");
        this.gson = gson;
    }

    /**
     * Signouts out the play
     *
     * @param request - the http request
     * @param response - the http response
     */
    @Override
    public Object handle(Request request, Response response) {

        final Session httpSession = request.session();

        Player p1 = httpSession.attribute(GetHomeRoute.PLAYER);
        PlayerLobby playerLobby = gameCenter.getLobby();
        playerLobby.removePlayer(p1.getName());
        CheckersGame game = gameCenter.retrieveGame(p1);

        if (game != null){
            if(p1.getName().equals(game.getRedPlayer().getName())) {
                gameCenter.setMessage(game.getWhitePlayer().getName(), "Your opponent has signed out.");
            } else {
                gameCenter.setMessage(game.getRedPlayer().getName(), "Your opponent has signed out.");
            }
            gameCenter.endGame(game);
        }
        playerLobby.signOut(p1.getName());

        httpSession.attribute(GetHomeRoute.PLAYER, null);

        response.redirect(WebServer.HOME_URL);
        return gson.toJson( new Message( "You have signed out", Message.Type.INFO ) );
    }
}

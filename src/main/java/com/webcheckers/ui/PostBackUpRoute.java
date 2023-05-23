package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;
import java.util.Objects;

/**
 * backup a move that the current play made.
 *
 * @author Jialiang(Hayden) Liang
 * @author Greg Godlewski
 */
public class PostBackUpRoute implements Route{

    public static final Message MOVE_BACKUP = Message.info("Move backed up.");

    final Gson gson;
    final GameCenter gamecenter;

    public PostBackUpRoute( GameCenter gamecenter, Gson gson){
        Objects.requireNonNull(gson, "templateEngine must not be null");
        this.gson = gson;
        Objects.requireNonNull(gamecenter, "gameCenter must not be null");
        this.gamecenter = gamecenter;
    }

    /**
     * Backs up a unwanted move
     *
     * @param request - the http request
     * @param response - the http response
     */
    @Override
    public Object handle(Request request, Response response){
        Session newSession = request.session();
        Player p1 = newSession.attribute(GetHomeRoute.PLAYER);
        CheckersGame game = gamecenter.retrieveGame(p1);
        if( game == null) {
            return gson.toJson(new Message("The other player resigned, press my home to go home .", Message.Type.INFO));
        } else {
            game.backupMove();
            return gson.toJson(MOVE_BACKUP);
        }
    }
}

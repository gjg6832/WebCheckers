package com.webcheckers.ui;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;
import spark.*;

import com.webcheckers.util.Message;
import static spark.Spark.halt;

/***
 * The UI Controller to handle moves made by players
 *
 * @author Shrif Rai
 */
public class PostMoveRoute implements Route {
    GameCenter gameCenter;

    /**
     * The constructor for Spark Route {@code POST /} move requests
     *
     * @param gameCenter for
     */
    public PostMoveRoute(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    /**
     * validate move
     *
     * @param request the HTTP request
     * @param response the HTTP response
     *
     * @return move if move valid or not
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Session newSession = request.session();
        // get the current user
        Player p1 = newSession.attribute(GetHomeRoute.PLAYER);
        // get the game
        CheckersGame game = gameCenter.retrieveGame(p1);

        Gson parser = new Gson();
        String move_url = request.body();
        move_url = URLDecoder.decode(move_url,"UTF-8");

        if( game == null) {
            return parser.toJson(new Message("The other player resigned, press my home to go home .", Message.Type.INFO));
        } else {
            final Move move = parser.fromJson(move_url.substring(11), Move.class);
            Message msg = game.validateMove(move);

            return parser.toJson(msg);
        }
    }
}

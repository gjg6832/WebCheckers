package com.webcheckers.ui;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;
import spark.*;

import com.webcheckers.util.Message;

import static spark.Spark.halt;


/**
 * The UI Controller to POST
 *
 * @author Ethan Yaccarino-Mims
 * @author Shrif Rai
 */
public class PostGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostGameRoute.class.getName());
    static final String VIEW_NAME = "game.ftl";

    private final TemplateEngine templateEngine;
    private GameCenter gameCenter;

    /**
     * The constructor for Spark Route {@code POST /} signin requests
     *
     * @param templateEngine the HTML template rendering engine
     */
    PostGameRoute(GameCenter gameCenter, TemplateEngine templateEngine) {// templateEngine != null
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.gameCenter = gameCenter;
    }


    /**
     * render the sign in page if username valid
     *
     * @param request the HTTP request
     * @param response the HTTP response
     *
     * @return the rendered HTML for the Sign-in page
     */
    @Override
    public String handle(Request request, Response response) {
        Map<String, Object> vm  = new HashMap<>();

        // start a new session
        final Session newSession = request.session();

        PlayerLobby lobby = gameCenter.getLobby();

        // get players
        Player p1 = newSession.attribute(GetHomeRoute.PLAYER);
        String p2 = request.queryParams("availablePlayer");


        if(gameCenter.isInAGame(p1) || gameCenter.isInAGame(lobby.getPlayerRemove(p2))) {
            newSession.attribute(GetHomeRoute.OPPONENT_IN_GAME, true);
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }


        // get players and remove them from the lobby
        p1 = lobby.getPlayerRemove(p1.getName());
        Player player2 = lobby.getPlayerRemove(p2);

        CheckersGame game = gameCenter.startGame(p1, player2);

        Board board = game.getGameBoard(game.getColor(p1));
        GetGameRoute.viewMode viewmode = GetGameRoute.viewMode.PLAY;// for the MVP, play is needed, implement replay and spectator later on

        vm.put("title", GetGameRoute.TITLE);
        vm.put("currentUser", p1);
        vm.put("viewMode",viewmode);
        vm.put("whitePlayer", game.getWhitePlayer());
        vm.put("redPlayer", game.getRedPlayer());
        vm.put("activeColor",game.getActiveColor());
        vm.put("board", board);

        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}

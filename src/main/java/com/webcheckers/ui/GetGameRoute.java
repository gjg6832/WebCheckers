package com.webcheckers.ui;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Game page.
 *
 * @author Shrif Rai
 * @author Greg Godlewski
 * @author JiaLiang (Hayden) Liang
 */
public class GetGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
    public static final String TITLE = "Checkers Game";


    private final TemplateEngine templateEngine;
    private  GameCenter gameCenter;

    //
    //enum for viewMode
    //
    enum viewMode{ PLAY, SPECTATOR}

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetGameRoute(GameCenter gameCenter, final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.gameCenter = gameCenter;
        //
        LOG.config("GetHomeRoute is initialized.");
    }

    /**
     * Render the WebCheckers Game page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetGameRoute is invoked.");

        final Session newSession = request.session();

        final Player player = newSession.attribute(GetHomeRoute.PLAYER);

        Map<String, Object> vm = new HashMap<>();
        //PlayerLobby lobby = gameCenter.getLobby();
        CheckersGame mainGame = gameCenter.retrieveGame(player);
        if(mainGame == null) {
            response.redirect(WebServer.HOME_URL);
            return null;
        } else {
            Board board = mainGame.getGameBoard(mainGame.getColor(player));

            vm.put("title", TITLE);
            vm.put("currentUser", player);
            viewMode viewmode = viewMode.PLAY; // for the MVP, play is needed, implement replay and spectator later on
            vm.put("viewMode", viewmode);
            vm.put("whitePlayer", mainGame.getWhitePlayer());
            vm.put("redPlayer", mainGame.getRedPlayer());
            vm.put("activeColor", mainGame.getActiveColor());
            vm.put("board", board);


            // render the View
            return templateEngine.render(new ModelAndView(vm, "game.ftl"));
        }
    }
}


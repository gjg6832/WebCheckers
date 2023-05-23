package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Home page.
 *
 * @author Shrif Rai
 * @author Ethan Yaccarino-Mims
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final String WELCOME_MSG = "Welcome to the world of online Checkers, %s.";

  private final TemplateEngine templateEngine;
  private final GameCenter gameCenter;

  public static final String PLAYERS_LIST = "playerList";
  public static final String PLAYER = "player";
  public static final String USER_NAME = "userName";

  public static final String OPPONENT_IN_GAME = "unavailable";

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final TemplateEngine templateEngine, GameCenter gameCenter) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.gameCenter = gameCenter;
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
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
    LOG.finer("GetHomeRoute is invoked.");

    Map<String, Object> vm = new HashMap<>();

    Session newSession = request.session();
    if(newSession.attribute(PLAYER) == null) {
      vm.put("title", "Welcome!");
      vm.put("message", Message.info("Their are currently " + gameCenter.numPlayers()
              + " other players online."));
    }
    else if (newSession.attribute(OPPONENT_IN_GAME) == (Boolean)true) {
      Player player = newSession.attribute(PLAYER);
      vm.put("title","Homepage");
      vm.put("message", Message.error("The player you selected is currently in a game."));
      newSession.attribute(OPPONENT_IN_GAME, false);
      gameCenter.setMessage(player.getName(), "The player you selected is currently in a game.");
      response.redirect(WebServer.HOME_URL);
    }
    else if(gameCenter.isInAGame(newSession.attribute(PLAYER))) {
      response.redirect(WebServer.GAME_URL);
      halt();
      return null;
    }
    else {
      PlayerLobby lobby = gameCenter.getLobby();
      Player player = newSession.attribute(PLAYER);
      vm.put("title", "Homepage");
      vm.put(USER_NAME, player);
      vm.put(PLAYERS_LIST, lobby.getAvailblePlayers());
      // display a user message in the Home page
      String message = "";
      if(gameCenter.hasMessage(player.getName())) {
        message = " " + gameCenter.getMessage(player.getName());
      }
      vm.put("message", Message.info(String.format(WELCOME_MSG, player.getName()) + message));
    }

    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}

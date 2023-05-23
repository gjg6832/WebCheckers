package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to POST
 *
 * @author Shrif Rai
 * @author Ethan Yaccarino-Mims
 */
public class PostSigninRoute  implements Route {
    private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());
    private static final String VIEW_NAME = "signin.ftl";
    private static final String NAME_PARAM = "userName";
    private static final Message TAKEN_MSG = Message.error("The username you entered is taken. Please try again.");
    private static final Message IVALID_MSG = Message.error("The username you entered is invalid. Name must start with letter and must be alphanumeric.");

    private final TemplateEngine templateEngine;
    private GameCenter gameCenter;

    /**
     * The constructor for Spark Route {@code POST /} signin requests
     *
     * @param templateEngine the HTML template rendering engine
     */
    PostSigninRoute(GameCenter gameCenter, TemplateEngine templateEngine) {// templateEngine != null
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
    public String handle(Request request, Response response){
        // start a new session
        final Map<String, Object> vm = new HashMap<>();
        String username = request.queryParams(NAME_PARAM);
        PlayerLobby lobby = gameCenter.getLobby();
        PlayerLobby.UsernameAvailability available = lobby.add_player(username);

        final Session newSession = request.session();
        newSession.attribute("currentUser", username);

        if(available == PlayerLobby.UsernameAvailability.AVAILABLE) {
            newSession.attribute(GetHomeRoute.PLAYER, lobby.getPlayer(username));
            response.redirect(WebServer.HOME_URL);
            return null;
        }
        else if(available == PlayerLobby.UsernameAvailability.TAKEN) {
            vm.put("message", TAKEN_MSG);
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }
        else {
            vm.put("message", IVALID_MSG);
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }
    }


}
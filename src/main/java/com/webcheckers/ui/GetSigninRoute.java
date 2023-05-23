package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Sign-in page
 *
 * @author Ethan Yaccarino-Mims
 * @author Shrif Rai
 */
public class GetSigninRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());
    private static final Message SIGN_IN_MESSAGE = Message.info("Please enter a valid username.");

    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSigninRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetSigninRoute is initialized.");
    }

    /**
     * Render the WebChecker
     *
     * @param request
     *  the HTTP request
     * @param response
     * the HTTP response
     *
     * @return
     *  the rendered HTML for the Sign-in page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSigninRoute is invoked.");

        Map<String, Object> vm = new HashMap<>();
        vm.put("message", SIGN_IN_MESSAGE);

        return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    }
}



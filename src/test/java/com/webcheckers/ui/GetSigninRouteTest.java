package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;


/**
 * The unit test suite for the {@link GetSigninRouteTest} component.
 *
 * @author <a href='mailto:eay4074@rit.edu'>Ethan Yaccarino-Mims</a>
 */
@Tag("UI-tier")
public class GetSigninRouteTest {

    /**
     * The component under the test
     */
    private GetSigninRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;


    /**
     * Sets up the new mock objects before each test is ran
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        CuT = new GetSigninRoute(engine);
    }

    /**
     * Tests the loading of the signin route
     */
    @Test
    public void testSignIn() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();

        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
    }
}

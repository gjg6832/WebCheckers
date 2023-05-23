package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.webcheckers.appl.GameCenter;

import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;



/**
 * The unit test suite for the {@link GetGameRoute} component.
 *
 *
 */
@Tag("UI-tier")
public class GetGameRouteTester {

    /**
     * The component-under-test (CuT).
     */
    private GetGameRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    private GameCenter center;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        center = mock(GameCenter.class);

        CuT = new GetGameRoute(center, engine);
    }

    @Test
    public void testHandle(){
        assertNotNull(CuT);
    }




}
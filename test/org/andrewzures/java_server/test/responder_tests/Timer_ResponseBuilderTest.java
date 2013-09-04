package org.andrewzures.java_server.test.responder_tests;

import org.andrewzures.java_server.request.Request;
import org.andrewzures.java_server.responders.TimerResponder;
import org.andrewzures.java_server.response.Response;
import org.andrewzures.java_server.test.MockInputStream;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Timer_ResponseBuilderTest {

   TimerResponder builder;

    public Timer_ResponseBuilderTest(){
        builder = new TimerResponder("timerResponse.html");
    }

    @Test
    public void testTimerBuilder(){
        Request request = new Request();
        request.method = "GET";
        request.relativePath = "/time";
        Response response = builder.respond(request);
        assertTrue(response.inputStream != null);
    }

    @Test
    public void testTimerThrowInterrupt(){
        Request request = new Request();
        request.method = "GET";
        request.relativePath = "/time";
        Thread.currentThread().interrupt();
        Response response = builder.respond(request);
        assertEquals(null, response);
    }

    @Test
    public void testUpdateFile(){
        InputStream testStream = new MockInputStream();
        String result = builder.convertStreamToString(testStream);
        assertEquals(null, result);
    }



}

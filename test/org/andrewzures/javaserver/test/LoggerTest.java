package org.andrewzures.javaserver.test;

import org.andrewzures.javaserver.Logger;
import org.andrewzures.javaserver.request.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LoggerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private Logger logger;

    public LoggerTest(){
        logger = new Logger();
    }


    @Before
    public void setUpstream(){
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStream(){
        System.setOut(null);
    }


    @Test
    public void testLog1() throws Exception {
        Request request = new Request();
        request.method = "GET";
        request.relativePath = "/path";
        request.httpType = "HTTP/1.1";
        logger.logRequest(request);
        assertThat(outContent.toString(), JUnitMatchers.containsString("GET /path"));
    }

    @Test
    public void testLog2() throws Exception {
        Request request = new Request();
        request.method = "GET";
        request.relativePath = "/path";
        logger.logRequest(request);
        assertThat(outContent.toString(), JUnitMatchers.containsString("Invalid Request Received"));
    }

    @Test
    public void testValidRequest1() throws Exception {
        Request request = new Request();
        request.method = "GET";
        request.relativePath = "/path";
        request.httpType = "HTTP/1.1";
        boolean result = logger.validRequest(request);
        assertEquals(true, result);
    }

    @Test
    public void testValidRequest2() throws Exception {
        Request request = new Request();
        request.method = "GET";
        request.relativePath = "/path";
        boolean result = logger.validRequest(request);
        assertEquals(false, result);
    }
}

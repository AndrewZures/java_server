package org.andrewzures.javaserver.test;

import org.andrewzures.javaserver.PostParser;
import org.andrewzures.javaserver.request.Request;
import org.andrewzures.javaserver.server_and_sockets.SocketInterface;
import org.andrewzures.javaserver.test.socket_test.MockSocket;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class PostParserTest {
    PostParser parser;

    public PostParserTest(){
        parser = new PostParser();
    }

    @Test
    public void testGetFormBody() throws Exception {
        Request request = new Request();
        SocketInterface socket = new MockSocket();
        String testContent = "name=andrew&day=wednesday";
        socket.setInputStream(testContent);
        request.contentLength = testContent.length();
        request.socket = socket;
        String formBody = parser.getFormBody(request);
        assertEquals("name=andrew&day=wednesday", formBody);
    }

    @Test
    public void testBadSocketInputStream() throws Exception {
        Request request = new Request();
        SocketInterface socket = new MockSocket();
        socket.setInputStream(null);
        request.contentLength = 2;
        request.socket = socket;
        String formBody = parser.getFormBody(request);
        assertEquals("", formBody);
    }

    @Test
    public void testParsePostHash() throws Exception {
        String testString = "name=andrew&day=wednesday";
        HashMap<String, String> resultMap = parser.parsePostHash(testString);
        assertEquals(2, resultMap.size());

    }


    @Test
    public void testGetKeyValuePairs() throws Exception {
      String testString = "name=andrew&day=wednesday";
        String[] result = parser.getKeyValuePairs(testString);
        assertEquals(2, result.length);
        assertEquals("name=andrew", result[0]);
        assertEquals("day=wednesday", result[1]);

    }
}

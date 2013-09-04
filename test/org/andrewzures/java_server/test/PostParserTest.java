package org.andrewzures.java_server.test;

import org.andrewzures.java_server.InputReader;
import org.andrewzures.java_server.PostParser;
import org.andrewzures.java_server.request.Request;
import org.andrewzures.java_server.server_and_sockets.SocketInterface;
import org.andrewzures.java_server.test.socket_test.MockSocket;
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
        socket.setInputStream("name=andrew&day=wednesday");
        InputReader reader = new InputReader(socket);
        request.inputReader = reader;
        String formBody = parser.getFormBody(request);
        assertEquals("name=andrew&day=wednesday", formBody);
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

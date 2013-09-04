package org.andrewzures.java_server.test.server_test;

import org.andrewzures.java_server.Logger;
import org.andrewzures.java_server.server_and_sockets.Server;
import org.andrewzures.java_server.server_and_sockets.ServerSocketInterface;
import org.andrewzures.java_server.test.server_test.MockServerSocket;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ServerTest {

    private Server server;
    ServerSocketInterface mockServerSocket;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    public ServerTest(){
        mockServerSocket = new MockServerSocket();
        Logger logger = new Logger();
        server = new Server(7170, "./../", mockServerSocket, logger);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testServerFailure1(){
        mockServerSocket.closeServer();
        server.go();
        assertThat(outContent.toString(), JUnitMatchers.containsString("Server start error, aborting\n"));
    }

    @Test
    public void testServer(){
        mockServerSocket.closeAccept();
        server.go();
        assertThat(outContent.toString(), JUnitMatchers.containsString("could not connect to socket"));
    }

    @Test
    public void testServerMultipleSockets1(){
        mockServerSocket.setNumSocketsToReturn(2);
        server.go();
        assertEquals(2, server.getThreadCount());
    }

    @Test
    public void testServerMultipleSockets2(){
        mockServerSocket.setNumSocketsToReturn(4);
        server.go();
        assertEquals(4, server.getThreadCount());
    }

}

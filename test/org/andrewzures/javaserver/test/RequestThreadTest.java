package org.andrewzures.javaserver.test;

import org.andrewzures.javaserver.Logger;
import org.andrewzures.javaserver.PostParser;
import org.andrewzures.javaserver.responders.DirectoryResponder;
import org.andrewzures.javaserver.responders.File404Responder;
import org.andrewzures.javaserver.response.ResponseBuilder;
import org.andrewzures.javaserver.response.ResponseBuilderInterface;
import org.andrewzures.javaserver.server_and_sockets.RequestThread;
import org.andrewzures.javaserver.server_and_sockets.Server;
import org.andrewzures.javaserver.server_and_sockets.ServerSocketInterface;
import org.andrewzures.javaserver.server_and_sockets.SocketInterface;
import org.andrewzures.javaserver.test.server_test.MockServerSocket;
import org.andrewzures.javaserver.test.socket_test.MockSocket;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestThreadTest {

    @Before
    public void runBefore(){
        try {
            FileOutputStream f = new FileOutputStream("file.txt");
            System.setOut(new PrintStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    @Test
    public void testConstructor() {
        SocketInterface socket = new MockSocket();
        String startingPath = ".";
        ServerSocketInterface mockServerSocket = new MockServerSocket();
        Logger logger = new Logger();
        PostParser parser = new PostParser();
        Server server = new Server(8189, startingPath, mockServerSocket, logger);
        server.add404Responder(new File404Responder());
        server.addDirectoryResponder(new DirectoryResponder());

        ResponseBuilderInterface builder = new ResponseBuilder();

        RequestThread handler = new RequestThread(socket, server, startingPath, builder, parser);
        assertTrue(handler instanceof RequestThread);
    }

    @Test
    public void testRun(){
        SocketInterface socket = new MockSocket();
        socket.closeInputStream();
        String startingPath = ".";
        PostParser parser = new PostParser();
        ServerSocketInterface mockServerSocket = new MockServerSocket();
        Logger logger = new Logger();
        Server server = new Server(8189, startingPath, mockServerSocket, logger);
        ResponseBuilderInterface builder = new ResponseBuilder();

        RequestThread handler = new RequestThread(socket, server, startingPath, builder,  parser);
        handler.run();
        assertEquals(true, socket.isClosed());
    }


}

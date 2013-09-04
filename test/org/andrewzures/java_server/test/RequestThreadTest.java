package org.andrewzures.java_server.test;

import org.andrewzures.java_server.Logger;
import org.andrewzures.java_server.PostParser;
import org.andrewzures.java_server.file_reader.FileReaderInterface;
import org.andrewzures.java_server.response.ResponseBuilder;
import org.andrewzures.java_server.response.ResponseBuilderInterface;
import org.andrewzures.java_server.server_and_sockets.RequestThread;
import org.andrewzures.java_server.server_and_sockets.Server;
import org.andrewzures.java_server.server_and_sockets.ServerSocketInterface;
import org.andrewzures.java_server.server_and_sockets.SocketInterface;
import org.andrewzures.java_server.test.server_test.MockServerSocket;
import org.andrewzures.java_server.test.socket_test.MockSocket;
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
        FileReaderInterface reader = new MockFileReader();
        ResponseBuilderInterface builder = new ResponseBuilder(reader);

        RequestThread handler = new RequestThread(socket, server, startingPath, builder, reader, parser);
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
        FileReaderInterface reader = new MockFileReader();
        ResponseBuilderInterface builder = new ResponseBuilder(reader);

        RequestThread handler = new RequestThread(socket, server, startingPath, builder, reader, parser);
        handler.run();
        assertEquals(true, socket.isClosed());
    }

}

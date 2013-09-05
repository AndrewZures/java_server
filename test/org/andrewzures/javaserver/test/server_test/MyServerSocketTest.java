package org.andrewzures.javaserver.test.server_test;

import org.andrewzures.javaserver.server_and_sockets.MyServerSocket;
import org.andrewzures.javaserver.server_and_sockets.SocketInterface;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.net.SocketTimeoutException;

import static junit.framework.Assert.assertEquals;

public class MyServerSocketTest {
    MyServerSocket serverSocket;


    public MyServerSocketTest(){
        serverSocket = new MyServerSocket();
    }

    @After
    public void after(){
        serverSocket.closeServer();
    }

    @Test
    public void testServerSocketIsClosed1(){
        serverSocket.runServer(8180);
        assertEquals(false, serverSocket.isClosed());
    }

    @Test
    public void testServerSocketIsClosed2(){
        serverSocket.runServer(8180);
        serverSocket.closeServer();
        assertEquals(true, serverSocket.isClosed());
    }

    @Test
    public void testRunServer1() throws Exception {
        boolean status = serverSocket.runServer(80);
        assertEquals(false, status);
    }

    @Test
    public void testRunServer2() throws Exception {
        boolean status = serverSocket.runServer(8280);
        assertEquals(true, status);
    }

    @Test
    public void testCloseServer() throws Exception {
        serverSocket.closeAccept();
        assertEquals(null, serverSocket.accept());
    }

    @Test
    public void testIsClosed1(){
        serverSocket.runServer(8180);
        boolean result = serverSocket.isClosed();
        assertEquals(false, result);
    }

    @Test
    public void testIsClosed2(){
        serverSocket.runServer(8180);
        boolean result = serverSocket.isClosed();
        assertEquals(false, result);
    }

    @Test
    public void testAccept1() throws Exception {
        serverSocket.runServer(80);
        SocketInterface socket = serverSocket.accept();
        assertEquals(null, socket);
    }

    @Test
    public void testAccept2() throws Exception {
        serverSocket.runServer(8281);
        serverSocket.setSoTimeOut(10);
        SocketInterface socket = null;
        try{
            socket = serverSocket.accept();
        } catch(SocketTimeoutException se){}
        assertEquals(null, socket);
    }

    @Test
    public void testSetNumSocketsToReturn() throws IOException {
        serverSocket.setNumSocketsToReturn(2);
    }
}

package org.andrewzures.java_server.test.server_test;

import org.andrewzures.java_server.server_and_sockets.ServerSocketInterface;
import org.andrewzures.java_server.server_and_sockets.SocketInterface;
import org.andrewzures.java_server.test.socket_test.MockSocket;

import java.io.IOException;
import java.net.SocketException;

public class MockServerSocket implements ServerSocketInterface {
    private boolean acceptOpen = true;
    private boolean serverStatus = true;
    private int numSockets = 0;
    private int count = 0;

    public MockServerSocket(){}

    public MockServerSocket(boolean status) throws IOException {
        if(status) throw new IOException();
    }

     public SocketInterface accept() throws IOException {
         if(acceptOpen && count < numSockets){
             SocketInterface socket = new MockSocket();
             count++;
             return socket;
         }
         else throw new IOException();
      }

    public void closeAccept() {
        acceptOpen = false;
    }

    @Override
    public boolean runServer(int port) {
        return serverStatus;
    }

    @Override
    public void closeServer() {
        serverStatus = false;
    }

    @Override
    public void setNumSocketsToReturn(int num) {
        numSockets = num;
    }

    @Override
    public void setSoTimeOut(int num) throws SocketException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}

package org.andrewzures.java_server.server_and_sockets;

import java.io.IOException;
import java.net.SocketException;

public interface ServerSocketInterface {

    public SocketInterface accept() throws IOException;
    public void closeAccept();
    public boolean runServer(int port);
    public void closeServer();
    public void setNumSocketsToReturn(int num);
    public void setSoTimeOut(int num) throws SocketException;


}

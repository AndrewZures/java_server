package org.andrewzures.java_server.server_and_sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MySocket implements SocketInterface {
    Socket socket;

    public MySocket(Socket socket){
        this.socket = socket;
    }

    public OutputStream getOutputStream() {
        try{
            return socket.getOutputStream();
        } catch (IOException ioe){
            return null;
        }
    }

    public InputStream getInputStream() throws IOException {
        try{
            return socket.getInputStream();
        } catch(IOException ioe){
            return null;
        }
    }

    @Override
    public void setInputStream(String input) {
        //do nothing. this method is used for testing
    }

    public void close() {
        try{
             this.socket.close();
        } catch(IOException ioe){}
    }


    public boolean isClosed() {
        return this.socket.isClosed();
    }

    @Override
    public String getOutputStreamString() {
        return null;
    }

    @Override
    public void closeInputStream() {
        //do nothing
    }

}

package org.andrewzures.javaserver.test.socket_test;

import org.andrewzures.javaserver.server_and_sockets.SocketInterface;

import java.io.*;

public class MockSocket implements SocketInterface {
    ByteArrayInputStream testInputStream;
    ByteArrayOutputStream testOutputStream;
    String inputString = null;
    boolean closed = false;

    public MockSocket(){
        inputString = "helloword";
        byte[] testBytes = inputString.getBytes();
        testInputStream = new ByteArrayInputStream(testBytes);
        testOutputStream = new ByteArrayOutputStream();
    }

    public void setInputStream(String input){
        this.inputString = input;
        byte[] testBytes =inputString.getBytes();
        testInputStream = new ByteArrayInputStream(testBytes);
    }

    public String getOutputStreamString(){
       if(testOutputStream != null)
           return testOutputStream.toString();
        else return null;
    }

    public OutputStream getOutputStream() throws IOException {
       if(testOutputStream != null)
            return testOutputStream;
       else throw new IOException("IOException!");
    }

    public InputStream getInputStream() throws IOException {
        if(testInputStream != null)
            return testInputStream;
        else throw new IOException("IOException!");
    }

    public void closeInputStream(){
        testInputStream = null;
    }

    @Override
    public void close() {
        closed = true;
    }


    @Override
    public boolean isClosed() {
        return closed;
    }

}

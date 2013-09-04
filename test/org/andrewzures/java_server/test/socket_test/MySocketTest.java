package org.andrewzures.java_server.test.socket_test;

import org.andrewzures.java_server.server_and_sockets.MySocket;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static junit.framework.Assert.assertEquals;

public class MySocketTest {
    MySocket socket;

    public MySocketTest(){
        Socket realSocket = new Socket();
        socket = new MySocket(realSocket);
    }

    @Test
    public void testGetOutputStream() {
        OutputStream stream = socket.getOutputStream();
        assertEquals(null, stream);
    }

    @Test
    public void testGetInputStream() throws Exception {
        InputStream stream = socket.getInputStream();
        assertEquals(null, stream);
    }

    @Test
    public void testSetInputStream() throws Exception {
        socket.setInputStream("hello_world");
        InputStream stream = socket.getInputStream();
        assertEquals(null, stream);
    }

    @Test
    public void testClose1() throws Exception {
        assertEquals(false, socket.isClosed());
    }

    @Test
       public void testClose2() throws Exception {
        socket.close();
        assertEquals(true, socket.isClosed());

    }

    @Test
    public void testGetOutputStreamString() throws Exception {
        String result = socket.getOutputStreamString();
        assertEquals(null, result);

    }

    @Test
    public void testCloseInputStream() throws Exception {
        socket.closeInputStream();
        InputStream stream = socket.getInputStream();
        assertEquals(null, stream);
    }
}

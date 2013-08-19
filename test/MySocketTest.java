import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: andrewzures
 * Date: 8/5/13
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
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

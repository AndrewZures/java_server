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
        Server server = new Server(8189, startingPath, mockServerSocket, logger);
        FileReaderInterface reader = new MockFileReader();

        RequestThread handler = new RequestThread(socket, server, startingPath, reader);
        assertTrue(handler instanceof RequestThread);
    }

    @Test
    public void testRun(){
        SocketInterface socket = new MockSocket();
        socket.closeInputStream();
        String startingPath = ".";
        ServerSocketInterface mockServerSocket = new MockServerSocket();
        Logger logger = new Logger();
        Server server = new Server(8189, startingPath, mockServerSocket, logger);
        FileReaderInterface reader = new MockFileReader();

        RequestThread handler = new RequestThread(socket, server, startingPath, reader);
        handler.run();
        assertEquals(true, socket.isClosed());
    }

}

import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

public class InputReaderTest {

    SocketInterface socket;
    InputReader reader;

    public InputReaderTest(){
        socket = new MockSocket();
        reader = new InputReader(socket);
    }

    @Test
    public void testCharIsAvailable() throws IOException {
        boolean result = reader.charIsAvailable();
        assertEquals(true, result);
    }

    @Test
    public void testCharIsNotAvailable() throws IOException {
        socket.setInputStream("");
        boolean result = reader.charIsAvailable();
        assertEquals(false, result);
    }

    @Test
    public void testCharIsAvailableIOException() throws IOException {
        socket.closeInputStream();
        boolean result = reader.charIsAvailable();
        assertEquals(false, result);
    }

    @Test
    public void testReadSingleCharacter() throws IOException {
        int result = reader.readNextChar();
        assertEquals('h', (char) result);
    }

    @Test
    public void testReadWithNoCharsLeft() throws IOException {
        socket.setInputStream("");
        int result = reader.readNextChar();
        assertEquals(-1, result);
    }

    @Test
    public void testReadNextCharIOException() throws IOException {
        socket.closeInputStream();
        int result = reader.readNextChar();
        assertEquals(-1, result);
    }


}

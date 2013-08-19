import java.io.IOException;
import java.io.InputStream;

public class MockInputStream extends InputStream {

    public int read() throws IOException {
        throw new IOException();
    }
}

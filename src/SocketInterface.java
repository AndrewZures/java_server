import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SocketInterface {

    public OutputStream getOutputStream() throws IOException;
    public InputStream getInputStream() throws IOException;
    public void setInputStream(String input);
    public void close();
    public boolean isClosed();
    public String getOutputStreamString();
    public void closeInputStream();

}

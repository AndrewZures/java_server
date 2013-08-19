import java.io.InputStream;

public class Response {

    public String method;
    public String path;
    public String httpType;
    public String contentType;
    public String statusCode;
    public String statusText;
    public String lineBreak = "\r\n";
    public String headerBreak = "\r\n\r\n";
    public String body = null;
    public InputStream inputStream = null;
    public FileReaderInterface fileReader = null;


}

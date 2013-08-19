public class Request {
    public String method;
    public String relativePath;
    public String fullPath;
    public String httpType;
    public int contentLength;
    public InputReader inputReader = null;
    public FileReaderInterface fileReader = null;
    public String body;
}

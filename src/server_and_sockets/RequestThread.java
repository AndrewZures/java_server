import java.io.IOException;


public class RequestThread extends Thread {
    Server server;
    SocketInterface socket;
    RequestBuilder requestBuilder;
    ResponseBuilder responseBuilder;
    OutputBuilder sender;
    InputReader inputReader;
    FileReaderInterface fileReader;
    String startingPath;
    Logger logger;

    public RequestThread(SocketInterface socket, Server server, String startingPath, FileReaderInterface fileReader, PostParser parser) {
        this.socket = socket;
        this.server = server;
        this.inputReader = new InputReader(socket);
        this.logger = new Logger();

        this.fileReader = fileReader;
        this.responseBuilder = new ResponseBuilder(fileReader);
        this.startingPath = startingPath;
        try{
            this.requestBuilder = new RequestBuilder(startingPath, inputReader, fileReader, parser);
            this.sender = new OutputBuilder(socket);
        } catch(IOException ioe){}
    }

    public void run() {
        Request request = requestBuilder.buildRequest();
        logger.logRequest(request);
        Response response = responseBuilder.buildResponse(request);
        sender.sendResponse(response, request);
        socket.close();
    }
}

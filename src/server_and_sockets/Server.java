import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    private int port;
    private ServerSocketInterface serverSocket;
    private List<Thread> threadList = Collections.synchronizedList(new ArrayList<Thread>());
    private String startingPath;
    private FileReaderInterface fileReader;
    private Logger logger;
    
    
    public Server(int port, String startingPath, ServerSocketInterface serverSocket, Logger logger){
        this.port = port;
        this.startingPath = startingPath;
        this.fileReader = new FileReader();
        this.serverSocket = serverSocket;
        this.logger = logger;
    }

    public void go() {
        SocketInterface socket;
        PostParser parser = new PostParser();
        boolean serverRunning = serverSocket.runServer(port);
        if(!serverRunning){
            logger.logServerStartFailed();
        } else {
            logger.logServerStartSuccess(port);
            while(true){
                try{
                    socket = serverSocket.accept();
                    Thread newThread = new RequestThread(socket, this, startingPath, fileReader, parser);
                    threadList.add(newThread);
                    newThread.start();
                }
                catch(IOException ioe){
                    logger.logSocketCouldNotConnect();
                    break;
                }
            }
        }
    }

    public int getThreadCount(){
        return threadList.size();
    }

}

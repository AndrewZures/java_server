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
    
    
    public Server(int port, String startingPath, ServerSocketInterface serverSocket){
        this.port = port;
        this.startingPath = startingPath;
        this.fileReader = new FileReader();
        this.serverSocket = serverSocket;
    }

    public void go() {
        SocketInterface socket;
        boolean status = serverSocket.runServer(port);
        try{
            if(status){
                System.out.println("Server listening on port " + port);
                while(true){
                    try{
                        socket = serverSocket.accept();
                        Thread newThread = new RequestThread(socket, this, startingPath, fileReader);
                        threadList.add(newThread);
                        newThread.start();
                    }
                    catch(IOException ioe){
                        try{
                            System.out.println("could not connect to socket");
                        } catch(NullPointerException npe){}
                        break;
                    }
                }
            } else {
                    System.out.println("Server start error, aborting");
            }
        } catch(NullPointerException npe){}
    }

    public int getThreadCount(){
        return threadList.size();
    }


}

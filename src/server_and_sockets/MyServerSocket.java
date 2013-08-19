import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class MyServerSocket implements ServerSocketInterface {
    private ServerSocket serverSocket;

    public MyServerSocket(){}


    public boolean runServer(int port){
        try{
            this.serverSocket = new ServerSocket(port);
            return true;
        } catch(IOException ioe){
            return false;
        }
    }

    @Override
    public void closeServer() {
        if(serverSocket == null){}
        else{
            try{
                serverSocket.close();
            } catch(IOException ioe){}
        }
    }

    public void setSoTimeOut(int num) throws SocketException{
            serverSocket.setSoTimeout(num);
    }

    public boolean isClosed(){
        if(serverSocket == null) return true;
        return serverSocket.isClosed();
    }

    public SocketInterface accept() throws IOException {
        if(serverSocket == null) return null;
        return new MySocket(serverSocket.accept());
    }

    @Override
    public void closeAccept() {
        //do nothing
    }

    public void setNumSocketsToReturn(int numSockets){
        //do nothing
    }
}

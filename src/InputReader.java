import java.io.IOException;

public class InputReader {
    SocketInterface socket;


    public InputReader(SocketInterface socket){
        this.socket = socket;
    }

    public int readNextChar() {
        try{
            if(socket.getInputStream().available() > 0){
                return socket.getInputStream().read();
            } else return -1;
        }
        catch(IOException ioe){ return -1; }
    }

    public boolean charIsAvailable() {
        try{
            return socket.getInputStream().available() > 0;
        } catch(IOException ioe){ return false; }
    }


}
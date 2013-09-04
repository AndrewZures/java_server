package org.andrewzures.java_server.server_and_sockets;

import org.andrewzures.java_server.Logger;
import org.andrewzures.java_server.PostParser;
import org.andrewzures.java_server.file_reader.FileReader;
import org.andrewzures.java_server.file_reader.FileReaderInterface;
import org.andrewzures.java_server.responders.ResponderInterface;
import org.andrewzures.java_server.response.*;

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
    private ResponseBuilderInterface builder;
    private Logger logger;


    public Server(int port, String startingPath, ServerSocketInterface serverSocket, Logger logger){
        this.port = port;
        this.startingPath = startingPath;
        this.fileReader = new FileReader();
        this.serverSocket = serverSocket;
        this.logger = logger;
        this.builder = new ResponseBuilder(fileReader);
    }

    public boolean addRoute(String method, String path, ResponderInterface responder){
        return builder.addRoute(method, path, responder);
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
                    Thread newThread = new RequestThread(socket, this, startingPath, builder, fileReader, parser);
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

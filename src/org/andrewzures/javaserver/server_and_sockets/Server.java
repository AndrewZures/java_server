package org.andrewzures.javaserver.server_and_sockets;

import org.andrewzures.javaserver.Logger;
import org.andrewzures.javaserver.PostParser;
import org.andrewzures.javaserver.responders.ResponderInterface;
import org.andrewzures.javaserver.response.ResponseBuilder;
import org.andrewzures.javaserver.response.ResponseBuilderInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    private int port;
    private ServerSocketInterface serverSocket;
    private List<Thread> threadList = Collections.synchronizedList(new ArrayList<Thread>());
    private String startingPath;
    private ResponseBuilderInterface builder;
    private Logger logger;


    public Server(int port, String startingPath, ServerSocketInterface serverSocket, Logger logger){
        this.port = port;
        this.startingPath = startingPath;
        this.serverSocket = serverSocket;
        this.logger = logger;
        this.builder = new ResponseBuilder();
    }

    public boolean addRoute(String method, String path, ResponderInterface responder){
        return builder.addRoute(method, path, responder);
    }

    public void add404Responder(ResponderInterface responder){
        builder.add404Responder(responder);
    }

    public void addDirectoryResponder(ResponderInterface responder){
        builder.addDirectoryResponder(responder);
    }

    public void addFileResponder(ResponderInterface responder){
        builder.addFileResponder(responder);
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
                    Thread newThread = new RequestThread(socket, this, startingPath, builder, parser);
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

    public ArrayList<String> getRoutes(){
        return builder.getRoutes();
    }

}

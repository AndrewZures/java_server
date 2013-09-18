package org.andrewzures.javaserver.server_and_sockets;

import org.andrewzures.javaserver.Logger;
import org.andrewzures.javaserver.OutputBuilder;
import org.andrewzures.javaserver.PostParser;
import org.andrewzures.javaserver.request.Request;
import org.andrewzures.javaserver.request.RequestBuilder;
import org.andrewzures.javaserver.response.Response;
import org.andrewzures.javaserver.response.ResponseBuilderInterface;

import java.io.IOException;


public class RequestThread extends Thread {
    Server server;
    SocketInterface socket;
    RequestBuilder requestBuilder;
    ResponseBuilderInterface responseBuilder;
    OutputBuilder sender;
    String startingPath;
    Logger logger;

    public RequestThread(SocketInterface socket, Server server, String startingPath, ResponseBuilderInterface responseBuilder, PostParser parser) {
        this.socket = socket;
        this.server = server;
        this.logger = new Logger();
        this.responseBuilder = responseBuilder;
        this.startingPath = startingPath;
        try{
            this.requestBuilder = new RequestBuilder(startingPath, socket, parser);
            this.sender = new OutputBuilder(this.socket);
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

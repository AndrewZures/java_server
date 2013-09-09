package org.andrewzures.javaserver;

import org.andrewzures.javaserver.responders.DefaultInternalResponder;
import org.andrewzures.javaserver.responders.FormResponder;
import org.andrewzures.javaserver.responders.TimerResponder;
import org.andrewzures.javaserver.server_and_sockets.MyServerSocket;
import org.andrewzures.javaserver.server_and_sockets.Server;

public class Main {

    public static void main(String[] args) {
        ArgumentParser parser = new ArgumentParser(args);
        Logger logger = new Logger();
        Server server = new Server(parser.getPort(), parser.getPath(), new MyServerSocket(), logger);
        server.addRoute("get", "/hello", new DefaultInternalResponder("org/andrewzures/javaserver/resources/welcome.html"));
        server.addRoute("get", "/time", new TimerResponder("org/andrewzures/javaserver/resources/timerResponse.html"));
        server.addRoute("get", "/form", new DefaultInternalResponder("org/andrewzures/javaserver/resources/form.html"));
        server.addRoute("post", "/form", new FormResponder("org/andrewzures/javaserver/resources/formResponse.html"));
        server.go();
    }

}
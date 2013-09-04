package org.andrewzures.java_server;

import org.andrewzures.java_server.responders.DefaultInternalResponder;
import org.andrewzures.java_server.responders.FormResponder;
import org.andrewzures.java_server.responders.TimerResponder;
import org.andrewzures.java_server.server_and_sockets.MyServerSocket;
import org.andrewzures.java_server.server_and_sockets.Server;

public class Main {

    public static void main(String[] args) {
        ArgumentParser parser = new ArgumentParser(args);
        Logger logger = new Logger();
        Server server = new Server(parser.getPort(), parser.getPath(), new MyServerSocket(), logger);
        server.addRoute("get", "/hello", new DefaultInternalResponder("welcome.html"));
        server.addRoute("get", "/time", new TimerResponder("timerResponse.html"));
        server.addRoute("get", "/form", new DefaultInternalResponder("form.html"));
        server.addRoute("post", "/form", new FormResponder("formResponse.html"));
        server.go();
    }

}
package org.andrewzures.javaserver;

import org.andrewzures.javaserver.responders.*;
import org.andrewzures.javaserver.server_and_sockets.MyServerSocket;
import org.andrewzures.javaserver.server_and_sockets.Server;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        ArgumentParser parser = new ArgumentParser(args);
        Logger logger = new Logger();
        Server server = new Server(parser.getPort(), parser.getPath(), new MyServerSocket(), logger);
        server.add404Responder(new File404Responder());
        server.addDirectoryResponder(new DirectoryResponder());
        server.addFileResponder(new FileResponder(Main.getSupportedFileTypes()));
        server.addRoute("get", "/hello", new DefaultInternalResponder("org/andrewzures/javaserver/resources/welcome.html"));
        server.addRoute("get", "/time", new TimerResponder("org/andrewzures/javaserver/resources/timerResponse.html"));
        server.addRoute("get", "/form", new DefaultInternalResponder("org/andrewzures/javaserver/resources/form.html"));
        server.addRoute("post", "/form", new FormResponder("org/andrewzures/javaserver/resources/formResponse.html", new PostParser()));
        server.go();
    }

    public static HashMap<String, String> getSupportedFileTypes(){
            HashMap<String, String> typeMap = new HashMap<String, String>();
            typeMap.put("png", "image/png");
            typeMap.put("jpg", "image/jpeg");
            typeMap.put("pdf", "application/pdf");
            typeMap.put("txt", "text/html");
            typeMap.put("html", "text/html");
            typeMap.put("gif", "image/gif");
            return typeMap;
    }

}
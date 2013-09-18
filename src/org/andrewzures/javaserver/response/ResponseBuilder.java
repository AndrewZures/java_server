package org.andrewzures.javaserver.response;

import org.andrewzures.javaserver.request.Request;
import org.andrewzures.javaserver.responders.ResponderInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ResponseBuilder implements ResponseBuilderInterface {
    private HashMap<String, ResponderInterface> specialPaths;
    private ArrayList<String> supportedMethods;
    private ResponderInterface file404Responder = null;
    private ResponderInterface directoryResponder = null;
    private ResponderInterface fileResponder = null;

    public ResponseBuilder() {
        this.specialPaths = new HashMap<String, ResponderInterface>();
        this.supportedMethods = this.populateSupportedMethodsArray();
    }

    public ArrayList<String> populateSupportedMethodsArray() {
        ArrayList<String> supportedMethods = new ArrayList<String>();
        supportedMethods.add("GET");
        supportedMethods.add("POST");
        return supportedMethods;
    }


    public Response buildResponse(Request request) {
        ResponderInterface responder = null;
        if (requestIsValid(request) && supportedMethods.contains(request.method)) {
            String pathKey = request.method.toLowerCase() + "_" + request.relativePath;
            if (specialPaths.containsKey(pathKey)) {
                responder = specialPaths.get(pathKey);
            } else if (request.method.equalsIgnoreCase("GET")) {
                File file = new File(request.fullPath);
                if (file.exists() && file.isDirectory() && directoryResponder != null) {
                    responder = directoryResponder;

                } else if (file.exists() && fileResponder != null) {
                    responder = fileResponder;
                }
            }
        }

        Response response = new Response();
        Response tempResponse;

        if (responder == null || (tempResponse = responder.respond(request)) == null) {
            if (file404Responder != null) {
                response = file404Responder.respond(request);
            }
        } else {
            response = tempResponse;
        }
        return response;
    }

    @Override
    public boolean addRoute(String method, String path, ResponderInterface responder) {
        String routeID = method.toLowerCase() + "_" + path;
        if (!specialPaths.containsKey(routeID)) {
            specialPaths.put(routeID, responder);
            return true;
        } else {
            return false;
        }
    }

    public void add404Responder(ResponderInterface responder) {
        this.file404Responder = responder;
    }

    public void addDirectoryResponder(ResponderInterface responder) {
        this.directoryResponder = responder;
    }

    public void addFileResponder(ResponderInterface responder) {
        this.fileResponder = responder;
    }

    @Override
    public ArrayList<String> getRoutes() {
        return new ArrayList<String>(specialPaths.keySet());
    }

    private boolean requestIsValid(Request request) {
        return !(request == null
                || request.method == null
                || request.relativePath == null
                || request.fullPath == null);
    }
}

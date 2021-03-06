package org.andrewzures.javaserver.response;

import org.andrewzures.javaserver.request.Request;
import org.andrewzures.javaserver.responders.ResponderInterface;

import java.util.ArrayList;

public interface ResponseBuilderInterface {

    public Response buildResponse(Request request);
    public boolean addRoute(String method, String path, ResponderInterface responder);
    public void add404Responder(ResponderInterface responder);
    public void addDirectoryResponder(ResponderInterface responder);
    public void addFileResponder(ResponderInterface responder);
    public ArrayList<String> getRoutes();


}

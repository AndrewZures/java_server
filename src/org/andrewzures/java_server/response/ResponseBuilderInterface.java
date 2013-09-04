package org.andrewzures.java_server.response;

import org.andrewzures.java_server.request.Request;
import org.andrewzures.java_server.responders.ResponderInterface;

public interface ResponseBuilderInterface {

    public Response buildResponse(Request request);
    public boolean addRoute(String method, String path, ResponderInterface responder);
}

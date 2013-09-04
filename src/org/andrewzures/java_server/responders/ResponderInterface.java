package org.andrewzures.java_server.responders;
import org.andrewzures.java_server.request.Request;
import org.andrewzures.java_server.response.Response;

public interface ResponderInterface {
    public Response respond(Request request);
}

package org.andrewzures.javaserver.responders;
import org.andrewzures.javaserver.request.Request;
import org.andrewzures.javaserver.response.Response;

public interface ResponderInterface {
    public Response respond(Request request);
}

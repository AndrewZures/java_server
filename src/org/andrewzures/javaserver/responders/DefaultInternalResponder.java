package org.andrewzures.javaserver.responders;

import org.andrewzures.javaserver.request.Request;
import org.andrewzures.javaserver.response.Response;

import java.io.InputStream;

public class DefaultInternalResponder implements ResponderInterface {
    private String path;

    public DefaultInternalResponder(String path){
        this.path = path;
    }

    public Response respond (Request request) {
        InputStream stream  = this.getClass().getClassLoader().getResourceAsStream(path);
        if(stream == null) return null;
        Response response = new Response();
        response.inputStream = stream;
        response = this.populateHeader(response);
        return response;
    }

    public Response populateHeader(Response response){
        response.statusCode = "200";
        response.statusText = "OK";
        return response;
    }
}


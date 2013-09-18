package org.andrewzures.javaserver.responders;

import org.andrewzures.javaserver.request.Request;
import org.andrewzures.javaserver.response.Response;

public class File404Responder implements ResponderInterface {

    public Response respond(Request request){
        Response response = new Response();
        response.inputStream = this.getClass().getClassLoader().getResourceAsStream("org/andrewzures/javaserver/resources/404.html");
        response = populateHeaderInformation(response);
        return response;
    }

    public Response populateHeaderInformation(Response response){
        response.statusCode = "404";
        response.statusText = "File Not Found";
        return response;
    }
}

package org.andrewzures.java_server.test;

import org.andrewzures.java_server.file_reader.FileReader;
import org.andrewzures.java_server.file_reader.FileReaderInterface;
import org.andrewzures.java_server.response.Response;

public class MockFileReader extends FileReader implements FileReaderInterface {
    Response response = null;

    public Response readFile(String path) {
        response = buildDefaultResponse();
        response.path = path;
        if(path.equalsIgnoreCase("Static_Pages/form.html")){
            response.body = "firstname: , lastname: ";
        }

        return response;
    }

    public boolean fileIsDirectory(String path) {
        return true;
    }

    public boolean fileExists(String path) {
        return true;
    }


    public void setResponse(Response response){
        this.response = response;
    }

    public void clearResponse(){
        this.response = null;
    }

    private Response buildDefaultResponse(){
        Response response = new Response();
        response.httpType = "HTTP/1.1";
        response.contentType = "text/html";
        response.statusCode = "200";
        response.statusText = "OK";
        return response;
    }



}

package org.andrewzures.javaserver.responders;

import org.andrewzures.javaserver.request.Request;
import org.andrewzures.javaserver.response.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class FileResponder implements ResponderInterface {
    private HashMap<String, String> fileTypeMap;
    private static String DEFAULTCONTENTTYPE = "text/html";
    private static String DEFAULTRESPONSETYPE = "txt";

    public FileResponder(HashMap<String, String> fileTypeMap) {
        this.fileTypeMap = fileTypeMap;
    }

    public Response respond(Request request) {
        Response response = new Response();
        String responseType = determineResponseType(request.fullPath);
        if (responseType != null) {
            File file = new File(request.fullPath);
            try {
                response.inputStream = new FileInputStream(file);
                response = this.setContentType(response, responseType);
                response = this.populateSuccessfulResponse(response);
                return response;
            } catch (FileNotFoundException e) {
            }
        }
        return null;
    }

    private Response populateSuccessfulResponse(Response response) {
        response.statusCode = "200";
        response.statusText = "OK";
        return response;
    }

    public String determineResponseType(String path) {
        String fileType = getFileType(path);
        if (fileTypeMap.containsKey(fileType)) return fileType;
        else return DEFAULTRESPONSETYPE;
    }

    public Response setContentType(Response response, String responseType) {
        if (fileTypeMap.containsKey(responseType)) {
            response.contentType = fileTypeMap.get(responseType);
        } else response.contentType = DEFAULTCONTENTTYPE;
        return response;
    }

    public String getFileType(String path) {
        String[] list;
        if (path.contains(".")) {
            list = path.split("\\.");
            if (list.length <= 0) {
                return null;
            } else {
                return list[list.length - 1];
            }
        }
        return null;
    }


}

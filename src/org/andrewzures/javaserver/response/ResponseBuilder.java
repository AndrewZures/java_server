package org.andrewzures.javaserver.response;

import org.andrewzures.javaserver.file_reader.FileReaderInterface;
import org.andrewzures.javaserver.request.Request;
import org.andrewzures.javaserver.responders.DirectoryResponder;
import org.andrewzures.javaserver.responders.FileResponder;
import org.andrewzures.javaserver.responders.ResponderInterface;
import org.andrewzures.javaserver.responders.file404Responder;

import java.util.ArrayList;
import java.util.HashMap;

public class ResponseBuilder implements ResponseBuilderInterface {
    private FileReaderInterface fileReader;
    private HashMap<String, ResponderInterface> specialPaths;
    private ArrayList<String> supportedMethods;
    private HashMap<String, String> fileTypeMap;

    public ResponseBuilder(FileReaderInterface fileReader){
        this.fileReader = fileReader;
        this.specialPaths = new HashMap<String, ResponderInterface>();
        this.supportedMethods = this.populateSupportedMethodsArray();
        this.fileTypeMap = this.populateFileTypeMap();
    }

    public ArrayList<String> populateSupportedMethodsArray(){
        ArrayList<String> supportedMethods = new ArrayList<String>();
        supportedMethods.add("GET");
        supportedMethods.add("POST");
        return supportedMethods;
    }

    public HashMap<String, String> populateFileTypeMap() {
        HashMap<String, String> typeMap = new HashMap<String, String>();
        typeMap.put("png", "image/png");
        typeMap.put("jpg", "image/jpeg");
        typeMap.put("pdf", "application/pdf");
        typeMap.put("txt", "text/html");
        typeMap.put("html", "text/html");
        typeMap.put("gif", "image/gif");
        return typeMap;
    }

    public Response buildResponse(Request request){
        ResponderInterface responder = null;
        if(requestIsValid(request) && supportedMethods.contains(request.method)){
            String pathKey = request.method.toLowerCase()+"_"+request.relativePath;
            if(specialPaths.containsKey(pathKey)){
                responder = specialPaths.get(pathKey);
            }
            else if(request.method.equalsIgnoreCase("GET")){
                if(fileReader.fileIsDirectory(request.fullPath)){
                    responder = new DirectoryResponder();
                }
                else{
                    responder = new FileResponder(fileTypeMap, fileReader);
                }
            }
        }
        Response response;
        if(responder == null || (response = responder.respond(request)) == null)
            response = new file404Responder().respond(request);
        return response;
    }

    @Override
    public boolean addRoute(String method, String path, ResponderInterface responder) {
        String routeID = method.toLowerCase()+"_"+path;
        if(!specialPaths.containsKey(routeID)){
            specialPaths.put(routeID, responder);
            return true;
        } else {
            return false;
        }
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

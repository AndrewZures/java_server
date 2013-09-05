package org.andrewzures.javaserver.responders;

import org.andrewzures.javaserver.file_reader.FileReaderInterface;
import org.andrewzures.javaserver.request.Request;
import org.andrewzures.javaserver.response.Response;

import java.util.HashMap;

public class FileResponder implements ResponderInterface {
    private HashMap<String, String> fileTypeMap;
    private FileReaderInterface reader;
    private static String DEFAULTCONTENTTYPE = "text/html";
    private static String DEFAULTRESPONSETYPE = "txt";

    public FileResponder(HashMap<String, String> fileTypeMap, FileReaderInterface reader){
        this.fileTypeMap = fileTypeMap;
        this.reader = reader;
    }

    public Response respond(Request request) {
        Response response = null;
        String responseType = determineResponseType(request.fullPath);
        if(responseType != null){
            response = reader.readFile(request.fullPath);
            if(response == null) return null;
            response = this.setContentType(response, responseType);
        }
        return response;
    }

    public String determineResponseType(String path){
        String fileType = getFileType(path);
        if(fileTypeMap.containsKey(fileType)) return fileType;
        else return DEFAULTRESPONSETYPE;
    }

    public Response setContentType(Response response, String responseType){
        if(fileTypeMap.containsKey(responseType)){
            response.contentType = fileTypeMap.get(responseType);
        }
        else response.contentType = DEFAULTCONTENTTYPE;
        return response;
    }

    public String getFileType(String path) {
        String[] list;
        if(path.contains(".")){
            list = path.split("\\.");
            if(list.length <= 0){
                return null;
            }
            else {
                return list[list.length-1];
            }
        }
        return null;
    }



}

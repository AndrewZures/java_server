package org.andrewzures.java_server.responders;

import org.andrewzures.java_server.request.Request;
import org.andrewzures.java_server.response.Response;

import java.io.*;
import java.util.ArrayList;

public class FormResponder implements ResponderInterface {
    private String path;
    String[] list = new String[3];


    public FormResponder(String path){
        this.path = path;
        list[0] = "<%first_param%>";
        list[1] = "<%second_param%>";
        list[2] = "<%third_param%>";

    }

    public Response respond (Request request) {
        String result = getFormBody(request);
        InputStream stream  = this.getClass().getClassLoader().getResourceAsStream(path);
        if(stream == null) return null;
        Response response = new Response();
        response.inputStream = updateFileStreamWithInsertedVariables(stream, parsePostHash(result));
        response = populateSuccessfulResponse(response);
        return response;
    }

    public Response populateSuccessfulResponse(Response response){
        response.statusCode = "200";
        response.statusText = "OK";
        response.contentType = "text";
        return response;
    }

    public InputStream updateFileStreamWithInsertedVariables(InputStream stream, ArrayList<String> valueList){
        String fileString = this.convertStreamToString(stream);
        fileString = insertVariablesIntoFileString(fileString, valueList);
        byte[] bodyArray = fileString.getBytes();
        return new ByteArrayInputStream(bodyArray);
    }
    
    public String insertVariablesIntoFileString(String fileString, ArrayList<String> valueList){

        for(int i = 0; i < valueList.size() && i < 3; i++){
            fileString = fileString.replace(list[i], valueList.get(i));
        }
        return fileString;
    }

    public String getFormBody(Request request){
        String result = "";
        while(request.inputReader.charIsAvailable()){
           result += (char) request.inputReader.readNextChar();
        }
        return result;
    }

    public ArrayList<String> parsePostHash(String postBody){
        ArrayList<String> valueList = new ArrayList<String>();
        String[] keyValuePairs = getKeyValuePairs(postBody);
        for(String pair : keyValuePairs){
            pair = pair.replaceAll("\\+", " ");
            String[] splitElements = pair.split("=");
            if(splitElements.length > 1){
                valueList.add(splitElements[1]);
            }
        }
        return valueList;
    }

    public String[] getKeyValuePairs(String postHash){
       return postHash.split("&");
    }

    public String convertStreamToString(InputStream stream) {
        int bufferSize = 4000;
        Reader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuffer content = new StringBuffer();
        char[] buffer = new char[bufferSize];
        int n;
        try{
            while ( ( n = reader.read(buffer)) != -1 ) {
                content.append(buffer,0,n);
            }
            return content.toString();
        } catch(IOException ioe){
            return null;
        }
    }
}

package org.andrewzures.java_server.responders;

import org.andrewzures.java_server.request.Request;
import org.andrewzures.java_server.response.Response;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimerResponder implements ResponderInterface {

    private String startTime;
    private String endTime;
    private final int SLEEPTIME = 1000;
    private String path;

    public TimerResponder(String path){
        this.path = path;
    }

    public Response respond (Request request){
        InputStream stream  = this.getClass().getClassLoader().getResourceAsStream(path);
        boolean status = this.getTimes();
        if(stream == null || !status) return null;
        Response response = new Response();

        response.inputStream = updateFile(stream);
        response.method = "GET";
        response.statusCode = "200";
        response.statusText = "OK";
        return response;
    }

    public InputStream updateFile(InputStream stream){
         String stringFile = this.convertStreamToString(stream);
         stringFile = this.insertVariablesIntoFile(stringFile);
         byte[] bodyArray = stringFile.getBytes();
         return new ByteArrayInputStream(bodyArray);
    }
    
    public String insertVariablesIntoFile(String fileString){
        fileString = fileString.replace("<%start_time%>", startTime);
        fileString = fileString.replace("<%end_time%>", endTime);
        return fileString;
    }

    public boolean getTimes() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        startTime = dateFormat.format(new Date());
        boolean status = true;
        try{
             Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
            status = false;
        }
        endTime = dateFormat.format(new Date());
        return status;
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

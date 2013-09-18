package org.andrewzures.javaserver;

import org.andrewzures.javaserver.request.Request;
import org.andrewzures.javaserver.server_and_sockets.SocketInterface;

import java.io.IOException;
import java.util.HashMap;

public class PostParser {

    public String getFormBody(Request request) {
        String result = "";
        int count = 0;
        while(count < request.contentLength){
            int nextChar = this.readNextChar(request.socket);
            if(nextChar == -1) break;
            result += (char) nextChar;
            count++;
        }
        return result;
    }

    public HashMap<String, String> parsePostHash(String postBody) {
        HashMap<String, String> postMap = new HashMap<String, String>();
        String[] keyValuePairs = getKeyValuePairs(postBody);
        for (String pair : keyValuePairs) {
            pair = pair.replaceAll("\\+", " ");
            String[] splitElements = pair.split("=");
            if (splitElements.length > 1) {
                postMap.put(splitElements[0], splitElements[1]);
            }
        }
        return postMap;
    }

    public String[] getKeyValuePairs(String postHash) {
        return postHash.split("&");
    }

    public int readNextChar(SocketInterface socket) {
        try {
            return socket.getInputStream().read();
        } catch (IOException ioe) {
            return -1;
        }
    }
}

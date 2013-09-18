package org.andrewzures.javaserver.request;

import org.andrewzures.javaserver.server_and_sockets.SocketInterface;

public class Request {
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getHttpType() {
        return httpType;
    }

    public void setHttpType(String httpType) {
        this.httpType = httpType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setSocket(SocketInterface socket){
        this.socket = socket;
    }

    public SocketInterface getSocket(){
        return this.socket;
    }

    public String method;
    public String relativePath;
    public String fullPath;
    public String httpType;
    public int contentLength;
    public SocketInterface socket;
    public String body;
}

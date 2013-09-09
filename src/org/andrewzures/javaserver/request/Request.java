package org.andrewzures.javaserver.request;

import org.andrewzures.javaserver.InputReader;
import org.andrewzures.javaserver.file_reader.FileReaderInterface;

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

    public InputReader getInputReader() {
        return inputReader;
    }

    public void setInputReader(InputReader inputReader) {
        this.inputReader = inputReader;
    }

    public FileReaderInterface getFileReader() {
        return fileReader;
    }

    public void setFileReader(FileReaderInterface fileReader) {
        this.fileReader = fileReader;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String method;
    public String relativePath;
    public String fullPath;
    public String httpType;
    public int contentLength;
    public InputReader inputReader = null;
    public FileReaderInterface fileReader = null;
    public String body;
}

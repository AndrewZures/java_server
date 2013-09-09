package org.andrewzures.javaserver.response;

import org.andrewzures.javaserver.file_reader.FileReaderInterface;

import java.io.InputStream;

public class Response {

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpType() {
        return httpType;
    }

    public void setHttpType(String httpType) {
        this.httpType = httpType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getLineBreak() {
        return lineBreak;
    }

    public void setLineBreak(String lineBreak) {
        this.lineBreak = lineBreak;
    }

    public String getHeaderBreak() {
        return headerBreak;
    }

    public void setHeaderBreak(String headerBreak) {
        this.headerBreak = headerBreak;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public FileReaderInterface getFileReader() {
        return fileReader;
    }

    public void setFileReader(FileReaderInterface fileReader) {
        this.fileReader = fileReader;
    }

    public String method;
    public String path;
    public String httpType;
    public String contentType;
    public String statusCode;
    public String statusText;
    public String lineBreak = "\r\n";
    public String headerBreak = "\r\n\r\n";
    public String body = null;
    public InputStream inputStream = null;
    public FileReaderInterface fileReader = null;


}

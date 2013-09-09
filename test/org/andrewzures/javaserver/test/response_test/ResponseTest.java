package org.andrewzures.javaserver.test.response_test;

import org.andrewzures.javaserver.file_reader.FileReader;
import org.andrewzures.javaserver.response.Response;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class ResponseTest {

    @Test
    public void testGetandSetMethod() throws Exception {
        Response response = new Response();
        String method = "GET";
        response.setMethod(method);
        assertEquals("GET", response.getMethod());
    }

    @Test
    public void testGetandSetPath() throws Exception {
        Response response = new Response();
        String path = "/test_path";
        response.setPath(path);
        assertEquals("/test_path", response.getPath());
    }

    @Test
    public void testGetandSetHttpType() throws Exception {
        Response response = new Response();
        String httpType = "HTTP/1.1";
        response.setHttpType(httpType);
        assertEquals("HTTP/1.1", response.getHttpType());
    }

    @Test
    public void testGetandSetContentType() throws Exception {
        Response response = new Response();
        String contentType = "text/html";
        response.setContentType(contentType);
        assertEquals("text/html", response.getContentType());
    }

    @Test
    public void testGetandSetStatusCode() throws Exception {
        Response response = new Response();
        String statusCode = "200";
        response.setStatusCode(statusCode);
        assertEquals("200", response.getStatusCode());
    }

    @Test
    public void testGetandSetStatusText() throws Exception {
        Response response = new Response();
        String statusText = "OK";
        response.setStatusText(statusText);
        assertEquals("OK", response.getStatusText());
    }

    @Test
    public void testGetLineBreak() throws Exception {
        Response response = new Response();
        assertEquals("\r\n", response.getLineBreak());
    }

    @Test
    public void testSetLineBreak() throws Exception {
        Response response = new Response();
        String lineBreak = "/h/n";
        response.setLineBreak(lineBreak);
        assertEquals("/h/n", response.getLineBreak());
    }

    @Test
    public void testGetHeaderBreak() throws Exception {
        Response response = new Response();
        assertEquals("\r\n\r\n", response.getHeaderBreak());
    }

    @Test
    public void testSetHeaderBreak() throws Exception {
        Response response = new Response();
        String headerBreak = "/h/n/h/n";
        response.setHeaderBreak(headerBreak);
        assertEquals("/h/n/h/n", response.getHeaderBreak());
    }

    @Test
    public void testGetandSetBody() throws Exception {
        Response response = new Response();
        String body = "test response body";
        response.setBody(body);
        assertEquals("test response body", response.getBody());
    }

    @Test
    public void testGetandSetInputStream() throws Exception {
        ByteArrayInputStream stream = new ByteArrayInputStream("hello".getBytes());
        Response response = new Response();
        response.setInputStream(stream);
        assertNotNull(response.getInputStream());
    }

    @Test
    public void testGetandSetFileReader() throws Exception {
        FileReader reader = new FileReader();
        Response response = new Response();
        response.setFileReader(reader);
        assertNotNull(response.getFileReader());
    }

}

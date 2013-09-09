package org.andrewzures.javaserver.test.request_test;

import org.andrewzures.javaserver.InputReader;
import org.andrewzures.javaserver.file_reader.FileReader;
import org.andrewzures.javaserver.request.Request;
import org.andrewzures.javaserver.server_and_sockets.SocketInterface;
import org.andrewzures.javaserver.test.socket_test.MockSocket;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class RequestTest {


    @Test
    public void testBasicGettersSetters(){
        Request request = new Request();
        String method = "GET";
        String relativePath = "/test_path";
        String fullPath = "./test_path";
        String httpType = "HTTP/1.1";
        int contentLength = 101;
        String body = "test request body";

        request.setMethod(method);
        request.setRelativePath(relativePath);
        request.setFullPath(fullPath);
        request.setHttpType(httpType);
        request.setContentLength(contentLength);
        request.setBody(body);

        assertEquals("GET", request.getMethod());
        assertEquals("/test_path", request.getRelativePath());
        assertEquals("./test_path", request.getFullPath());
        assertEquals("HTTP/1.1", request.getHttpType());
        assertEquals(101, request.getContentLength());
        assertEquals("test request body", request.getBody());
    }

    @Test
    public void testGetandSetInputReader(){
        Request request = new Request();
        SocketInterface socket = new MockSocket();
        request.setInputReader(new InputReader(socket));
        assertNotNull(request.getInputReader());
    }

    @Test
    public void testGetandSetFileReader(){
        Request request = new Request();
        request.setFileReader(new FileReader());
        assertNotNull(request.getFileReader());
    }

}

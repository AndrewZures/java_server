package org.andrewzures.javaserver.test;

import org.andrewzures.javaserver.OutputBuilder;
import org.andrewzures.javaserver.request.Request;
import org.andrewzures.javaserver.response.Response;
import org.andrewzures.javaserver.server_and_sockets.SocketInterface;
import org.andrewzures.javaserver.test.socket_test.MockSocket;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class OutputBuilderTest {

    Response response;
    Request request;
    SocketInterface socket;
    OutputBuilder sender;

    public OutputBuilderTest() throws IOException {
        socket = new MockSocket();
        sender = new OutputBuilder(socket);
    }

    @Test
    public void addSystemParameters1(){
        response = getBasicResponse();
        request = getBasicRequest();
        response = sender.addSystemParametersToResponse(response, request);
        assertEquals("GET", response.method);
        assertEquals("HTTP/1.1", response.httpType);
        assertEquals("/test_path", response.path);
    }

    @Test
    public void testBuildHeader(){
        Response response = getBasicResponse();
        String stringResponse = sender.buildHeader(response);
        assertThat(stringResponse, JUnitMatchers.containsString("HTTP/1.1 200 OK"));
        assertThat(stringResponse, JUnitMatchers.containsString("Content-Type: text/html"));
    }

    @Test
    public void testSendBytes1(){
        boolean result = sender.sendBytes(null);
        assertEquals(true, result);
    }

    @Test
    public void testSendBytes2() {
        response = getBasicResponse();
        request = getBasicRequest();
        File testFile = new File("./test/org/andrewzures/javaserver/test/test_output_file.txt");

        if(testFile.exists()){
            try {
                response.inputStream = new FileInputStream(testFile);
            } catch(IOException ioe){response.inputStream = null; }
            sender.sendResponse(response, request);
        }
        assertThat(socket.getOutputStreamString(), JUnitMatchers.containsString("HTTP/1.1 200 OK"));
        assertThat(socket.getOutputStreamString(), JUnitMatchers.containsString("hello world, this is a file"));
    }

    @Test
    public void testSendBytes3() {
        InputStream stream = new MockInputStream();
        boolean result = sender.sendBytes(stream);
        assertEquals(true, result);
    }

    @Test
    public void testSendBody() throws IOException {
        response = getBasicResponse();
        File testFile = new File("./test/org/andrewzures/javaserver/test/test_output_file.txt");
        FileInputStream stream = new FileInputStream(testFile);
        if(testFile.exists()){
            response.inputStream = stream;
            sender.sendBody(response);
        }
        assertThat(socket.getOutputStreamString(), JUnitMatchers.containsString("hello world, this is a file"));
    }

    @Test
    public void testSendBodyNull(){
        boolean result = sender.sendBody(null);
        assertEquals(false, result);
    }

    @Test
    public void testSendResponse(){
        request = getBasicRequest();
        response = getBasicResponse();
        boolean status = sender.sendResponse(response, request);
        assertEquals(true, status);
    }

    @Test
    public void testSendResponse2(){
        boolean status = sender.sendResponse(null, null);
        assertEquals(false, status);
    }


    public Request getBasicRequest(){
        Request request = new Request();
        request.method = "GET";
        request.httpType = "HTTP/1.1";
        request.relativePath = "/test_path";
        return request;
    }

    public Response getBasicResponse(){
        Response response = new Response();
        response.httpType = "HTTP/1.1";
        response.statusCode = "200";
        response.statusText = "OK";
        response.contentType = "text/html";
        return response;
    }
}

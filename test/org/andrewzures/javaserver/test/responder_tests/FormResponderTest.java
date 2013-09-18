package org.andrewzures.javaserver.test.responder_tests;

import org.andrewzures.javaserver.request.Request;
import org.andrewzures.javaserver.responders.FormResponder;
import org.andrewzures.javaserver.response.Response;
import org.andrewzures.javaserver.server_and_sockets.SocketInterface;
import org.andrewzures.javaserver.test.MockInputStream;
import org.andrewzures.javaserver.test.socket_test.MockSocket;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class FormResponderTest {

    FormResponder builder;

    public FormResponderTest(){
        builder = new FormResponder("org/andrewzures/javaserver/resources/formResponse.html");
    }

    @Test
    public void testGetKeyValuePairs(){
        String testString = "firstname=andrew&lastname=zures";
        String[] pairs = builder.getKeyValuePairs(testString);
        assertEquals("firstname=andrew", pairs[0]);
        assertEquals("lastname=zures", pairs[1]);
    }


    @Test
    public void testParsePostHash2(){
        Request request = new Request();
        request.body = "firstname=andrew&lastname=zures";
        ArrayList<String> resultList = builder.parsePostHash(request.body);
        assertEquals("andrew", resultList.get(0));
        assertEquals("zures", resultList.get(1));
    }


    @Test
    public void convertStreamtoString1() throws IOException {
        String hello = "helloworld";
        byte[] array = hello.getBytes();
        ByteArrayInputStream stream = new ByteArrayInputStream(array);
        String result = builder.convertStreamToString(stream);
        assertEquals("helloworld", result);
    }

    @Test
    public void convertStreamtoString2() throws IOException {
        String hello = "helloworld";
        byte[] array = hello.getBytes();
        ByteArrayInputStream stream = new ByteArrayInputStream(array);
        String result = builder.convertStreamToString(stream);
        assertEquals("helloworld", result);
    }

    @Test
    public void testInsertVariablesIntoFiles(){
        String testString = "this is a test: <%first_param%> end";
        ArrayList<String> testList = new ArrayList<String>();
        testList.add("successful");
        String result = builder.insertVariablesIntoFileString(testString, testList);
        assertEquals("this is a test: successful end", result);
    }

    @Test
    public void testUpdateFileStreamWithVariables(){
        String testString = "helloworld <%first_param%>";
        byte[] array = testString.getBytes();
        ByteArrayInputStream stream = new ByteArrayInputStream(array);
        Response response = new Response();
        response.inputStream = stream;
        ArrayList<String> testList = new ArrayList<String>();
        testList.add("successful");
        response.inputStream = builder.updateFileStreamWithInsertedVariables(stream, testList);
        String result = builder.convertStreamToString(response.inputStream);
        assertEquals("helloworld successful", result);

    }

    @Test
    public void testPopulateSuccessfulResponse(){
        Response response = new Response();
        response = builder.populateSuccessfulResponse(response);
        assertEquals("200", response.statusCode);
        assertEquals("OK", response.statusText);
        assertEquals("text", response.contentType);
    }

    @Test
    public void testGetFormBody(){
        Request request = new Request();
        SocketInterface socket = new MockSocket();
        String testContent = "helloworld";
        socket.setInputStream(testContent);
        request.contentLength = testContent.length();
        request.socket = socket;
        String result = builder.getFormBody(request);
        assertEquals("helloworld", result);
    }


    @Test
    public void testRespond() throws IOException {
        Request request = new Request();
        SocketInterface socket = new MockSocket();
        String testContent = "first_name=andrew";
        socket.setInputStream(testContent);
        request.contentLength = testContent.length();
        request.socket = socket;
        Response response = builder.respond(request);
        String result = convertStreamToString(response.inputStream);
        assertTrue(result.contains("One: andrew"));
    }

    @Test
    public void testBadSocketInputStream(){
        Request request = new Request();
        SocketInterface socket = new MockSocket();
        socket.setInputStream(null);
        request.contentLength = 2;
        request.socket = socket;
        String result = builder.getFormBody(request);
        assertEquals("", result);
    }


    @Test
    public void testConvertStreamToStringIOE(){
        InputStream stream = new MockInputStream();
        String result = builder.convertStreamToString(stream);
        assertEquals(null, result);
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
        } catch(IOException exception){}
        return content.toString();
    }


}

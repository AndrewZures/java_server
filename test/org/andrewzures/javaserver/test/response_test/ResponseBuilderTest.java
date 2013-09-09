package org.andrewzures.javaserver.test.response_test;

import org.andrewzures.javaserver.file_reader.FileReader;
import org.andrewzures.javaserver.file_reader.FileReaderInterface;
import org.andrewzures.javaserver.request.Request;
import org.andrewzures.javaserver.responders.DefaultInternalResponder;
import org.andrewzures.javaserver.responders.FormResponder;
import org.andrewzures.javaserver.responders.TimerResponder;
import org.andrewzures.javaserver.response.Response;
import org.andrewzures.javaserver.response.ResponseBuilder;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ResponseBuilderTest {
    ResponseBuilder builder;

    public ResponseBuilderTest() {
        FileReaderInterface mockFileReader = new FileReader();
        builder = new ResponseBuilder(mockFileReader);
        builder.addRoute("get", "/hello", new DefaultInternalResponder("welcome.html"));
        builder.addRoute("get", "/time", new TimerResponder("timerResponse.html"));
        builder.addRoute("get", "/form", new DefaultInternalResponder("form.html"));
        builder.addRoute("post", "/form", new FormResponder("formResponse.html"));
    }


    @Test
    public void testPopulateSupportedMethodsArray() throws Exception {
        ArrayList<String> testList = builder.populateSupportedMethodsArray();
        assertEquals("GET", testList.get(0));
    }


    @Test
    public void testPopulateFileTypeMap() throws Exception {
        HashMap<String, String> result = builder.populateFileTypeMap();
        assertEquals(result.containsKey("png"), true);
        assertEquals(result.containsValue("image/png"), true);

        assertEquals(result.containsKey("pdf"), true);
        assertEquals(result.containsValue("application/pdf"), true);

        assertEquals(result.containsKey("jpg"), true);
        assertEquals(result.containsValue("image/jpeg"), true);

        assertEquals(result.containsKey("txt"), true);
        assertEquals(result.containsValue("text/html"), true);

    }

    @Test
    public void testBuildResponse1() throws Exception {
        Request request = buildSimpleRequest();
        Response response = builder.buildResponse(request);
        assertEquals("200", response.statusCode);
        assertEquals("OK", response.statusText);
        String result = convertStreamToString(response.inputStream);
        assertTrue(result.contains("Welcome"));
    }

    @Test
    public void testBuildResponse2() throws IOException {
        Request request = buildSimpleRequest();
        request.relativePath = "/time";
        request.fullPath = "./time";
        Response response = builder.buildResponse(request);
        assertEquals("200", response.statusCode);
        assertEquals("OK", response.statusText);
        String result = convertStreamToString(response.inputStream);
        assertTrue(result.contains("start time"));
    }

    @Test
    public void testBuildResponse3() throws IOException {
        Request request = buildSimpleRequest();
        request.relativePath = "/form";
        Response response = builder.buildResponse(request);
        assertEquals("200", response.statusCode);
        assertEquals("OK", response.statusText);
        String result = convertStreamToString(response.inputStream);
        assertTrue(result.contains("name"));
    }

    @Test
    public void testBuildResponse4() throws IOException {
        Request request = buildSimpleRequest();
        request.relativePath = ".";
        request.fullPath = "./sample_test_files";
        request.fullPath = "./sample_test_files";
        request.method = "GET";
        Response response = builder.buildResponse(request);
        assertEquals("200", response.statusCode);
        assertEquals("OK", response.statusText);
        String result = convertStreamToString(response.inputStream);
        assertTrue(result.contains("test_picture.jpg"));
    }


    @Test
    public void testBuildResponse5() throws IOException {
        Request request = buildSimpleRequest();
        request.relativePath = "/sample_test_files/test_picture.jpg";
        request.fullPath = "./sample_test_files/test_picture.jpg";
        request.method = "GET";
        Response response = builder.buildResponse(request);
        assertEquals("200", response.statusCode);
        assertEquals("OK", response.statusText);
        assertEquals("image/jpeg", response.contentType);
    }

    @Test
    public void testgetRoutes() {
        ArrayList<String> routes = builder.getRoutes();
        assertEquals(4, routes.size());
        assertEquals("get_/time", routes.get(1));
    }

    @Test
    public void testAddRoutes() {
        boolean result = builder.addRoute("get", "/test", new DefaultInternalResponder("stuff"));
        assertEquals(true, result);
    }

    @Test
    public void testRouteAddFail() {
        boolean result = builder.addRoute("get", "/hello", new DefaultInternalResponder("stuff"));
        assertEquals(false, result);
    }


    public Request buildSimpleRequest() {
        Request request = new Request();
        request.method = "GET";
        request.relativePath = "/hello";
        request.fullPath = "./hello";
        return request;
    }

    public String convertStreamToString(InputStream stream) {
        int bufferSize = 4000;
        Reader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuffer content = new StringBuffer();
        char[] buffer = new char[bufferSize];
        int n;

        try {
            while ((n = reader.read(buffer)) != -1) {
                content.append(buffer, 0, n);
            }
        } catch (IOException e) {

        }

        return content.toString();
    }


}

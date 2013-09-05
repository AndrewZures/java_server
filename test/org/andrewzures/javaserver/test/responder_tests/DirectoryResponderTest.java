package org.andrewzures.javaserver.test.responder_tests;

import org.andrewzures.javaserver.request.Request;
import org.andrewzures.javaserver.responders.DirectoryResponder;
import org.andrewzures.javaserver.response.Response;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import java.io.File;

import static org.junit.Assert.*;

public class DirectoryResponderTest {
    DirectoryResponder builder = new DirectoryResponder();

    @Test
    public void testBuildParentLink(){
        String path = "./sample_test_files/";
        String responseString = builder.buildParentLink(path);
        assertThat(responseString, JUnitMatchers.containsString("."));
    }

    @Test
    public void testBuildChildrenLinks1(){
        String path = "./sample_test_files/";
        File testFile = new File(path);
        String responseString = builder.buildChildrenLinks(testFile);
        assertThat(responseString, JUnitMatchers.containsString("test_picture"));
    }

    @Test
    public void testBuildChildrenLinks2(){
        String path = "./invalid_path/";
        File testFile = new File(path);
        String responseString = builder.buildChildrenLinks(testFile);
        assertNull(responseString);
    }

    @Test
    public void testGetParentLink(){
        String result = builder.getParentLink();
        assertThat(result, JUnitMatchers.containsString("<a href=\"../\">Go Up A Directory</a>"));
    }

    @Test
    public void testBuildDefaultLink(){
        String result = builder.buildDefaultLink("helloWorld");
        assertThat(result, JUnitMatchers.containsString("<a href=\"helloWorld\">"));
    }

    @Test
    public void testSanitizeString1(){
        String result = builder.sanitizePath("/test_path");
        assertThat(result, JUnitMatchers.containsString("test_path"));
    }

    @Test
    public void testBuildDirectoryLink(){
        String result = builder.buildDirectoryLink("test_path");
        assertEquals("<a href=\"test_path/\">test_path</a>", result);
    }

   @Test
    public void testSanitizeString2(){
       String result = builder.sanitizePath("helloWorld");
       assertThat(result, JUnitMatchers.containsString("helloWorld"));
   }

    @Test
    public void testBuildResponse1(){
        Request request = new Request();
        request.method = "GET";
        request.fullPath = "/false_path";
       Response response = builder.respond(request);
       assertNull(response);
    }

    @Test
    public void testBuildResponse2(){
        Request request = new Request();
        request.method = "GET";
        request.fullPath = "sample_test_files/";
        Response response = builder.respond(request);
        assertNotNull(response);
    }

    @Test
    public void testBuildResponse3(){
        Request request = new Request();
        request.method = "GET";
        request.fullPath = "sample_test_files/";
        Response response = builder.respond(request);
        assertNotNull(response.inputStream);

    }

}

package org.andrewzures.javaserver.test.responder_tests;

import org.andrewzures.javaserver.Main;
import org.andrewzures.javaserver.request.Request;
import org.andrewzures.javaserver.request.RequestBuilder;
import org.andrewzures.javaserver.responders.FileResponder;
import org.andrewzures.javaserver.response.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FileResponderTest {
    FileResponder fileResponder;

    public FileResponderTest(){

        this.fileResponder = new FileResponder(Main.getSupportedFileTypes());
    }

    @Test
     public void testGoodPath(){
        Request request = new Request();
        request.fullPath = "./sample_test_files/test_picture.jpg";
        Response response = fileResponder.respond(request);
        assertNotNull(response);
        assertEquals("200", response.statusCode);
        assertEquals("OK", response.statusText);
    }

    @Test
    public void testBadPath(){
        Request request = new Request();
        request.fullPath = "./sample_test_files/test_asdfag";
        Response response = fileResponder.respond(request);
        assertEquals(null, response);
    }

    @Test
    public void testBadFileName(){
        Request request = new Request();
        request.fullPath = "./sample_test_files/test_asdfasdfas.jpg";
        Response response = fileResponder.respond(request);
        assertEquals(null, response);
    }

    @Test
    public void getFileType1(){
        String testString = "some_file.jpg";
        String result = fileResponder.getFileType(testString);
        assertEquals("jpg", result);
    }

    @Test
    public void getFileType2(){
        String testString = "some_file.png";
        String result = fileResponder.getFileType(testString);
        assertEquals("png", result);
    }

    @Test
    public void getFileType3(){
        String testString = "some_file.pdf";
        String result = fileResponder.getFileType(testString);
        assertEquals("pdf", result);
    }

    @Test
    public void getFileType4(){
        String testString = "malformed_file";
        String result = fileResponder.getFileType(testString);
        assertNull(result);
    }


    @Test
    public void getFileType6(){
        String testString = ".";
        String result = fileResponder.getFileType(testString);
        assertNull(result);
    }

    @Test
    public void testSetContentType1(){
        Response response = new Response();
        response = fileResponder.setContentType(response, "png");
        assertEquals("image/png", response.contentType);
    }

    @Test
    public void testSetContentType2(){
        Response response = new Response();
        response = fileResponder.setContentType(response, "unique");
        assertEquals("text/html", response.contentType);
    }

    @Test
    public void testDetermineResponseType(){
        String testString = "unique_file_type.unique";
        String result = fileResponder.determineResponseType(testString);
        assertEquals("txt", result);
    }



    @Test
    public void testDetermineResponseType2(){
        String testString = "test_picture.jpg";
        String result = fileResponder.determineResponseType(testString);
        assertEquals("jpg", result);
    }

}

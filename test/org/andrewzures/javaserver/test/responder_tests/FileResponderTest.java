package org.andrewzures.javaserver.test.responder_tests;

import org.andrewzures.javaserver.file_reader.FileReaderInterface;
import org.andrewzures.javaserver.responders.FileResponder;
import org.andrewzures.javaserver.response.Response;
import org.andrewzures.javaserver.test.MockFileReader;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FileResponderTest {
    FileResponder fileResponder;
    FileReaderInterface reader;
    HashMap<String, String> fileTypeMap;

    public FileResponderTest(){
        this.reader = new MockFileReader();
        fileTypeMap = populateFileTypeMap();
        this.fileResponder = new FileResponder(fileTypeMap, reader);
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
    public void getFileType5(){
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

    public HashMap<String, String> populateFileTypeMap() {
        HashMap<String, String> typeMap = new HashMap<String, String>();
        typeMap.put("png", "image/png");
        typeMap.put("jpg", "image/jpeg");
        typeMap.put("pdf", "application/pdf");
        typeMap.put("txt", "text/html");
        typeMap.put("html", "text/html");
        typeMap.put("gif", "image/gif");
        return typeMap;
    }

}

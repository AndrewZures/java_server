package org.andrewzures.java_server.test;

import org.andrewzures.java_server.file_reader.FileReader;
import org.andrewzures.java_server.file_reader.FileReaderInterface;
import org.andrewzures.java_server.response.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileReaderTest {

    FileReaderInterface reader = new FileReader();
    Response response = new Response();

    @Test
    public void buildResponseForInvalidPath(){
        response = reader.readFile("sample_test_files/false_path");
        assertEquals(null, response);
    }

//    @Test
//    public void BuildResponseForValidPath1(){
//        response = reader.readFile("./src/org/andrewzures/java_server/404.html");
//        assertNotNull(response.inputStream);
//        assertEquals("200", response.statusCode);
//        assertEquals("OK", response.statusText);
//    }

    @Test
    public void BuildResponseForValidPath2(){
        response = reader.readFile("sample_test_files/test_picture.jpg");
        assertNotNull(response.inputStream);
        assertEquals("200", response.statusCode);
        assertEquals("OK", response.statusText);
    }

    @Test
    public void fileIsDirectory1(){
        boolean result = reader.fileIsDirectory(".");
        assertEquals(true, result);
    }

    @Test
    public void fileIsDirectory2(){
        boolean result = reader.fileIsDirectory("./../false_path/");
        assertEquals(false, result);
    }

    @Test
    public void fileIsDirectory3(){
        boolean result = reader.fileIsDirectory("sample_test_files/test_picture.jpg");
        assertEquals(false, result);
    }

    @Test
    public void fileExists1(){
        boolean result = reader.fileExists("sample_test_files/test_picture.jpg");
        assertEquals(true, result);
    }

    @Test
    public void fileExists2(){
        boolean result = reader.fileExists("./false_path/");
        assertEquals(false, result);
    }

}

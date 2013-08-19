import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileReaderTest {

    FileReaderInterface reader = new FileReader();
    Response response = new Response();

    @Test
    public void buildResponseForInvalidPath(){
        response = reader.readFile("testFiles/false_path");
        assertEquals(null, response);
    }

    @Test
    public void BuildResponseForValidPath1(){
        response = reader.readFile("./src/resources/404.html");
        assertNotNull(response.inputStream);
        assertEquals("200", response.statusCode);
        assertEquals("OK", response.statusText);
    }

    @Test
    public void BuildResponseForValidPath2(){
        response = reader.readFile("testFiles/test_picture.jpg");
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
        boolean result = reader.fileIsDirectory("testFiles/test_picture.jpg");
        assertEquals(false, result);
    }

    @Test
    public void fileExists1(){
        boolean result = reader.fileExists("testFiles/test_picture.jpg");
        assertEquals(true, result);
    }

    @Test
    public void fileExists2(){
        boolean result = reader.fileExists("./false_path/");
        assertEquals(false, result);
    }

}
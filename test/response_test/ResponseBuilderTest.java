import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ResponseBuilderTest {
    ResponseBuilder builder;

    public ResponseBuilderTest(){
       FileReaderInterface mockFileReader = new FileReader();
       builder = new ResponseBuilder(mockFileReader);
    }


    @Test
    public void testPopulateSupportedMethodsArray() throws Exception {
        ArrayList<String> testList = builder.populateSupportedMethodsArray();
        assertEquals("GET", testList.get(0));
    }

    @Test
    public void testPopulateSpecialPathsArray() throws Exception {
        HashMap<String, ResponderInterface> specialPaths = builder.populateSpecialPaths();
        assertEquals(true, specialPaths.containsKey("get_/hello"));
        assertEquals(true, specialPaths.get("get_/hello") instanceof DefaultInternalResponder);
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
        assertThat(result, JUnitMatchers.containsString("Welcome"));
    }

    @Test
     public void testBuildResponse2() throws IOException {
        Request request = buildSimpleRequest();
        request.relativePath = "/time";
        Response response = builder.buildResponse(request);
        assertEquals("200", response.statusCode);
        assertEquals("OK", response.statusText);
        String result = convertStreamToString(response.inputStream);
        assertThat(result, JUnitMatchers.containsString("start time"));
    }

    @Test
    public void testBuildResponse3() throws IOException {
        Request request = buildSimpleRequest();
        request.relativePath = "/form";
        Response response = builder.buildResponse(request);
        assertEquals("200", response.statusCode);
        assertEquals("OK", response.statusText);
        String result = convertStreamToString(response.inputStream);
        assertThat(result, JUnitMatchers.containsString("name"));
    }

    @Test
    public void testBuildResponse4() throws IOException {
        Request request = buildSimpleRequest();
        request.relativePath = ".";
        request.fullPath = "./sample_test_files";
        request.method = "GET";
        Response response = builder.buildResponse(request);
        assertEquals("200", response.statusCode);
        assertEquals("OK", response.statusText);
        String result = convertStreamToString(response.inputStream);
        assertThat(result, JUnitMatchers.containsString("test_picture.jpg"));
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


    public Request buildSimpleRequest(){
        Request request = new Request();
        request.method = "GET";
        request.relativePath = "/hello";
        request.fullPath = "./hello";
        return request;
    }

    public String convertStreamToString(InputStream stream) throws IOException {
        int bufferSize = 4000;
        Reader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuffer content = new StringBuffer();
        char[] buffer = new char[bufferSize];
        int n;

        while ( ( n = reader.read(buffer)) != -1 ) {
            content.append(buffer,0,n);
        }

        return content.toString();
    }


}

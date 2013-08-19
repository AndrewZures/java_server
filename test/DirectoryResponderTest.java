import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import java.io.File;

import static org.junit.Assert.*;

public class DirectoryResponderTest {
    DirectoryResponder builder = new DirectoryResponder();

    @Test
    public void testBuildParentLink(){
        String path = "./TestFiles/";
        String responseString = builder.buildParentLink(path);
        assertThat(responseString, JUnitMatchers.containsString("."));
    }

    @Test
    public void testBuildChildrenLinks1(){
        String path = "./TestFiles/";
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
        request.fullPath = "/TestFiles/";
        Response response = builder.respond(request);
        assertNotNull(response);
    }

    @Test
    public void testBuildResponse3(){
        Request request = new Request();
        request.method = "GET";
        request.fullPath = "/TestFiles/";
        Response response = builder.respond(request);
        assertNotNull(response.inputStream);

    }

}

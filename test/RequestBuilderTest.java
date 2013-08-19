import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestBuilderTest {

    RequestBuilder requestBuilder;
    InputReader reader;
    SocketInterface socket;

    public RequestBuilderTest() throws Exception {
        socket = new MockSocket();
        reader = new InputReader(socket);
        requestBuilder = new RequestBuilder(".", reader, new FileReader());
    }

    @Before
    public void runBefore(){
        try {
            FileOutputStream f = new FileOutputStream("file.txt");
            System.setOut(new PrintStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    @Test
    public void testReadHeader1() throws IOException {
        socket.setInputStream("GET /form HTTP/1.1\r\n\r\n");
        String[] result = requestBuilder.readHeader();
        assertEquals("GET", result[0]);
    }

    @Test
    public void testReadHeader2() throws IOException {
        socket.setInputStream("GET /form HTTP/1.1\r\n\r\n");
        String[] result = requestBuilder.readHeader();
        assertEquals("/form", result[1]);
    }

    @Test
    public void testReadHeader3() throws IOException {
        socket.setInputStream("GET /form HTTP/1.1\r\n\r\n");
        String[] result = requestBuilder.readHeader();
        assertEquals("HTTP/1.1", result[2]);
    }

    @Test
    public void testReadHeaderLength() throws IOException {
        socket.setInputStream("GET /form HTTP/1.1\r\n\r\n");
        String[] result = requestBuilder.readHeader();
        assertEquals(3, result.length);
    }

    @Test
    public void testReadHeader5Null() throws IOException {
        socket.setInputStream("");
        String[] result = requestBuilder.readHeader();
        assertNull(result);
    }

    @Test
    public void testRequestHasContent1() throws IOException {
        socket.setInputStream("GET /form HTTP/1.1\r\nContent-Length: 20");
        String[] headerArray = requestBuilder.readHeader();
        boolean result = requestBuilder.requestHasContent(headerArray);
        assertEquals(true, result);
    }

    @Test
    public void testRequestHasContent2() throws IOException {
        socket.setInputStream("GET /form HTTP/1.1\r\n\r\n");
        String[] headerArray = requestBuilder.readHeader();
        boolean result = requestBuilder.requestHasContent(headerArray);
        assertEquals(false, result);
    }

    @Test
    public void testPopulateRequestObject1(){
        String[] headerArray = getHeaderStringArray();
        Request request = requestBuilder.populateRequestMessage(headerArray);
        assertEquals("GET", request.method);
        assertEquals("/form", request.relativePath);
        assertEquals("HTTP/1.1", request.httpType);
    }

    @Test
       public void testPopulateResponseObject2(){
        String[] headerArray = getHeaderStringArray();
        headerArray[3] = "Content-Length:";
        headerArray[4] = "44";
        Request request = requestBuilder.populateRequestMessage(headerArray);
        assertTrue(request.inputReader instanceof InputReader);
    }

    @Test
    public void testPopulateResponseObject4(){
        String[] headerArray = getHeaderStringArray();
        Request request = requestBuilder.populateRequestMessage(headerArray);
        assertEquals(null, request.inputReader);
    }

    @Test
    public void testGetContentLength1(){
        String[] headerArray = getHeaderStringArray();
        headerArray[3] = "Content-Length:";
        headerArray[4] = "22";
        int contentLength = requestBuilder.getContentLength(headerArray);
        assertEquals(22, contentLength);
    }

    @Test
    public void testGetContentLength2(){
        String[] headerArray = getHeaderStringArray();
        int contentLength = requestBuilder.getContentLength(headerArray);
        assertEquals(-1, contentLength);

    }

    @Test
    public void testHasHeaderBreak(){
        String testString = "GET /relativePath HTTP/1.1\r\n\r\n";
        boolean result = requestBuilder.hasHeaderBreak(testString);
        assertEquals(true, result);
    }

    @Test
    public void testStringSanitization(){
        String testString = "element1\r\nelement2 element3\r\nelement4";
        String result = requestBuilder.removeLineBreaks(testString);
        assertEquals("element1 element2 element3 element4", result);
    }

    @Test
    public void testBuildRequest(){
        socket.setInputStream("GET /form HTTP/1.1\r\n\r\n");
        Request request =  requestBuilder.buildRequest();
        assertEquals("GET", request.method);
        assertEquals("/form", request.relativePath);
        assertEquals("HTTP/1.1", request.httpType);
    }



    public String[] getHeaderStringArray(){
        String[] headerArray = new String[40];
        headerArray[0] = "GET";
        headerArray[1] = "/form";
        headerArray[2] = "HTTP/1.1";
        return headerArray;
    }

}

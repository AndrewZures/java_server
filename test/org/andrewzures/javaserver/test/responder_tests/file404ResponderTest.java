package org.andrewzures.javaserver.test.responder_tests;

import org.andrewzures.javaserver.responders.File404Responder;
import org.andrewzures.javaserver.responders.ResponderInterface;
import org.andrewzures.javaserver.response.Response;
import org.junit.Test;

import java.io.*;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class File404ResponderTest {
    ResponderInterface file404Responder;

    public File404ResponderTest() {
        file404Responder = new File404Responder();
    }

    @Test
    public void responds() {
        Response response = file404Responder.respond(null);
        assertEquals("404", response.statusCode);
        assertEquals("File Not Found", response.statusText);
        String body = convertStreamToString(response.inputStream);
        assertTrue(body.contains("File Not Found"));
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
        } catch (IOException exception) {
        }
        return content.toString();
    }
}

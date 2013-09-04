package org.andrewzures.java_server.test;

import org.andrewzures.java_server.ArgumentParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static junit.framework.Assert.assertEquals;

public class ArgumentParserTest {

    private ArgumentParser parser;

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
    public void testGetPath(){
        String[] testInput = new String[2];
        testInput[0] = "-d";
        testInput[1] = "/test_path";
        parser = new ArgumentParser(testInput);
        assertEquals("/test_path", parser.getPath());
    }

    @Test
    public void testGetPath2(){
        String[] testInput = new String[2];
        testInput[1] = "-d";
        testInput[0] = "/test_path";
        parser = new ArgumentParser(testInput);
        assertEquals(".", parser.getPath());
    }

    @Test
    public void testGetPath3(){
        String[] testInput = new String[1];
        testInput[0] = "-d";
        parser = new ArgumentParser(testInput);
        assertEquals(".", parser.getPath());
    }

    @Test
    public void testGetPort1(){
        String[] testInput = new String[2];
        testInput[0] = "-p";
        testInput[1] = "7170";
        parser = new ArgumentParser(testInput);
        assertEquals(7170, parser.getPort());
    }

    @Test
    public void testGetPort2(){
        String[] testInput = new String[2];
        testInput[1] = "-p";
        testInput[0] = "7170";
        parser = new ArgumentParser(testInput);
        assertEquals(8189, parser.getPort());
    }

    @Test
    public void testGetPort3(){
        String[] testInput = new String[1];
        testInput[0] = "-p";
        parser = new ArgumentParser(testInput);
        assertEquals(8189, parser.getPort());
    }

    @Test
    public void testPortAndDirectory1(){
        String[] testInput = new String[] {"-p", "7170", "-d", "/test_path"};
        parser = new ArgumentParser(testInput);
        assertEquals(7170, parser.getPort());
        assertEquals("/test_path", parser.getPath());
    }

    @Test
    public void testPortAndDirectory2(){
        String[] testInput = new String[] {"7170", "-p", "-d", "/test_path"};
        parser = new ArgumentParser(testInput);
        assertEquals(8189, parser.getPort());
        assertEquals("/test_path", parser.getPath());
    }

}

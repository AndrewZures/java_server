package org.andrewzures.javaserver.test;

import org.andrewzures.javaserver.test.socket_test.ClientSocket;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static junit.framework.Assert.assertTrue;

public class MainTest {

    @Before
    public void runBefore() {
        try {
            FileOutputStream f = new FileOutputStream("file.txt");
            System.setOut(new PrintStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMain() {
        try {
            TestThread thread1 = new TestThread();
            thread1.start();
            System.out.println(thread1.getName());
            Thread.sleep(1000);
            ClientSocket socket = new ClientSocket();
            socket.run();
            Thread.sleep(2000);
            thread1.interrupt();

            assertTrue(socket.getResult().contains("test_picture.jpg"));
        } catch (Exception e) {
        }
    }
}

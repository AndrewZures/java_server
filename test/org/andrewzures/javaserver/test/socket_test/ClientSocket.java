package org.andrewzures.javaserver.test.socket_test;

import org.andrewzures.javaserver.server_and_sockets.MySocket;
import org.andrewzures.javaserver.server_and_sockets.SocketInterface;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSocket {
    SocketInterface socket;
    OutputStream outputStream;
    InputStream inputStream;
    String result = null;

    public ClientSocket() {
        try {
            socket = new MySocket(new Socket(InetAddress.getLocalHost(), 8191));
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
        } catch (IOException e) {
        }
    }

    public void run() {
        ByteArrayInputStream stream = new ByteArrayInputStream("GET /sample_test_files HTTP/1.1\r\n\r\n".getBytes());
        this.sendBytes(stream);
        result = this.readInputStream();
        System.out.println("run error");
    }

    public boolean sendBytes(InputStream stream) {
        int bytesRead;
        byte[] buffer = new byte[4096];
        if (stream != null) {
            try {
                while ((bytesRead = stream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                return true;
            } catch (IOException e1) {
                return false;
            }
        }
        return true;
    }

    public String readInputStream() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            String allInput = "";
            String userInput;

            while ((userInput = in.readLine()) != null) {
                allInput += userInput;
            }
            System.out.println("allinput = " + allInput);
            return allInput;
        } catch (IOException e) {
            return "";
        }
    }

    public String getResult() {
        return result;
    }
}

package org.andrewzures.java_server;

import org.andrewzures.java_server.server_and_sockets.SocketInterface;

import java.io.IOException;

public class InputReader {
    SocketInterface socket;


    public InputReader(SocketInterface socket) {
        this.socket = socket;
    }

    public int readNextChar() {
        try {
            return socket.getInputStream().read();
        } catch (IOException ioe) {
            return -1;
        }
    }

    public boolean charIsAvailable() {
        try {
            return socket.getInputStream().available() > 0;
        } catch (IOException ioe) {
            return false;
        }
    }


}
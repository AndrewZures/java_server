package org.andrewzures.java_server.file_reader;

import org.andrewzures.java_server.response.Response;

public interface FileReaderInterface {

    public Response readFile(String path);
    public boolean fileIsDirectory(String path);
    public boolean fileExists(String path);
}

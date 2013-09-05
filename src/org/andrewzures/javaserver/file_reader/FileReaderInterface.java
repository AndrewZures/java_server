package org.andrewzures.javaserver.file_reader;

import org.andrewzures.javaserver.response.Response;

public interface FileReaderInterface {

    public Response readFile(String path);
    public boolean fileIsDirectory(String path);
    public boolean fileExists(String path);
}

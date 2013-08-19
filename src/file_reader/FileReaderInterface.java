public interface FileReaderInterface {

    public Response readFile(String path);
    public boolean fileIsDirectory(String path);
    public boolean fileExists(String path);
}

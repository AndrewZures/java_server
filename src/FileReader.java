import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class FileReader implements FileReaderInterface{
    Response response = new Response();

    public FileReader(){
    }

    public Response readFile(String fullPath) {
        Response response;
        File file = new File(fullPath);
        try{
            FileInputStream stream = new FileInputStream(file);
            response = new Response();
            response.inputStream = stream;
            response.statusCode = "200";
            response.statusText = "OK";
            return response;
        }
        catch(FileNotFoundException ffe){
            return null;
        }
    }

    public boolean fileIsDirectory(String fullPath){
        File file = new File(fullPath);
        return file.exists() && file.isDirectory();
    }

    public boolean fileExists(String fullPath){
        File file = new File(fullPath);
        return file.exists();
    }
}

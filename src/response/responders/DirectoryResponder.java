
import java.io.ByteArrayInputStream;
import java.io.File;

public class DirectoryResponder implements ResponderInterface {
    
    public Response respond(Request request) {
        Response response = new Response();
        String fullPath = sanitizePath(request.fullPath);

        File directory = new File(fullPath);
        if(directory.exists()){
            String responseBody = "";
            responseBody += "<html><body>";
            responseBody += buildParentLink(fullPath);
            responseBody += buildChildrenLinks(directory);
            responseBody += "</body></html>";
            byte[] responseArray = responseBody.getBytes();
            response.inputStream = new ByteArrayInputStream(responseArray);
            response = populateResponseHeaderFields(response);
        }
        else return null;
        return response;
    }

    public String buildParentLink(String fullPath){
        String parentString = "";
        File parentDirectory = new File(fullPath+"../");
        if(parentDirectory.exists()){
            parentString += getParentLink();
            parentString += "<br />";
        } 
        return parentString;
    }

    public String buildChildrenLinks(File directory) {
        if(directory.exists()){
            String childString = "";
            File[] listOfFiles = directory.listFiles();
            for(File file : listOfFiles){
                if(file.isDirectory()) childString += buildDirectoryLink(file.getName());
                else childString += buildDefaultLink(file.getName());
                childString += "<br />";
            }
            return childString;
        } else return null;
    }

    public String sanitizePath(String fullPath){
        if(fullPath.substring(0,1).equalsIgnoreCase("/")){
            fullPath = fullPath.substring(1);
        }
        return fullPath;
    }

    public String getParentLink(){
        return "<a href=\"../\">Go Up A Directory</a>";
    }

    public String buildDefaultLink(String fileName) {
        return "<a href=\""+fileName+"\">"+fileName+"</a>";
    }

    public String buildDirectoryLink(String fileName){
        return "<a href=\""+fileName+"/\">"+fileName+"</a>";
    }

    private Response populateResponseHeaderFields(Response response){
        response.contentType = "text";
        response.statusCode = "200";
        response.statusText = "OK";
        return response;
    }
    
    
}

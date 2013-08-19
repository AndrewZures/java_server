import java.io.InputStream;

public class file404Responder implements ResponderInterface {

    public Response respond(Request request){
        Response response = new Response();
        response.inputStream = this.getClass().getResourceAsStream("404.html");
        response = populateHeaderInformation(response);
        return response;
    }

    public Response populateHeaderInformation(Response response){
        response.statusCode = "404";
        response.statusText = "File Not Found";
        return response;
    }
}

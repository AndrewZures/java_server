public class Logger {

    public void logRequest(Request request){
        if(validRequest(request)){
            System.out.println("Request received: "+request.method+" "+request.relativePath+" "+request.httpType);
        }
        else{
            System.out.println("Invalid Request Received");
        }
    }

    public void logServerStartSuccess(int port){
        System.out.println("Server listening on port " + port);
    }

    public void logServerStartFailed(){
        System.out.println("Server start error, aborting");
    }

    public void logSocketCouldNotConnect(){
        System.out.println("could not connect to socket");
    }


    public boolean validRequest(Request request){
        return request != null
                && request.method != null
                && request.relativePath != null
                && request.httpType != null;
    }


}

public class Logger {

    public void logRequest(Request request){
        try{
            if(validRequest(request)){
                System.out.println("Request received: "+request.method+" "+request.relativePath+" "+request.httpType);
            }
            else{
                System.out.println("Invalid Request Received");
            }
        } catch(NullPointerException npe){}
    }


    public boolean validRequest(Request request){
        return request != null
                && request.method != null
                && request.relativePath != null
                && request.httpType != null;
    }


}

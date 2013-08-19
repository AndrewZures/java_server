import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OutputBuilder {
    SocketInterface socket;
    OutputStream fileOut;

    public OutputBuilder(SocketInterface socket) throws IOException {
        this.socket = socket;
        fileOut = socket.getOutputStream();
    }

    public boolean sendResponse(Response response, Request request){
        response = addSystemParametersToResponse(response, request);
        boolean headerStatus = sendHeader(response);
        boolean bodyStatus = sendBody(response);
        return headerStatus && bodyStatus;
    }

    public boolean sendHeader(Response response){
        String header = this.buildHeader(response);
        if(header == null) return false;
        byte[] headerArray = header.getBytes();
        ByteArrayInputStream stream = new ByteArrayInputStream(headerArray);
        return sendBytes(stream);
    }

    public boolean sendBody(Response response){
        if(response == null) return false;
        return sendBytes(response.inputStream);
    }

    public boolean sendBytes(InputStream stream){
        int bytesRead;
        byte[] buffer = new byte[4096];
        if(stream != null){
            try {
                while((bytesRead = stream.read(buffer)) != -1){
                    fileOut.write(buffer, 0, bytesRead);
                }
                return true;
            } catch (IOException e1) {}
        }
        return true;
    }

    public String buildHeader(Response response){
        if(response == null) return null;
        String header = "";
        header += response.httpType + " ";
        header += response.statusCode + " ";
        header += response.statusText;
        header += response.lineBreak;
        header += "Content-Type: "+ response.contentType;
        header += response.headerBreak;
        return header;
    }

    public Response addSystemParametersToResponse(Response response, Request request){
        if(response == null || request == null) return null;
        response.httpType = "HTTP/1.1";
        response.method = request.method;
        response.path = request.relativePath;
        return response;
    }
}
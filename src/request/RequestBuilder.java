import java.io.IOException;

public class RequestBuilder {
    InputReader inputReader;
    FileReaderInterface fileReader;
    private String startingPath;
    private final static int METHOD = 0;
    private final static int PATH = 1;
    private final static int HTTPTYPE = 2;
    private final static int MINHEADERLENGTH = 2;

    public RequestBuilder(String startingPath, InputReader inputReader, FileReaderInterface fileReader) throws IOException {
        this.inputReader = inputReader;
        this.fileReader = fileReader;
        this.startingPath = startingPath;
    }

    public Request buildRequest(){
        String[] headerString = this.readHeader();
        return populateRequestMessage(headerString);
    }

    public Request populateRequestMessage(String[] headerArray){
        Request request = new Request();
        if(headerArray != null && headerArray.length > MINHEADERLENGTH){
            request.method = headerArray[METHOD];
            request.relativePath = headerArray[PATH];
            request.fullPath = startingPath+request.relativePath;
            request.httpType = headerArray[HTTPTYPE];
            request.fileReader = this.fileReader;
            if(requestHasContent(headerArray)){
                request.contentLength = getContentLength(headerArray);
                request.inputReader = this.inputReader;
            }
        }
        else return null;
        return request;
    }

    public boolean requestHasContent(String[] headerArray){
        int index = 0;
        while(index < headerArray.length && headerArray[index] != null){
            if(headerArray[index].equalsIgnoreCase("Content-Length:")){
                return true;
            }
            index++;
        }
        return false;
    }

    public int getContentLength(String[] headerArray){
        int index = 0;
        while(index < headerArray.length && headerArray[index] != null){
            if(headerArray[index].equalsIgnoreCase("Content-Length:")){
                if(headerArray[index+1] != null){
                    return Integer.parseInt(headerArray[index+1]);
                }
            }
            index++;
        }
        return -1;
    }

    public String[] readHeader() {
        String header = "";
        while(inputReader.charIsAvailable()){
            header += (char) inputReader.readNextChar();
            if(header.contains("\r\n\r\n"))
                break;
        }
        if(header.equalsIgnoreCase("")) return null;
        header = this.removeLineBreaks(header);
        return header.split(" ");
    }



    public boolean hasHeaderBreak(String word){
        return word.contains("\r\n\r\n");
    }

    public String removeLineBreaks(String header){
        return header.replaceAll("\r\n", " ");
    }
}
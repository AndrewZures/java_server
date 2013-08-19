
public class Main {

    public static void main(String[] args) {
        ArgumentParser parser = new ArgumentParser(args);
        Server server = new Server(parser.getPort(), parser.getPath(), new MyServerSocket());
        server.go();
    }

}
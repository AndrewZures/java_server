
public class Main {

    public static void main(String[] args) {
        ArgumentParser parser = new ArgumentParser(args);
        Logger logger = new Logger();
        Server server = new Server(parser.getPort(), parser.getPath(), new MyServerSocket(), logger);
        server.go();
    }

}
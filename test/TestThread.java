public class TestThread extends Thread implements Runnable {

    public void run(){
        String[] args = new String[2];
        args[0] = "-p";
        args[1] = "8191";
        Main.main(args);
    }
}

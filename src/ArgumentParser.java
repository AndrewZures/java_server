public class ArgumentParser {
    String[] args;

    public ArgumentParser(String[] args){
       this.args = args;
    }

    public String getPath(){
        for(int i = 0; i < args.length; i++){
            if(args[i].equalsIgnoreCase("-d") && i+1 < args.length){
                return args[i+1];
            }
        }
        return ".";
    }

    public int getPort(){
        for(int i = 0; i < args.length; i++){
            if(args[i].equalsIgnoreCase("-p") && i+1 < args.length){
                String portString = args[i+1];
                try{
                 return Integer.parseInt(portString);
                } catch(NumberFormatException nfe){
                    System.out.println("specified port could not be read");
                }
            }
        }
        return 8189;
    }
}

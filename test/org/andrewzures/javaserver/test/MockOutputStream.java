package org.andrewzures.javaserver.test;

import java.io.OutputStream;

public class MockOutputStream extends OutputStream {
    byte[] receivingArray = new byte[1000000];
    int index = 0;


    public void write(int b) {
        if(index < receivingArray.length){
            receivingArray[index] = (byte) b;
            index++;
        }
    }

    public void write(byte[] carrier){
        for(int i = 0; i < carrier.length; i++){
            this.write((int) carrier[i]);
        }
    }

    public void write(byte[] carrier, int offset, int writeLength){

        for(int i = 0; i  < writeLength; i++){
            this.write((int) carrier[i]);
        }
    }

    public String getResultString(){
        return new String(receivingArray);
    }

    public int getIndex(){
        return index;
    }

}

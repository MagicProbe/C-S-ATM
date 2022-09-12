package com.minipax;


import java.io.*;

public class Utils {

    private PrintWriter printWriter = null;
    private BufferedReader bufferedReader = null;

    public Utils(PrintWriter printWriter, BufferedReader bufferedReader){
        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
    }

    public void SendMsg(String msg) {
        printWriter.println(msg);
    }

    public String ReceiveMsg() throws IOException {
        return bufferedReader.readLine();
    }

}

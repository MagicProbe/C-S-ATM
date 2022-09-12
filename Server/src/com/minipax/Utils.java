package com.minipax;

import com.minipax.entity.User;

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
        SendMsg("-readLine");
        return bufferedReader.readLine();
    }

    public String ReceiveMsg(String tip) throws IOException {
        SendMsg("-readLine");
        SendMsg(tip);
        return bufferedReader.readLine();
    }

    public static synchronized void OutputObject(User user){
        FileOutputStream fo = null;
        ObjectOutputStream oos = null;
        try {
            fo = new FileOutputStream(Constant.USER_DATA_PATH + user.getId());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            oos = new ObjectOutputStream(fo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized User InputObject(String id){
        User user = new User();
        FileInputStream fi = null;
        ObjectInputStream ois = null;
        try {
            fi = new FileInputStream(Constant.USER_DATA_PATH + id);
        } catch (FileNotFoundException e) {
            return null;
        }

        try {
            ois = new ObjectInputStream(fi);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            user = (User)ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean isNum(String str){
        for (int i = 0;i<str.length();i++){
            if (str.charAt(i) < '0' || str.charAt(i) > '9'){
                return false;
            }
        }
        return true;
    }

}

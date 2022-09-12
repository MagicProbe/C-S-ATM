package com.minipax;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入主机ip：");
        String ip = scanner.next();
        System.out.print("请输入主机端口：");
        String port = scanner.next();

        Socket socket = null;
        try {
            socket = new Socket(ip, Integer.parseInt(port));
        } catch (IOException e) {
            System.out.println("连接失败！");
            System.exit(0);
        }

        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));


        try {
            Start(printWriter, bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void Start(PrintWriter printWriter, BufferedReader bufferedReader) throws IOException {
        Utils utils = new Utils(printWriter, bufferedReader);
        while (true) {
            String Msg = utils.ReceiveMsg();
            if (Msg.equals("-continue")){
                continue;
            }

            if(Msg.equals("-exit")) {
                break;
            }

            if(Msg.equals("-readLine")){
                System.out.print(utils.ReceiveMsg());
                utils.SendMsg(new Scanner(System.in).next());
                continue;
            }
            System.out.println(Msg);
        }
    }

}

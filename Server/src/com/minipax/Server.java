package com.minipax;

import com.minipax.entity.User;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.minipax.Constant.ROOT_PATH;
import static com.minipax.Constant.USER_DATA_PATH;
import static java.lang.System.exit;

public class Server {

    public static List<String > IpList = new ArrayList<>();

    public static List<User> UserList = new ArrayList<>();

    public static void main(String[] args) {

        Server server = new Server();
        server.start();

    }

    private void start() {

        ServerSocket server = null;

        try {
            server = new ServerSocket();
            Constant.setUserDataPath();
            System.out.print("请输入端口：");
            server.bind(new InetSocketAddress(Integer.parseInt(new Scanner(System.in).next())));
            ExecutorService executorService = Executors.newFixedThreadPool(20);

            File file = new File(USER_DATA_PATH);
            if(!file.exists()) {
                if (file.mkdir()) {
                    System.out.println("用户文件夹创建成功！");
                } else {
                    System.out.println("用户文件夹创建失败！");
                }
            }
            executorService.execute(new Monitor());
            executorService.execute(new upDate());
            while(true) {
                Socket accept = server.accept();
                IpList.add(accept.getRemoteSocketAddress().toString());
                executorService.execute(new ClientHandler(accept));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Monitor implements Runnable{

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String cmd = scanner.next();

                if ("-status".equals(cmd)) {
                    System.out.println("共有" + IpList.size() + "名用户在访问。");
                    for (String ip : Server.IpList) {
                        System.out.println(ip);
                    }
                }

                if("-user".equals(cmd)) {
                    System.out.println("共有" + UserList.size() + "名用户已注册。");
                    for (User user :
                            UserList) {
                        System.out.println("id：" + user.getId()
                                + " 密码：" + user.getPassword()
                                + " 是否封禁：" + (user.getLOCK_TIME() > System.currentTimeMillis()/1000/60/60/24)
                                + " 金额：" + user.getCash());
                    }
                }

                if ("-exit".equals(cmd)) {
                    exit(1);
                }
            }
        }
    }

    private class upDate implements Runnable {
        @Override
        public void run() {
            while (true) {
                UpdateUser();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void UpdateUser() {
            UserList.clear();
            File file = null;
            file = new File(Constant.USER_DATA_PATH);
            File[] array = file.listFiles();
            if(array != null) {
                for (int i = 0; i < array.length; i++){
                    if(array[i].isFile()){
                        UserList.add(Utils.InputObject(array[i].getName()));
                    }
                }
            }
        }
    }

}

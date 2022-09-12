package com.minipax;

import com.minipax.dao.UserDao;
import com.minipax.entity.User;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    Socket socket = null;
    UserDao userDao = null;
    Utils utils = null;

    public ClientHandler(Socket socket) throws IOException {

        this.socket = socket;

        printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

        this.utils = new Utils(printWriter, bufferedReader);
        this.userDao = new UserDao(printWriter, bufferedReader);

    }

    @Override
    public void run() {
        try {
            Start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Start() throws IOException {
        while(true){
            utils.SendMsg("----欢迎ATM机存取款系统----");
            utils.SendMsg("1.登录 2.注册 3.退卡");
            String choice1 = utils.ReceiveMsg("请选择菜单：");

            if("1".equals(choice1)){
                User user = userDao.LoginIn();
                if(user == null){
                    utils.SendMsg("登陆失败！");
                } else {
                    utils.SendMsg("登陆成功！");
                    Constant.LoggedIn.put(user.getId(), "login");
                    while (true){
                        utils.SendMsg("--欢迎进入主画面--");
                        utils.SendMsg("1.查询余额 2.取款 3.存款 4.转账 5.修改密码 6.退卡");
                        String choice2 = utils.ReceiveMsg("请选择菜单：");

                        if("1".equals(choice2)){
                            utils.SendMsg(user.getCash());
                            continue;
                        }

                        if("2".equals(choice2)){
                            if(userDao.Withdraw(user)){
                                utils.SendMsg("取款成功!");
                            } else {
                                utils.SendMsg("取款失败!");

                            }
                            utils.OutputObject(user);
                            continue;
                        }

                        if("3".equals(choice2)){
                            if(userDao.Deposit(user)){
                                utils.SendMsg("存款成功！");
                            } else {
                                utils.SendMsg("存款失败！");
                            }
                            continue;
                        }

                        if("4".equals(choice2)){
                            if(userDao.Transfer(user)){
                                utils.SendMsg("转账成功！");
                            } else {
                                utils.SendMsg("转账失败！");
                            }
                            continue;
                        }

                        if("5".equals(choice2)){
                            if (userDao.ChangePassword(user)) {
                                utils.SendMsg("修改密码成功！");
                            } else {
                                utils.SendMsg("修改密码失败！");
                            }

                            utils.OutputObject(user);
                            continue;
                        }

                        if("6".equals(choice2)){
                            Constant.LoggedIn.remove(user.getId());
                            break;
                        }
                    }

                }
                continue;
            }

            if("2".equals(choice1)){
                if(userDao.SignIn()){
                    utils.SendMsg("注册成功！");
                } else {
                    utils.SendMsg("注册失败！");
                }
                continue;
            }

            if("3".equals(choice1)){
                utils.SendMsg("-exit");
                Server.IpList.remove(socket.getRemoteSocketAddress().toString());
                socket.close();
                break;
            }
        }
    }




}

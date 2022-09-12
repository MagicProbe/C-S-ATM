package com.minipax.dao;

import com.minipax.Constant;
import com.minipax.Utils;
import com.minipax.entity.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class UserDao {

    Utils utils = null;

    public UserDao(PrintWriter printWriter, BufferedReader bufferedReader){
        utils = new Utils(printWriter, bufferedReader);
    }

    public boolean SignIn() throws IOException {
        String id = utils.ReceiveMsg("请输入卡号：");
        String password = utils.ReceiveMsg("请输入密码：");
        String name = utils.ReceiveMsg("请输入真实姓名：");
        String identity = utils.ReceiveMsg("请输入身份证：");
        String cash = utils.ReceiveMsg("请输入开户金额：");

        if(!utils.isNum(id)){
            return false;
        }

        File file = null;
        file = new File(Constant.USER_DATA_PATH);
        File[] array = file.listFiles();
        for (int i = 0;i<array.length;i++){
            if(array[i].isFile()){
                if (array[i].getName().equals(id)){
                    return false;
                }
            }
        }

        if(!utils.isNum(identity)){
            return false;
        }

        if(!utils.isNum(cash)){
            return false;
        }


        User user = new User();
        user.setId(id);
        user.setPassword(password);
        user.setName(name);
        user.setIdentity(identity);
        user.setCash(cash);
        user.setLOCK_TIME(System.currentTimeMillis()/1000/60/60/24);
        user.setLockTimes(0);

        utils.OutputObject(user);

        return true;
    }

    public User LoginIn() throws IOException {
        String id = utils.ReceiveMsg("请输入卡号：");
        String password = utils.ReceiveMsg("请输入密码：");

        if (Constant.LoggedIn.containsKey(id)){
            return null;
        }
        User user = utils.InputObject(id);

        if (user == null){
            return user;
        }

        if(user.getLOCK_TIME() > System.currentTimeMillis()/1000/60/60/24){
            return null;
        }

        if(user.getPassword().equals(password)){
            return user;
        }

        else{
            if(user.getLockTimes() >= 2){
                LockUser(user);
                user.setLockTimes(0);
            } else {
                user.setLockTimes(user.getLockTimes() + 1);
            }
            utils.OutputObject(user);
            return null;
        }

    }

    public boolean Withdraw(User user) throws IOException {
        String n = utils.ReceiveMsg("请输入取款金额：");

        if(!utils.isNum(n)){
            return false;
        }

        int c = Integer.parseInt(user.getCash()) - Integer.parseInt(n);
        if(c < 0){
            return false;
        }
        user.setCash(c + "");
        utils.OutputObject(user);
        return true;
    }

    public boolean Deposit(User user) throws IOException {
        String n = utils.ReceiveMsg("请输入存款金额：");

        if(!utils.isNum(n)){
            return false;
        }

        user.setCash((Integer.parseInt(user.getCash()) + Integer.parseInt(n)) + "");
        utils.OutputObject(user);
        return true;
    }

    public boolean Transfer(User user1) throws IOException {
        User user2 = utils.InputObject(utils.ReceiveMsg("请输入目标账户："));
        if (user2 == null){
            return false;
        }

        if (user2.getId().equals(user1.getId())) {
            return false;
        }

        String n = utils.ReceiveMsg("请输入转账金额：");

        if(!utils.isNum(n)){
            return false;
        }

        int c = Integer.parseInt(user1.getCash()) - Integer.parseInt(n);
        if(c < 0){
            return false;
        }

        user1.setCash(c + "");
        user2.setCash((Integer.parseInt(user2.getCash()) + Integer.parseInt(n)) + "");
        utils.OutputObject(user1);
        utils.OutputObject(user2);
        return true;
    }

    public boolean ChangePassword(User user) throws IOException {
        String password = utils.ReceiveMsg("请输入原密码：");
        if(!user.getPassword().equals(password)){
            return false;
        }

        String pw1 = utils.ReceiveMsg("请输入新密码：");
        String pw2 = utils.ReceiveMsg("请再次输入新密码：");

        if(pw1.equals(pw2)){
            user.setPassword(pw1);
            return true;
        } else {
            return false;
        }
    }

    public void LockUser(User user){
        user.setLOCK_TIME(System.currentTimeMillis()/1000/60/60/24 + 1);
    }

}

package com.minipax;

import java.util.HashMap;
import java.util.Map;

public class Constant {

    public static Map<String, String> LoggedIn = new HashMap<>();

    public static String USER_DATA_PATH;
    public static String ROOT_PATH;

    public static void setUserDataPath(){
        ROOT_PATH = Constant.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        USER_DATA_PATH = ROOT_PATH.substring(0, ROOT_PATH.lastIndexOf("/")) + "/UserData/";
    }

}

package com.minipax.entity;

import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String password;
    private String name;
    private String identity;
    private String cash;
    private long LOCK_TIME;
    private Integer lockTimes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public long getLOCK_TIME(){
        return LOCK_TIME;
    }

    public void setLOCK_TIME(long LOCK_TIME){
        this.LOCK_TIME = LOCK_TIME;
    }

    public Integer getLockTimes() {
        return lockTimes;
    }

    public void setLockTimes(Integer lockTimes) {
        this.lockTimes = lockTimes;
    }

}

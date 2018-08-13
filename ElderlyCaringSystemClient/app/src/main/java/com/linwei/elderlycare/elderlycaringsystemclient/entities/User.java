package com.linwei.elderlycare.elderlycaringsystemclient.entities;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    //0 represents guardian. 1 represents ward
    private int type;
    private String address;
    private String phonenum;
    private User bindUser;


    public User() {
        super();
    }

    public User(String id) {
        this.setObjectId(id);
    }

    public void setBindUser(User bindUser) {
        this.bindUser = bindUser;
    }

    public User getBindUser() {
        return bindUser;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}

package com.linwei.elderlycare.elderlycaringsystemclient.entities;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    //0 represents guardian. 1 represents ward
    private int type;
    private String address;

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

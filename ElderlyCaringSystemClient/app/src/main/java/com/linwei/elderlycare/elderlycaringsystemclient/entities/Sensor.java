package com.linwei.elderlycare.elderlycaringsystemclient.entities;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class Sensor extends BmobObject {
    private String title;
    private String sensorBTAddress;
    private BmobUser owner;
    private String sensorDescription;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public void setSensorDescription(String sensorDescription) {
        this.sensorDescription = sensorDescription;
    }

    public String getSensorDescription() {
        return sensorDescription;
    }

    public boolean isOn() {
        return powerStatus;
    }

    private boolean powerStatus;

    public void setOwner(BmobUser owner) {
        this.owner = owner;
    }

    public void setSensorBTAddress(String sensorBTAddress) {
        this.sensorBTAddress = sensorBTAddress;
    }

    public String getSensorBTAddress() {
        return sensorBTAddress;
    }

    public BmobUser getOwner() {
        return owner;
    }

    public void setPowerStatus(boolean powerStatus) {
        this.powerStatus = powerStatus;
    }


}

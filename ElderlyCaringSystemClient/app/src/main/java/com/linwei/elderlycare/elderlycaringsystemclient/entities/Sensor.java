package com.linwei.elderlycare.elderlycaringsystemclient.entities;

public class Sensor {
    private String sensorBTAddress;
    private User owner;

    public boolean isOn() {
        return powerStatus;
    }

    private boolean powerStatus;

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setSensorBTAddress(String sensorBTAddress) {
        this.sensorBTAddress = sensorBTAddress;
    }

    public String getSensorBTAddress() {
        return sensorBTAddress;
    }

    public User getOwner() {
        return owner;
    }

    public void setPowerStatus(boolean powerStatus) {
        this.powerStatus = powerStatus;
    }


}

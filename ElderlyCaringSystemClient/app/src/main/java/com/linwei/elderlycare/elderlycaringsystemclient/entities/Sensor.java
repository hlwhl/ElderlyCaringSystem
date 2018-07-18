package com.linwei.elderlycare.elderlycaringsystemclient.entities;

public class Sensor {
    private String sensorBTAddress;
    private User owner;
    private String sensorDescription;

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

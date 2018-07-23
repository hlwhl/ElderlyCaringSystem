package com.linwei.elderlycare.elderlycaringsystemclient.entities;

import cn.bmob.v3.BmobObject;

public class SensorDataHistory extends BmobObject {
    private Sensor sensor;
    private String type;
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}

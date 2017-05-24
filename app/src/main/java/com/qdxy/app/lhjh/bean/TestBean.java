package com.qdxy.app.lhjh.bean;

/**
 * Created by KY on 2016/11/2.
 */

public class TestBean {

    private String uName;
    private String data;

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "uName='" + uName + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

}

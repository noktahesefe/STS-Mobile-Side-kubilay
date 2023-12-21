package com.example.birdaha.General;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HwModel implements Serializable {


    String title;

    @SerializedName("content")
    String info;


    public HwModel(String title, String info) {
        this.title = title;
        this.info = info;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}

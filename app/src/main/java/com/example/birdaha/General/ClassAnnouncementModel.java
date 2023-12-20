package com.example.birdaha.General;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClassAnnouncementModel implements Serializable {

    @SerializedName("announcement_title")
    private String title;
    @SerializedName("announcement_content")
    private String details;

    public ClassAnnouncementModel(String title, String details){
        this.title = title;
        this.details = details;
    }
    public ClassAnnouncementModel(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}

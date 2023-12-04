package com.example.birdaha.General;

public class ClassAnnouncementModel {

    private String title;
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

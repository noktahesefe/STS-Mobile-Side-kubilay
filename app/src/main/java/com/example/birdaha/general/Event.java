package com.example.birdaha.General;

import android.graphics.Bitmap;

import java.util.List;

public class Event {
    private String title;
    private int imageResource;
    // private List<Bitmap> photos;
    private String details;

    public Event(String title, int imageResource, String details){
        this.title = title;
        this.imageResource = imageResource;
        this.details = details;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /*public List<Bitmap> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Bitmap> photos) {
        this.photos = photos;
    } */

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}

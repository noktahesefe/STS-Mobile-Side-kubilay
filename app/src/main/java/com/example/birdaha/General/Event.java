package com.example.birdaha.General;

import android.graphics.Bitmap;

import java.util.List;

/**
 * This class represents an event with a title, image resource, and details.
 */
public class Event {
    private String title;          // The title of the event
    private int imageResource;     // The resource ID of the event's image
    private String details;        // Details or description of the event
    // private List<Bitmap> photos; // Potential list of event photos (commented out for simplicity)

    /**
     * Constructor for the Event class.
     *
     * @param title         The title of the event.
     * @param imageResource The resource ID of the event's image.
     * @param details       Details or description of the event.
     */
    public Event(String title, int imageResource, String details) {
        this.title = title;
        this.imageResource = imageResource;
        this.details = details;
    }

    /**
     * Get the resource ID of the event's image.
     *
     * @return The resource ID of the event's image.
     */
    public int getImageResource() {
        return imageResource;
    }

    /**
     * Set the resource ID of the event's image.
     *
     * @param imageResource The resource ID of the event's image.
     */
    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    /**
     * Get the title of the event.
     *
     * @return The title of the event.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the event.
     *
     * @param title The title of the event.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the details or description of the event.
     *
     * @return Details or description of the event.
     */
    public String getDetails() {
        return details;
    }

    /**
     * Set the details or description of the event.
     *
     * @param details Details or description of the event.
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /*
    // Uncomment the following methods if you want to work with event photos
    public List<Bitmap> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Bitmap> photos) {
        this.photos = photos;
    }
    */
}

package com.example.birdaha.General;

/**
 * This class represents a general announcement that contains a title and details.
 */
public class GeneralAnnouncement {
    private String title;   // The title of the general announcement
    private String details; // The details or content of the general announcement

    /**
     * Constructor for the GeneralAnnouncement class.
     *
     * @param title   The title of the general announcement.
     * @param details The details or content of the general announcement.
     */
    public GeneralAnnouncement(String title, String details) {
        this.title = title;
        this.details = details;
    }

    /**
     * Get the title of the general announcement.
     *
     * @return The title of the general announcement.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the general announcement.
     *
     * @param title The title of the general announcement.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the details or content of the general announcement.
     *
     * @return The details or content of the general announcement.
     */
    public String getDetails() {
        return details;
    }

    /**
     * Set the details or content of the general announcement.
     *
     * @param details The details or content of the general announcement.
     */
    public void setDetails(String details) {
        this.details = details;
    }
}

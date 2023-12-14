package com.example.birdaha.General;

/**
 * This class represents a homework model that contains information about a homework assignment.
 */
public class HwModel {

    private String title; // The title of the homework
    private String info;  // Additional information about the homework

    /**
     * Constructor for the HwModel class.
     *
     * @param title The title of the homework.
     * @param info  Additional information about the homework.
     */
    public HwModel(String title, String info) {
        this.title = title;
        this.info = info;
    }

    /**
     * Get the title of the homework.
     *
     * @return The title of the homework.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the homework.
     *
     * @param title The title of the homework.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get additional information about the homework.
     *
     * @return Additional information about the homework.
     */
    public String getInfo() {
        return info;
    }

    /**
     * Set additional information about the homework.
     *
     * @param info Additional information about the homework.
     */
    public void setInfo(String info) {
        this.info = info;
    }
}


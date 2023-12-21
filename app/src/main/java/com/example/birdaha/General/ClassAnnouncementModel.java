package com.example.birdaha.General;

/**
 * This class represents a class announcement model with a title and details.
 */
public class ClassAnnouncementModel {

    private String title;   // The title of the class announcement

    private String teacherName; // The name of the teacher who posted the announcement



    private String details; // Details or content of the class announcement

    /**
     * Constructor for the ClassAnnouncementModel class.
     *
     * @param title   The title of the class announcement.
     * @param details Details or content of the class announcement.
     */
    public ClassAnnouncementModel(String title, String details, String teacherName) {
        this.title = title;
        this.details = details;
        this.teacherName = teacherName;
    }

    /**
     * Constructor for the ClassAnnouncementModel class with only a title.
     *
     * @param title The title of the class announcement.
     */
    public ClassAnnouncementModel(String title) {
        this.title = title;
    }

    /**
     * Get the title of the class announcement.
     *
     * @return The title of the class announcement.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the class announcement.
     *
     * @param title The title of the class announcement.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the details or content of the class announcement.
     *
     * @return Details or content of the class announcement.
     */
    public String getDetails() {
        return details;
    }

    /**
     * Set the details or content of the class announcement.
     *
     * @param details Details or content of the class announcement.
     */
    public void setDetails(String details) {
        this.details = details;
    }


    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}

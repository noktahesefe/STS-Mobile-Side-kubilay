package com.example.birdaha.General;

/**
 * This class represents a homework model that contains information about a homework assignment.
 */
public class HwModel {

    private String title; // The title of the homework
    private String hw_content;  // Additional information about the homework


    private String lecture;

    private String dueDate;

    private int grade;


    /**
     * Constructor for the HwModel class.
     *
     * @param title The title of the homework.
     * @param hw_content  Additional information about the homework.
     */
    public HwModel(String title, String hw_content, String lecture, String dueDate, int grade) {
        this.title = title;
        this.hw_content = hw_content;
        this.lecture = lecture;
        this.dueDate = dueDate;
        this.grade = grade;
    }

    public HwModel(String title,String lecture) {
        this.title = title;
        this.hw_content = lecture;

    }

    public HwModel(String title, String hw_content, String lecture, String dueDate) {
        this.title = title;
        this.hw_content = hw_content;
        this.lecture = lecture;
        this.dueDate = dueDate;
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
    public String getHw_content() {
        return hw_content;
    }

    /**
     * Set additional information about the homework.
     *
     * @param hw_content Additional information about the homework.
     */
    public void setHw_content(String hw_content) {
        this.hw_content = hw_content;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

}


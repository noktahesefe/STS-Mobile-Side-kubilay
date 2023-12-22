package com.example.birdaha.General;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HwModel implements Serializable {
    private int homework_id;
    private int teacher_id;
    private int classroom_id;
    private String course_name;
    private String due_date;
    private String image;
    private String title;

    @SerializedName("content")
    private String info;

    public HwModel(int classroom_id, int teacher_id, String course_name, String dueDate, String title, String info, String image){
        this.classroom_id = classroom_id;
        this.teacher_id = teacher_id;
        this.course_name = course_name;
        this.due_date = dueDate;
        this.title = title;
        this.info = info;
        this.image = image;
    }

    public int getHomework_id() {
        return homework_id;
    }

    public void setHomework_id(int homework_id) {
        this.homework_id = homework_id;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public int getClassroom_id() {
        return classroom_id;
    }

    public void setClassroom_id(int classroom_id) {
        this.classroom_id = classroom_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

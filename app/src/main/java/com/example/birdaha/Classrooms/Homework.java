package com.example.birdaha.Classrooms;

import java.util.Date;

public class Homework {
    private Lecture lecture;
    private Date dueDate;
    private int grade;

    public Homework(Lecture lecture, Date dueDate, int grade) {
        this.lecture = lecture;
        this.dueDate = dueDate;
        this.grade = grade;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}

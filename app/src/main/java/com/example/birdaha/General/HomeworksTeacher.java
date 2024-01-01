package com.example.birdaha.General;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeworksTeacher {
    List<HwModel> homeworks;
    List<StudentModel> students;

    public List<StudentModel> getStudents() {
        return students;
    }

    public void setStudents(List<StudentModel> students) {
        this.students = students;
    }

    public List<HwModel> getHomeworks() {
        return homeworks;
    }

    public void setHomeworks(List<HwModel> homeworks) {
        this.homeworks = homeworks;
    }

}

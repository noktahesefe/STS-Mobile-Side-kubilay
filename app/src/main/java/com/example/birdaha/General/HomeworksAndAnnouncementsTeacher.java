package com.example.birdaha.General;

import com.example.birdaha.Users.Student;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeworksAndAnnouncementsTeacher {
    List<HwModel> homeworks;
    List<StudentModel> students;
    @SerializedName("classroom-announcements")
    List<ClassAnnouncementModel> classAnnouncementModels;

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

    public List<ClassAnnouncementModel> getClassAnnouncementModels() {
        return classAnnouncementModels;
    }

    public void setClassAnnouncementModels(List<ClassAnnouncementModel> classAnnouncementModels) {
        this.classAnnouncementModels = classAnnouncementModels;
    }
}

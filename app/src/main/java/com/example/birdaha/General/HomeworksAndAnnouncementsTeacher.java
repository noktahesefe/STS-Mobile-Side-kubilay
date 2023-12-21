package com.example.birdaha.General;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeworksAndAnnouncementsTeacher {
    List<HwModel> homeworks;
    @SerializedName("classroom-announcements")
    List<ClassAnnouncementModel> classAnnouncementModels;

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

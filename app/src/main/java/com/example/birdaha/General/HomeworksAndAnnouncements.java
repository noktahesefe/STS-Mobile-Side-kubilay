package com.example.birdaha.General;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeworksAndAnnouncements {
    private List<HwModel> homeworks;
    @SerializedName("classroom_announcements")
    private List<ClassAnnouncementModel> classAnnouncements;

    public List<HwModel> getHomeworks() {
        return homeworks;
    }

    public void setHomeworks(List<HwModel> homeworks) {
        this.homeworks = homeworks;
    }

    public List<ClassAnnouncementModel> getClassAnnouncements() {
        return classAnnouncements;
    }

    public void setClassAnnouncements(List<ClassAnnouncementModel> classAnnouncements) {
        this.classAnnouncements = classAnnouncements;
    }
}

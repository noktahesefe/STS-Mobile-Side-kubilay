package com.example.birdaha.General;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnnouncementsStudent {
    @SerializedName("classroom_announcements")
    private List<ClassAnnouncementModel> classAnnouncements;

    public List<ClassAnnouncementModel> getClassAnnouncements() {
        return classAnnouncements;
    }

    public void setClassAnnouncements(List<ClassAnnouncementModel> classAnnouncements) {
        this.classAnnouncements = classAnnouncements;
    }
}

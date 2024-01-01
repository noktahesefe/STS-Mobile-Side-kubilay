package com.example.birdaha.General;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnnouncementsTeacher {
    @SerializedName("classroom-announcements")
    List<ClassAnnouncementModel> classroomAnnouncements;

    public List<ClassAnnouncementModel> getClassroomAnnouncements() {
        return classroomAnnouncements;
    }

    public void setClassroomAnnouncements(List<ClassAnnouncementModel> classroomAnnouncements) {
        this.classroomAnnouncements = classroomAnnouncements;
    }
}

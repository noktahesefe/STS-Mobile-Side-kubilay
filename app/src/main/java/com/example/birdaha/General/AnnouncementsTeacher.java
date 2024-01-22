package com.example.birdaha.General;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnnouncementsTeacher {
    // List of class announcements for teachers
    @SerializedName("classroom-announcements")
    List<ClassAnnouncementModel> classroomAnnouncements;

    /**
     * Getter method for retrieving the list of class announcements for teachers.
     *
     * @return List of class announcements for teachers.
     */
    public List<ClassAnnouncementModel> getClassroomAnnouncements() {
        return classroomAnnouncements;
    }

}

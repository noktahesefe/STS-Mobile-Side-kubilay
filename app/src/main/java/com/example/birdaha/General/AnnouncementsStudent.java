package com.example.birdaha.General;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnnouncementsStudent {
    // List of class announcements
    @SerializedName("classroom_announcements")
    private List<ClassAnnouncementModel> classAnnouncements;

    /**
     * Getter method for retrieving the list of class announcements.
     *
     * @return List of class announcements.
     */
    public List<ClassAnnouncementModel> getClassAnnouncements() {
        return classAnnouncements;
    }

}

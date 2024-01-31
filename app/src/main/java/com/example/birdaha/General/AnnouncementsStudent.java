package com.example.birdaha.General;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementsStudent {
    // List of class announcements
    @SerializedName("classroom_announcements")
    private ArrayList<ClassAnnouncementModel> classAnnouncements;

    /**
     * Getter method for retrieving the list of class announcements.
     *
     * @return List of class announcements.
     */
    public ArrayList<ClassAnnouncementModel> getClassAnnouncements() {
        return classAnnouncements;
    }

}

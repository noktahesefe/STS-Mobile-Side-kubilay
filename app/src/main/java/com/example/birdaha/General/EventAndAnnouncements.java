package com.example.birdaha.General;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventAndAnnouncements {
    private List<Event> events;
    @SerializedName("announcements")
    private List<GeneralAnnouncement> generalAnnouncements;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<GeneralAnnouncement> getGeneralAnnouncements() {
        return generalAnnouncements;
    }

    public void setGeneralAnnouncements(List<GeneralAnnouncement> generalAnnouncements) {
        this.generalAnnouncements = generalAnnouncements;
    }
}

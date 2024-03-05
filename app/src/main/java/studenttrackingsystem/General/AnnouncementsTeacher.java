package studenttrackingsystem.General;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AnnouncementsTeacher {
    @SerializedName("classroom-announcements")
    ArrayList<ClassAnnouncementModel> classroomAnnouncements;

    public ArrayList<ClassAnnouncementModel> getClassroomAnnouncements() {
        return classroomAnnouncements;
    }

    public void setClassroomAnnouncements(ArrayList<ClassAnnouncementModel> classroomAnnouncements) {
        this.classroomAnnouncements = classroomAnnouncements;
    }
}

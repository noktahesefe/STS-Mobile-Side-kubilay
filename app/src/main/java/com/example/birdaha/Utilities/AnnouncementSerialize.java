package com.example.birdaha.Utilities;

import com.example.birdaha.General.ClassAnnouncementModel;
import com.example.birdaha.General.HwModel;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class AnnouncementSerialize implements Serializable {
    public ArrayList<ClassAnnouncementModel> arr;
    public static AnnouncementSerialize fromJson(String json)
    {
        return new Gson().fromJson(json, AnnouncementSerialize.class);
    }
}

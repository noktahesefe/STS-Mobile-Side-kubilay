package com.example.birdaha.Users;

import com.example.birdaha.Classrooms.Classroom;
import com.example.birdaha.Classrooms.Lecture;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User{
    private List<Classroom> classrooms;
    private List<Lecture> lectures;
    private static boolean isAlive = false;

    public Teacher(String username, String password){
        this.username = username;
        this.password = password;
        this.classrooms = new ArrayList<Classroom>();
        this.lectures = new ArrayList<Lecture>();
        this.isAlive = true;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public static boolean isAlive() {
        return isAlive;
    }
}

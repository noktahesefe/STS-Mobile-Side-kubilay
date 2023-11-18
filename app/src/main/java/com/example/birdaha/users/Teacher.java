package com.example.birdaha.users;

import com.example.birdaha.classrooms.Classroom;
import com.example.birdaha.classrooms.Lecture;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User{
    private List<Classroom> classrooms;
    private List<Lecture> lectures;

    public Teacher(String username, String password){
        this.username = username;
        this.password = password;
        this.classrooms = new ArrayList<Classroom>();
        this.lectures = new ArrayList<Lecture>();
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
}

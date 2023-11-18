package com.example.birdaha.users;

import com.example.birdaha.classrooms.Homework;

import java.util.ArrayList;
import java.util.List;

public class Student extends User{
    private List<Homework> homeworks;

    public Student(String username, String password){
        this.username = username;
        this.password = password;
        this.homeworks = new ArrayList<Homework>();
    }

    public List<Homework> getHomeworks() {
        return homeworks;
    }

    public void setHomeworks(List<Homework> homeworks) {
        this.homeworks = homeworks;
    }
}

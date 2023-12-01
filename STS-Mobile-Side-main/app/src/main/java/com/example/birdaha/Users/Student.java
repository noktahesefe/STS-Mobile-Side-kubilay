package com.example.birdaha.Users;

import com.example.birdaha.Classrooms.Homework;

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

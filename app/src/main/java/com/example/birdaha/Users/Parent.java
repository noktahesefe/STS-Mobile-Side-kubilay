package com.example.birdaha.Users;

import java.util.ArrayList;
import java.util.List;

public class Parent extends User{
    private List<Student> students;
    private static boolean isAlive = false;

    public Parent(String username, String password){
        this.username = username;
        this.password = password;
        this.students = new ArrayList<Student>();
        this.isAlive = true;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public static boolean isAlive() {
        return isAlive;
    }
}

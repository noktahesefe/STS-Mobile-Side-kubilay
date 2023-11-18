package com.example.birdaha.users;

import java.util.ArrayList;
import java.util.List;

public class Parent extends User{
    private List<Student> students;

    public Parent(String username, String password){
        this.username = username;
        this.password = password;
        this.students = new ArrayList<Student>();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}

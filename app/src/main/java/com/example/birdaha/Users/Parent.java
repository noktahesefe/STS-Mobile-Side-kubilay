package com.example.birdaha.Users;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Parent extends User{
    private int parent_id;
    private List<Student> students;

    public Parent(String name, int parent_id){
        this.name = name;
        this.parent_id = parent_id;
        this.students = new ArrayList<>();
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}

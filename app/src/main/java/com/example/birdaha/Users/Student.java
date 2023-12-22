package com.example.birdaha.Users;

import androidx.annotation.NonNull;

import com.example.birdaha.Classrooms.Classroom;
import com.example.birdaha.Classrooms.Homework;

import java.util.ArrayList;
import java.util.List;

public class Student extends User{
    //private int student_id;
    //private int classroom_id;
    //private int school_no;
    private List<Homework> homeworks;

    public Student(String name, int student_id, Classroom classroom, int school_no){
        this.name = name;
        //this.student_id = student_id;
        //this.classroom_id = classroom_id;
        //this.school_no = school_no;
        this.classroom = classroom;
        this.homeworks = new ArrayList<>();
    }

    @NonNull
    @Override
    public String toString() {
        return this.getName();
    }

    /*public int getClassroom_id() {
        return classroom_id;
    }

    public void setClassroom_id(int classroom_id) {
        this.classroom_id = classroom_id;
    }*/

    /*public int getSchool_no() {
        return school_no;
    }

    public void setSchool_no(int school_no) {
        this.school_no = school_no;
    }*/

    /*public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }*/

    public List<Homework> getHomeworks() {
        return homeworks;
    }

    public void setHomeworks(List<Homework> homeworks) {
        this.homeworks = homeworks;
    }
}

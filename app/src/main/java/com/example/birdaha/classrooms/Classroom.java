package com.example.birdaha.classrooms;

import com.example.birdaha.users.Student;
import com.example.birdaha.users.Teacher;

import java.util.List;

public class Classroom {
    private String name;
    private Teacher classroomTeacher;
    private List<Student> students;
    private List<Homework> homeworks;
    private List<ClassAnnouncement> classAnnouncements;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getClassroomTeacher() {
        return classroomTeacher;
    }

    public void setClassroomTeacher(Teacher classroomTeacher) {
        this.classroomTeacher = classroomTeacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Homework> getHomeworks() {
        return homeworks;
    }

    public void setHomeworks(List<Homework> homeworks) {
        this.homeworks = homeworks;
    }

    public List<ClassAnnouncement> getClassAnnouncements() {
        return classAnnouncements;
    }

    public void setClassAnnouncements(List<ClassAnnouncement> classAnnouncements) {
        this.classAnnouncements = classAnnouncements;
    }
}

package com.example.birdaha.Classrooms;

import com.example.birdaha.General.HwModel;
import com.example.birdaha.Users.Student;
import com.example.birdaha.Users.Teacher;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a classroom entity with information such as classroom name, students, homeworks, and class announcements.
 */
public class Classroom implements Serializable {
    /**
     * The unique identifier for the classroom.
     */
    private int classroom_id;

    /**
     * The name of the classroom.
     */
    @SerializedName("classroom_name")
    private String name;

    /**
     * The list of students enrolled in the classroom.
     */
    private List<Student> students;

    /**
     * The list of homework models associated with the classroom.
     */
    private List<HwModel> homeworks;

    /**
     * The list of class announcements for the classroom.
     */
    private List<ClassAnnouncement> classAnnouncements;

    /**
     * Constructor to initialize a Classroom object with a name and classroom ID.
     *
     * @param name         The name of the classroom.
     * @param classroom_id The unique identifier for the classroom.
     */
    public Classroom(String name, int classroom_id){

        this.classroom_id = classroom_id;
        this.name = name;
    }

    /**
     * Gets the unique identifier for the classroom.
     *
     * @return The classroom ID.
     */
    public int getClassroom_id() {
        return classroom_id;
    }


    /**
     * Sets the unique identifier for the classroom.
     *
     * @param classroom_id The classroom ID to set.
     */
    public void setClassroom_id(int classroom_id) {
        this.classroom_id = classroom_id;
    }

    /**
     * Sets the unique identifier for the classroom.
     *
     * @param classroom_id The classroom ID to set.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the classroom.
     *
     * @param name The name to set for the classroom.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of students enrolled in the classroom.
     *
     * @return The list of students.
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Sets the list of students for the classroom.
     *
     * @param students The list of students to set.
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

}

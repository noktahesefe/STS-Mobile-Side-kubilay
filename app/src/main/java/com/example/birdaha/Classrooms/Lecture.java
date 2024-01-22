package com.example.birdaha.Classrooms;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Represents a lecture or course with information such as course ID and name.
 */
public class Lecture implements Serializable {
    /**
     * The unique identifier for the course.
     */
    private int course_id;

    /**
     * The name of the course.
     */
    @SerializedName("course_name")
    private String name;

    /**
     * Constructor to initialize a Lecture object with a course ID and name.
     *
     * @param course_id The unique identifier for the course.
     * @param name      The name of the course.
     */
    public Lecture(int course_id, String name) {
        this.course_id = course_id;
        this.name = name;
    }

    /**
     * Gets the unique identifier for the course.
     *
     * @return The course ID.
     */
    public int getCourse_id() {
        return course_id;
    }

    /**
     * Sets the unique identifier for the course.
     *
     * @param course_id The course ID to set.
     */
    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    /**
     * Gets the name of the course.
     *
     * @return The name of the course.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the course.
     *
     * @param name The name to set for the course.
     */
    public void setName(String name) {
        this.name = name;
    }
}

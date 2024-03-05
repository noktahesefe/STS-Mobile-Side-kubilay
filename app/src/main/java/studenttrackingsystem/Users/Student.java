package studenttrackingsystem.Users;

import androidx.annotation.NonNull;

import studenttrackingsystem.Classrooms.Classroom;

public class Student extends User {

    public Student(String name, int student_id, Classroom classroom, long school_no) {
        this.name = name;
        this.student_id = student_id;
        this.school_no = school_no;
        this.classroom = classroom;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getName();
    }
}

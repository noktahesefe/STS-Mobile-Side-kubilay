package com.example.birdaha.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.birdaha.Adapters.StudentAdapter;
import com.example.birdaha.General.ClassAnnouncementModel;
import com.example.birdaha.R;
import com.example.birdaha.Users.Student;
import com.example.birdaha.Utilities.HomeworkStudentsViewInterface;

import java.util.ArrayList;

/**
 * This class represents the HomeworkStudentsScreen activity.
 * It implements the HomeworkStudentsViewInterface.
 */
public class HomeworkStudentsScreen extends AppCompatActivity implements HomeworkStudentsViewInterface {

    // List of students
    ArrayList<Student> students = new ArrayList<>();

    // Adapter for the student list
    private StudentAdapter studentAdapter;

    /**
     * This method is called when the activity is starting.
     * It sets the content view, initializes the RecyclerView and sets the students.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_students_screen);
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_students);

        setStudents();
        studentAdapter = new StudentAdapter(this, students,this);
        recyclerView.setAdapter(studentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * This method sets the students for the RecyclerView.
     * It is a dummy method for testing and can be removed later.
     */
    private void setStudents(){
        String[] titles = getResources().getStringArray(R.array.Students);
        for (int i = 0; i < titles.length; i++) {
            students.add(new Student(titles[i]));
        }
    }

    /**
     * This method is called when a student item in the RecyclerView is clicked.
     * It inflates a dialog for entering grade and note, and shows the dialog.
     * @param position The position of the clicked item.
     * @param view The view within the RecyclerView that was clicked.
     */
    @Override
    public void onHomeworkStudentItemClick(int position, View view) {
        // Inflate the dialog_grade_entry.xml layout
        View dialogView = getLayoutInflater().inflate(R.layout.overlay_student_grade, null);
        final EditText editTextGrade = dialogView.findViewById(R.id.editTextGrade);
        final EditText editTextNote = dialogView.findViewById(R.id.editTextNote);

        // Create and show the AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("Enter Grade and Note")
                .setView(dialogView)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String grade = editTextGrade.getText().toString().trim();
                        String note = editTextNote.getText().toString().trim();

                        // TODO: Process the entered grade and note here
                        // For example, save them or send them to a database
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}
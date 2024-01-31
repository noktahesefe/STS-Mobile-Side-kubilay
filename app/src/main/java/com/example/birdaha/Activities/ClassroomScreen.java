package com.example.birdaha.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.birdaha.Classrooms.Classroom;
import com.example.birdaha.R;
import com.example.birdaha.Users.Teacher;

/**
 * This class represents the screen for a specific classroom. It provides options
 * for navigating to the announcements and homework sections of the classroom.
 * The class receives data through Intent and displays relevant information.
 *
 */
public class ClassroomScreen extends AppCompatActivity {

    Button classroomAnnouncementsButton;
    Button classroomHomeworksButton;
    TextView classroomName;

    /**
     * Initializes the activity, sets up the UI components, and handles button clicks.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_screen);
        
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Set the title
            actionBar.setTitle("");
        }
      
        // Retrieve classroom information from the Intent
        Intent intent2 = getIntent();
        if (intent2 != null) {
            Classroom classroom = (Classroom) intent2.getSerializableExtra("classroom");

            // Display the classroom name
            classroomName = findViewById(R.id.classroom_info);
            classroomName.setText(classroom.getName());

            // Set up button for navigating to announcements screen
            classroomAnnouncementsButton = findViewById(R.id.classroom_announcements);
            classroomAnnouncementsButton.setOnClickListener(v -> {
                Intent getIntent = getIntent();
                if (getIntent != null) {
                    Teacher teacher = (Teacher) getIntent.getSerializableExtra("teacher");
                    Intent intent = new Intent(ClassroomScreen.this, ClassRoomAnnouncementScreen.class);
                    intent.putExtra("teacher", teacher);
                    intent.putExtra("classroom", classroom);
                    startActivity(intent);
                }
            });

            // Set up button for navigating to homework screen
            classroomHomeworksButton = findViewById(R.id.classroom_homeworks);
            classroomHomeworksButton.setOnClickListener(v -> {
                Intent getIntent2 = getIntent();
                if (getIntent2 != null) {
                    Teacher teacher = (Teacher) getIntent2.getSerializableExtra("teacher");
                    Intent intent = new Intent(ClassroomScreen.this, ClassroomHomeworkScreen.class);
                    intent.putExtra("teacher", teacher);
                    intent.putExtra("classroom", classroom);
                    startActivity(intent);
                }
            });
        }
    }
}
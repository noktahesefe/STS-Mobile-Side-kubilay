package com.example.birdaha.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.birdaha.R;

/**
 * This class represents the ClassroomScreen activity, which serves as the main screen for a classroom.
 * It provides buttons to navigate to announcements and homework screens.
 */
public class ClassroomScreen extends AppCompatActivity {

    Button classroomAnnouncementsButton; // Button to navigate to announcements screen
    Button classroomHomeworksButton; // Button to navigate to homework screen

    /**
     * Called when the activity is created. Initializes the user interface and sets up event handlers
     * for navigation to announcements and homework screens.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_screen);

        classroomAnnouncementsButton = findViewById(R.id.classroom_announcements); // Find the announcements button
        classroomHomeworksButton = findViewById(R.id.classroom_homeworks); // Find the homework button

        // Set a click listener for the announcements button to navigate to the announcements screen
        classroomAnnouncementsButton.setOnClickListener(v -> {
            Intent intent = new Intent(ClassroomScreen.this, ClassRoomAnnouncementScreen.class);
            startActivity(intent);
        });

        // Set a click listener for the homework button to navigate to the homework screen
        classroomHomeworksButton.setOnClickListener(v -> {
            Intent intent = new Intent(ClassroomScreen.this, ClassroomHomeworkScreen.class);
            startActivity(intent);
        });
    }
}

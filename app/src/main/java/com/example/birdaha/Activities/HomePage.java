package com.example.birdaha.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.birdaha.Fragments.EventFragment;
import com.example.birdaha.Fragments.GeneralAnnouncementFragment;
import com.example.birdaha.R;

/**
 * This class represents the HomePage activity, which serves as the main screen for the application.
 * It displays event and announcement fragments on the home page.
 */
public class HomePage extends AppCompatActivity {

    /**
     * Called when the activity is created. Initializes the user interface by replacing the event
     * and announcement containers with their respective fragments.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_home_page); // Set the content view to the home page layout

        // Replace the event container with the EventFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.event_container, new EventFragment())
                .commit();

        // Replace the announcement container with the GeneralAnnouncementFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.announcement_container, new GeneralAnnouncementFragment())
                .commit();
    }
}

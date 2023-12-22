package com.example.birdaha.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdaha.Adapters.ClassAnnouncementAdapter;
import com.example.birdaha.General.ClassAnnouncementModel;
import com.example.birdaha.General.GeneralAnnouncement;
import com.example.birdaha.R;
import com.example.birdaha.Utilities.ClassAnnouncementViewInterface;

import java.util.ArrayList;

/**
 * ClassAnnouncementScreen is an activity that displays a list of class announcements.
 * It implements the ClassAnnouncementViewInterface to handle click events on the list items.
 */
public class ClassAnnouncementScreen extends AppCompatActivity implements ClassAnnouncementViewInterface {

    // SearchView for filtering the list of class announcements
    SearchView search;

    // ArrayList to hold the class announcements
    ArrayList<ClassAnnouncementModel> classAnnouncementModels = new ArrayList<>();

    /**
     * This method is called when the activity is starting.
     * It initializes the activity and sets up the RecyclerView and SearchView.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_announcement_screen);
        RecyclerView recyclerView = findViewById(R.id.caRecyclerView);

        search = findViewById(R.id.searchView_Announcement);

        Intent intent = getIntent();
        if(intent != null){
            classAnnouncementModels = (ArrayList<ClassAnnouncementModel>) intent.getSerializableExtra("classAnnouncements");
        }

        //setClassAnnouncementModels();
        ClassAnnouncementAdapter classAnnouncementAdapter = new ClassAnnouncementAdapter(this, classAnnouncementModels, this);
        recyclerView.setAdapter(classAnnouncementAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the search functionality
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                classAnnouncementAdapter.getFilter().filter(newText);
                return true;
            }
        });

        // Restore the original list when the search is closed
        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //classAnnouncementAdapter.restoreOriginalList();
                return false;
            }
        });
    }

    /**
     * This method initializes the list of class announcements.
     * It retrieves the announcement titles from the resources and creates a ClassAnnouncementModel for each title.
     */
    private void setClassAnnouncementModels(){
        String[] titles = getResources().getStringArray(R.array.Announcements);
        for (int i = 0; i < titles.length; i++) {
            classAnnouncementModels.add(new ClassAnnouncementModel(titles[i]));
        }
    }
    public void onClassAnnouncementItemClick(ClassAnnouncementModel clickedItem, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        View overlayView = inflater.inflate(R.layout.class_announcement_overlay_layout, null);

        EditText title = overlayView.findViewById(R.id.announcement_detail_name);
        EditText details = overlayView.findViewById(R.id.announcement_detail_content);
        EditText teacherName = overlayView.findViewById(R.id.announcement_detail_teacher);
        Button editButton = overlayView.findViewById(R.id.edit_button);
        Button saveButton = overlayView.findViewById(R.id.save_button);
        title.setEnabled(false);
        details.setEnabled(false);
        teacherName.setEnabled(false);

        // Set edit button and save button invisible for students/parents
        editButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.INVISIBLE);

        title.setText(clickedItem.getTitle());
        details.setText(clickedItem.getDetails());
        teacherName.setText(clickedItem.getTeacher().getName());

        builder.setView(overlayView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
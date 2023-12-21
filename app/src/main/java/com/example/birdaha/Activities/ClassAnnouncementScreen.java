package com.example.birdaha.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdaha.Adapters.ClassAnnouncementAdapter;
import com.example.birdaha.General.ClassAnnouncementModel;
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

        setClassAnnouncementModels();
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
                classAnnouncementAdapter.search(newText);
                return true;
            }
        });

        // Restore the original list when the search is closed
        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                classAnnouncementAdapter.restoreOriginalList();
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




    /**
 * This method is triggered when a class announcement item is clicked.
 * It creates and displays a dialog with the details of the clicked announcement.
 *
 * @param position The position of the clicked item in the list.
 * @param view The view of the clicked item.
 */
public void onClassAnnouncementItemClick(int position, View view) {
    // Get the clicked announcement from the list using its position
    ClassAnnouncementModel classAnnouncementModel = classAnnouncementModels.get(position);

    // Create a new AlertDialog builder
    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    // Get the LayoutInflater from the view's context
    LayoutInflater inflater = LayoutInflater.from(view.getContext());

    // Inflate the overlay layout for the dialog
    View overlayView = inflater.inflate(R.layout.overlay_class_announcement_layout, null);

    // Get the title and details TextViews from the overlay view
    TextView title = overlayView.findViewById(R.id.announcement_detail_name);
    TextView details = overlayView.findViewById(R.id.announcement_detail_teacher);

    // Set the text of the title and details TextViews to the title and details of the clicked announcement
    title.setText(classAnnouncementModel.getTitle());
    details.setText(classAnnouncementModel.getDetails());

    // Set the overlay view as the view for the dialog
    builder.setView(overlayView);

    // Create the dialog
    AlertDialog dialog = builder.create();

    // Show the dialog
    dialog.show();
}

}
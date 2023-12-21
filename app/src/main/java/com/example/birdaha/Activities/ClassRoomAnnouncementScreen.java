package com.example.birdaha.Activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
 * ClassRoomAnnouncementScreen is an activity that displays a list of class announcements.
 * It implements the ClassAnnouncementViewInterface to handle click events on the list items.
 */
public class ClassRoomAnnouncementScreen extends AppCompatActivity implements ClassAnnouncementViewInterface {

    // SearchView for filtering the list of class announcements
    SearchView search;

    // ArrayList to hold the class announcements
    ArrayList<ClassAnnouncementModel> classAnnouncementModels = new ArrayList<>();

    // Button for adding new class announcements
    Button addingAnnouncementButton;

    // Adapter for the RecyclerView that displays the class announcements
    private ClassAnnouncementAdapter classAnnouncementAdapter;

    /**
     * This method is called when the activity is starting.
     * It initializes the activity and sets up the RecyclerView, SearchView, and adding announcement button.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_announcement_screen);
        RecyclerView recyclerView = findViewById(R.id.caRecyclerView_classroom);

        addingAnnouncementButton = findViewById(R.id.adding_announcement_button);
        search = findViewById(R.id.searchView_Announcement);

        setClassAnnouncementModels();
        classAnnouncementAdapter = new ClassAnnouncementAdapter(this, classAnnouncementModels, this);
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

        // Set up the adding announcement button functionality
        addingAnnouncementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAnnouncementDialog();
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
        String[] titles = getResources().getStringArray(R.array.ClassroomAnnouncements);
        for (int i = 0; i < titles.length; i++) {
            classAnnouncementModels.add(new ClassAnnouncementModel(titles[i]));
        }
    }



    /**
 * This method is used to show a dialog for adding a new class announcement.
 * It sets up the dialog view, retrieves the user input from the form, creates a new ClassAnnouncementModel with the input,
 * adds the new announcement to the classAnnouncementModels list, and notifies the adapter that the data set has changed.
 */
private void showAddAnnouncementDialog() {
    // Create a new AlertDialog builder
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Ödev Ekleme");

    // Inflate the layout for the add announcement form
    LayoutInflater inflater = getLayoutInflater();
    View dialogView = inflater.inflate(R.layout.dialog_add_announcement, null);
    builder.setView(dialogView);

    // Get the form controls from the dialog view
    EditText assignmentTitleEditText = dialogView.findViewById(R.id.lectureNameEditText);
    EditText teacherName2 = dialogView.findViewById(R.id.add_announcement_teacher_name);
    EditText contentEditText = dialogView.findViewById(R.id.add_announcement_content);
    Button saveButton = dialogView.findViewById(R.id.saveButton);

    // Create the dialog
    final AlertDialog dialog = builder.create();

    // Set the onClickListener for the save button
    saveButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Get the title and description from the form
            String title = assignmentTitleEditText.getText().toString();
            String teacherName = teacherName2.getText().toString();
            String content = contentEditText.getText().toString();

            // Create a new ClassAnnouncementModel with the title and description
            ClassAnnouncementModel classAnnouncement = new ClassAnnouncementModel(title, content,teacherName);

            // Add the new announcement to the classAnnouncementModels list
            classAnnouncementModels.add(classAnnouncement);

            // Notify the adapter that the data set has changed
            classAnnouncementAdapter.notifyDataSetChanged();

            // Dismiss the dialog
            dialog.dismiss();
        }
    });

    // Show the dialog
    dialog.show();
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
    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);

    // Get the LayoutInflater from the view's context
    LayoutInflater inflater = LayoutInflater.from(view.getContext());

    // Inflate the overlay layout for the dialog
    View overlayView = inflater.inflate(R.layout.overlay_class_announcement_layout, null);

    // Get the title and details TextViews from the overlay view
    EditText title = overlayView.findViewById(R.id.announcement_detail_name);
    EditText details = overlayView.findViewById(R.id.announcement_detail_content);
    EditText teacherName = overlayView.findViewById(R.id.announcement_detail_teacher);
    Button editButton = overlayView.findViewById(R.id.edit_button);
    Button saveButton = overlayView.findViewById(R.id.save_button);


    // Set the text of the title and details TextViews to the title and details of the clicked announcement
    title.setText(classAnnouncementModel.getTitle());
    details.setText(classAnnouncementModel.getDetails());
    teacherName.setText(classAnnouncementModel.getTeacherName());

    title.setEnabled(false);
    details.setEnabled(false);
    teacherName.setEnabled(false);

    editButton.setOnClickListener(v -> {
        // Enable EditTexts to make them editable
        title.setEnabled(true);
        details.setEnabled(true);
        teacherName.setEnabled(true);
        title.requestFocus();
    });

    saveButton.setOnClickListener(v -> {
        // Save the edited text
        String updatedTitle = title.getText().toString();
        String updatedDetails = details.getText().toString();
        String updatedTeacherName = teacherName.getText().toString();

        classAnnouncementModel.setTitle(updatedTitle);
        classAnnouncementModel.setDetails(updatedDetails);
        classAnnouncementModel.setTeacherName(updatedTeacherName);

        // Notify the adapter that the item has changed
        classAnnouncementAdapter.notifyItemChanged(position);

        // Disable EditTexts after saving
        title.setEnabled(false);
        details.setEnabled(false);
        teacherName.setEnabled(false);
    });


    // Set the overlay view as the view for the dialog
    builder.setView(overlayView);

    // Create the dialog
    androidx.appcompat.app.AlertDialog dialog = builder.create();

    // Show the dialog
    dialog.show();
}
}
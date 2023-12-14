package com.example.birdaha.Activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdaha.Adapters.HomeworkAdapter;
import com.example.birdaha.General.HwModel;
import com.example.birdaha.R;
import com.example.birdaha.Utilities.ClassroomHomeworkViewInterface;

import java.util.ArrayList;


/**
 * This class represents the ClassroomHomeworkScreen activity, which displays a list of homework items
 * and provides options for filtering and adding new homework assignments.
 * It extends AppCompatActivity and implements ClassroomHomeworkViewInterface.
 */
public class ClassroomHomeworkScreen extends AppCompatActivity implements ClassroomHomeworkViewInterface {

    SearchView search; // Declare a SearchView object

    ArrayList<HwModel> hwModels = new ArrayList<>(); // Create an ArrayList to store homework models

    Button addingHwButton; // Declare a Button for adding homework

    private HomeworkAdapter homeworkAdapter; // Declare a HomeworkAdapter object

    /**
     * Called when the activity is created. Initializes the user interface, sets up event handlers,
     * and populates the list of homework assignments.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_homework_screen); // Set the content view to a specific layout

        RecyclerView recyclerView = findViewById(R.id.hwRecyclerView_classroom); // Find a RecyclerView in the layout

        addingHwButton = findViewById(R.id.adding_hw_btn); // Find the "Add Homework" button
        search = findViewById(R.id.searchView_homework); // Find the SearchView

        // Initialize the homework models by calling the setHwModules() method
        setHwModules();

        // Create a new HomeworkAdapter with this activity, the homework models, and this class as parameters
        homeworkAdapter = new HomeworkAdapter(this, hwModels, this);

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(homeworkAdapter);

        // Set the layout manager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Find a view for filtering homework items and set a click listener
        View baselineFilterView = findViewById(R.id.filterView);
        baselineFilterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOverlay(); // Call the showOverlay() method when clicked
            }
        });

        // Set a click listener for the "Add Homework" button
        addingHwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAssignmentDialog(); // Call the showAddAssignmentDialog() method when clicked
            }
        });

        // Set a listener for the SearchView to handle query text changes
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                homeworkAdapter.search(newText); // Call a method in the adapter to perform search
                return true;
            }
        });

        // Set a listener for closing the SearchView
        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                homeworkAdapter.restoreOriginalList(); // Restore the original list in the adapter
                return false;
            }
        });
    }

    /**
     * This method displays a dialog for adding a new homework assignment.
     */
    private void showAddAssignmentDialog() {
        // Create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ödev Ekleme"); // Set the dialog title

        // Inflate the layout for the add homework form
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_homework, null);
        builder.setView(dialogView);

        // Find views in the dialog layout
        EditText assignmentTitleEditText = dialogView.findViewById(R.id.announcementTitleEditText);
        EditText assignmentDescriptionEditText = dialogView.findViewById(R.id.assignmentDescriptionEditText);
        Button saveButton = dialogView.findViewById(R.id.saveButton);

        // Create the dialog
        final AlertDialog dialog = builder.create();

        // Set a click listener for the "Save" button in the dialog
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text entered in the title and description EditText fields
                String title = assignmentTitleEditText.getText().toString();
                String description = assignmentDescriptionEditText.getText().toString();

                // Create a new HwModel object with the entered title and description
                HwModel newHomework = new HwModel(title, description);

                // Add the new homework to the hwModels list
                hwModels.add(newHomework);

                // Notify the adapter that the data set has changed
                homeworkAdapter.notifyDataSetChanged();

                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Show the dialog
        dialog.show();
    }

    /**
     * This method initializes the homework modules by populating the hwModels list.
     */
    private void setHwModules() {
        // Retrieve arrays of titles and infos from resources
        String[] titles = getResources().getStringArray(R.array.ClassroomHomeworks);
        String[] infos =  getResources().getStringArray(R.array.ClassroomHomeworks);

        // Iterate through the arrays and create HwModel objects, then add them to the hwModels list
        for (int i = 0; i < titles.length; i++) {
            hwModels.add(new HwModel(titles[i], infos[i]));
        }
    }


    /**
     * Displays an overlay dialog for filtering options.
     */
    private void showOverlay() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View overlayView = inflater.inflate(R.layout.filter_overlay, null);
        builder.setView(overlayView);

        AlertDialog dialog = builder.create();

        // Set the dialog window attributes to make it a small overlay
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();

        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.TOP | Gravity.CENTER;
        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }

    /**
     * Handles the click event of a classroom homework item.
     *
     * @param position The position of the clicked item in the list.
     * @param view     The clicked View.
     */
    @Override
    public void onClassroomHomeworkItemClick(int position, View view) {
        HwModel currentHw = hwModels.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = LayoutInflater.from(view.getContext());

        View overlayView = inflater.inflate(R.layout.homework_overlay_layout, null);
        EditText detail = overlayView.findViewById(R.id.homework_detail_info);
        EditText title = overlayView.findViewById(R.id.homework_detail_name);
        Button editButton = overlayView.findViewById(R.id.editButton);
        Button saveButton = overlayView.findViewById(R.id.saveButton);

        detail.setText(currentHw.getInfo());
        title.setText(currentHw.getTitle());

        // Initially set EditTexts to non-editable
        detail.setEnabled(false);
        title.setEnabled(false);

        editButton.setOnClickListener(v -> {
            // Enable EditTexts to make them editable
            detail.setEnabled(true);
            title.setEnabled(true);
            detail.requestFocus();
        });

        saveButton.setOnClickListener(v -> {
            // Save the edited text
            String updatedInfo = detail.getText().toString();
            String updatedTitle = title.getText().toString();
            currentHw.setInfo(updatedInfo);
            currentHw.setTitle(updatedTitle);

            // Notify the adapter that the item has changed
            homeworkAdapter.notifyItemChanged(position);

            // Disable EditTexts after saving
            detail.setEnabled(false);
            title.setEnabled(false);
        });

        builder.setView(overlayView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }




}
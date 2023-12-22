package com.example.birdaha.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.birdaha.Adapters.HomeworkAdapter;
import com.example.birdaha.General.HwModel;
import com.example.birdaha.R;
import com.example.birdaha.Utilities.ClassroomHomeworkViewInterface;

import java.util.ArrayList;

/**
 * This activity displays a list of homework modules.
 */
public class HomeWorkScreen extends AppCompatActivity implements ClassroomHomeworkViewInterface {
    SearchView search;
    ArrayList<HwModel> hwModels;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work_screen);

        search = findViewById(R.id.searchView_homework);

        RecyclerView recyclerView = findViewById(R.id.hwRecyclerView);

        Intent intent = getIntent();
        if(intent != null){
            hwModels = (ArrayList<HwModel>) intent.getSerializableExtra("homeworks");
        }


        HomeworkAdapter homeworkAdapter = new HomeworkAdapter(this, hwModels,this);
        recyclerView.setAdapter(homeworkAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        View baselineFilterView = findViewById(R.id.filterView);
        baselineFilterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOverlay();
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                homeworkAdapter.getFilter().filter(newText);
                return true;
            }
        });

        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //homeworkAdapter.restoreOriginalList();
                return false;
            }
        });

    }

    // This method is called when the user clicks on the filter icon
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

        // Find the checkboxes in the overlay layout
        CheckBox checkBox1 = overlayView.findViewById(R.id.checkBox);
        CheckBox checkBox2 = overlayView.findViewById(R.id.checkBox2);

        // Add any additional customization or logic to the checkboxes here

        dialog.show();
    }

    @Override
    public void onClassroomHomeworkItemClick(HwModel clickedItem, View view) {

        // Create an AlertDialog.Builder object with the context of the itemView
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        // Create a LayoutInflater object from the itemView's context
        LayoutInflater inflater = LayoutInflater.from(view.getContext());

        // Inflate the overlay_layout.xml file into a View object

        View overlayView = inflater.inflate(R.layout.overlay_homework_layout, null);
        EditText courseName = overlayView.findViewById(R.id.homework_detail_course_name);
        EditText title = overlayView.findViewById(R.id.homework_detail_title);
        EditText dueDate = overlayView.findViewById(R.id.homework_detail_duedate);
        EditText content = overlayView.findViewById(R.id.homework_detail_content);
        Button editButton = overlayView.findViewById(R.id.editButton);
        Button saveButton = overlayView.findViewById(R.id.saveButton);
        ImageView imageView = overlayView.findViewById(R.id.homework_detail_image);

        courseName.setEnabled(false);
        title.setEnabled(false);
        dueDate.setEnabled(false);
        content.setEnabled(false);

        courseName.setText(clickedItem.getCourse_name());
        title.setText(clickedItem.getTitle());
        dueDate.setText(clickedItem.getDue_date());
        content.setText(clickedItem.getInfo());
        byte[] imageBytes = Base64.decode(clickedItem.getImage(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes,0, imageBytes.length);
        Glide.with(HomeWorkScreen.this)
                .load(decodedImage)
                .into(imageView);

        editButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        // Set the inflated view as the custom view for the AlertDialog
        builder.setView(overlayView);

        // Create an AlertDialog object from the builder
        AlertDialog dialog = builder.create();

        // Show the AlertDialog
        dialog.show();
    }
}
package com.example.birdaha.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdaha.Adapters.ClassAnnouncementAdapter;
import com.example.birdaha.Classrooms.Classroom;
import com.example.birdaha.General.ClassAnnouncementModel;
import com.example.birdaha.General.UpdateRespond;
import com.example.birdaha.R;
import com.example.birdaha.Users.Teacher;
import com.example.birdaha.Utilities.ClassAnnouncementViewInterface;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class ClassRoomAnnouncementScreen extends AppCompatActivity implements ClassAnnouncementViewInterface {

    interface MakeAnnouncement{
        @POST("/api/v1/announcement/add")
        Call<ClassAnnouncementModel> makeAnnouncement(@Body ClassAnnouncementModel classAnnouncementModel);

        @POST("api/v1/announcements/update")
        Call<UpdateRespond> updateAnnouncement(@Body ClassAnnouncementModel classAnnouncementModel);
    }

    SearchView search;

    ArrayList<ClassAnnouncementModel> classAnnouncementModels;

    Button addingAnnouncementButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_announcement_screen);
        RecyclerView recyclerView = findViewById(R.id.caRecyclerView_classroom);

        search = findViewById(R.id.searchView_students);
        addingAnnouncementButton = findViewById(R.id.adding_announcement_button);

        Intent intent = getIntent();
        if(intent != null){
            classAnnouncementModels = (ArrayList<ClassAnnouncementModel>) intent.getSerializableExtra("announcements");
        }

        //setClassAnnouncementModels();
        ClassAnnouncementAdapter classAnnouncementAdapter = new ClassAnnouncementAdapter(this, classAnnouncementModels, this);

        recyclerView.setAdapter(classAnnouncementAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        addingAnnouncementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAnnouncementDialog();
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

    private void showAddAnnouncementDialog() {
        // Create a new AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Duyuru Yapma");

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

                Intent intent = getIntent();
                if(intent != null){
                    Classroom currentClassroom = (Classroom) intent.getSerializableExtra("classroom");
                    Teacher teacher = (Teacher) intent.getSerializableExtra("teacher");
                    ClassAnnouncementModel classAnnouncement = new ClassAnnouncementModel(title, content, currentClassroom.getClassroom_id(),teacher.getTeacher_id());
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://sinifdoktoruadmin.online/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    MakeAnnouncement postAnnouncement = retrofit.create(MakeAnnouncement.class);

                    postAnnouncement.makeAnnouncement(classAnnouncement).enqueue(new Callback<ClassAnnouncementModel>() {
                        @Override
                        public void onResponse(Call<ClassAnnouncementModel> call, Response<ClassAnnouncementModel> response) {
                            if(response.isSuccessful() && response.body() != null){
                                Log.d("Response",new Gson().toJson(response.body()));
                            }
                        }

                        @Override
                        public void onFailure(Call<ClassAnnouncementModel> call, Throwable t) {
                            Log.d("Error",t.getMessage());
                        }
                    });
                }

                dialog.dismiss();
            }
        });

        // Show the dialog
        dialog.show();
    }

    public void onClassAnnouncementItemClick(ClassAnnouncementModel clickedItem, View view) {
        //ClassAnnouncementModel classAnnouncementModel = classAnnouncementModels.get(position);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(view.getContext());

        View overlayView = inflater.inflate(R.layout.overlay_class_announcement_layout, null);


        EditText title = overlayView.findViewById(R.id.announcement_detail_name);
        EditText details = overlayView.findViewById(R.id.announcement_detail_content);
        EditText teacherName = overlayView.findViewById(R.id.announcement_detail_teacher);
        Button editButton = overlayView.findViewById(R.id.edit_button);
        Button saveButton = overlayView.findViewById(R.id.save_button);

        Intent intent = getIntent();
        if(intent != null){
            Teacher currentTeacher = (Teacher) intent.getSerializableExtra("teacher");
            if(currentTeacher.getTeacher_id() != clickedItem.getTeacher_id()){
                editButton.setEnabled(false);
                saveButton.setEnabled(false);
            }
        }

        title.setText(clickedItem.getTitle());
        details.setText(clickedItem.getDetails());
        teacherName.setText(clickedItem.getTeacher().getName());

        title.setEnabled(false);
        details.setEnabled(false);

        editButton.setOnClickListener(v -> {
            // Enable EditTexts to make them editable
            title.setEnabled(true);
            details.setEnabled(true);
            title.requestFocus();
        });

        saveButton.setOnClickListener(v -> {
            // Save the edited text
            String updatedTitle = title.getText().toString();
            String updatedDetails = details.getText().toString();

            clickedItem.setTitle(updatedTitle);
            clickedItem.setDetails(updatedDetails);

            System.out.println(clickedItem.getAnnouncement_id());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://sinifdoktoruadmin.online/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MakeAnnouncement updateAnnouncement = retrofit.create(MakeAnnouncement.class);
            updateAnnouncement.updateAnnouncement(clickedItem).enqueue(new Callback<UpdateRespond>() {
                @Override
                public void onResponse(Call<UpdateRespond> call, Response<UpdateRespond> response) {
                    if(response.isSuccessful() && response.body() != null){
                        Log.d("ResponseUpdate",new Gson().toJson(response.body()));
                    }
                    else{
                        Log.d("ResponseUpdate",new Gson().toJson(response.body()));
                        Log.d("ResponseUpdateCode",String.valueOf(response.code()));
                    }
                }

                @Override
                public void onFailure(Call<UpdateRespond> call, Throwable t) {
                    Log.d("Error",t.getMessage());
                }
            });

            // Disable EditTexts after saving
            title.setEnabled(false);
            details.setEnabled(false);
        });

        builder.setView(overlayView);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }
}
package com.example.birdaha.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdaha.Adapters.ClassAnnouncementAdapter;
import com.example.birdaha.Classrooms.Classroom;
import com.example.birdaha.General.AnnouncementsTeacher;
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
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class ClassRoomAnnouncementScreen extends AppCompatActivity implements ClassAnnouncementViewInterface {

    interface MakeAnnouncement{

        @GET("api/v1/teacher/announcements/{classroomId}")
        Call<AnnouncementsTeacher> getAnnouncements(@Path("classroomId") int classroomId);

        @POST("/api/v1/announcement/add")
        Call<ClassAnnouncementModel> makeAnnouncement(@Body ClassAnnouncementModel classAnnouncementModel);

        @POST("api/v1/announcements/update")
        Call<UpdateRespond> updateAnnouncement(@Body ClassAnnouncementModel classAnnouncementModel);
    }

    SearchView search;

    ArrayList<ClassAnnouncementModel> classAnnouncementModels;

    Button addingAnnouncementButton;

    ClassAnnouncementAdapter classAnnouncementAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_announcement_screen);
        RecyclerView recyclerView = findViewById(R.id.caRecyclerView_classroom);

        search = findViewById(R.id.searchView_students);
        addingAnnouncementButton = findViewById(R.id.adding_announcement_button);

        Classroom classroom = null;

        Intent intent = getIntent();
        if(intent != null){
            classroom = (Classroom) intent.getSerializableExtra("classroom");
            classAnnouncementModels = (ArrayList<ClassAnnouncementModel>) intent.getSerializableExtra("announcements");
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://sinifdoktoruadmin.online/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if(classroom != null){
            MakeAnnouncement getAnnouncement = retrofit.create(MakeAnnouncement.class);
            getAnnouncement.getAnnouncements(classroom.getClassroom_id()).enqueue(new Callback<AnnouncementsTeacher>() {
                @Override
                public void onResponse(Call<AnnouncementsTeacher> call, Response<AnnouncementsTeacher> response) {
                    if(response.isSuccessful() && response.body() != null){
                        AnnouncementsTeacher models = response.body();
                        classAnnouncementModels = (ArrayList<ClassAnnouncementModel>) models.getClassroomAnnouncements();

                        classAnnouncementAdapter = new ClassAnnouncementAdapter(ClassRoomAnnouncementScreen.this, classAnnouncementModels, ClassRoomAnnouncementScreen.this);
                        recyclerView.setAdapter(classAnnouncementAdapter);
                        Toast.makeText(ClassRoomAnnouncementScreen.this, "Duyurlar Listeleniyor", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(ClassRoomAnnouncementScreen.this, "Response Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AnnouncementsTeacher> call, Throwable t) {
                    Toast.makeText(ClassRoomAnnouncementScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


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
                showAnnouncementDialog();
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
        EditText teacherName2 = dialogView.findViewById(R.id.announcement_teacher_name);
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
                    classAnnouncement.setTeacher(teacher);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://sinifdoktoruadmin.online/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    MakeAnnouncement postAnnouncement = retrofit.create(MakeAnnouncement.class);

                    postAnnouncement.makeAnnouncement(classAnnouncement).enqueue(new Callback<ClassAnnouncementModel>() {
                        @Override
                        public void onResponse(Call<ClassAnnouncementModel> call, Response<ClassAnnouncementModel> response) {
                            if(response.isSuccessful() && response.body() != null){
                                classAnnouncementModels.add(classAnnouncement);
                                classAnnouncementAdapter.notifyDataSetChanged();
                                Toast.makeText(ClassRoomAnnouncementScreen.this, "Duyuru başarıyla yapıldı", Toast.LENGTH_SHORT).show();
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
    public void showAnnouncementDialog()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AnnouncementDialogFragment newFragment = new AnnouncementDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable("teacher", getIntent().getSerializableExtra("teacher"));
        args.putSerializable("classroom", getIntent().getSerializableExtra("classroom"));
        newFragment.setArguments(args);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    public void onClassAnnouncementItemClick(ClassAnnouncementModel clickedItem, View view) {
        //ClassAnnouncementModel classAnnouncementModel = classAnnouncementModels.get(position);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(view.getContext());

        View overlayView = inflater.inflate(R.layout.dialog_ann_detail, null);


        EditText title = overlayView.findViewById(R.id.announcement_detail_name);
        EditText details = overlayView.findViewById(R.id.announcement_detail_content);
        EditText teacherName = overlayView.findViewById(R.id.announcement_detail_teacher);


        title.setText(clickedItem.getTitle());
        details.setText(clickedItem.getDetails());
        teacherName.setText(clickedItem.getTeacher().getName());

        title.setEnabled(false);
        details.setEnabled(false);


        builder.setView(overlayView);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }
}
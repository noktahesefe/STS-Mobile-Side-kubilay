package com.example.birdaha.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdaha.Adapters.ClassAnnouncementAdapter;
import com.example.birdaha.Classrooms.Classroom;
import com.example.birdaha.General.AnnouncementsStudent;
import com.example.birdaha.General.ClassAnnouncementModel;
import com.example.birdaha.R;
import com.example.birdaha.Utilities.ClassAnnouncementViewInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class ClassAnnouncementScreen extends AppCompatActivity implements ClassAnnouncementViewInterface {

    interface GetAnnouncement{
        @GET("api/v1/announcements/{classroomId}")
        Call<AnnouncementsStudent> getAnnouncements(@Path("classroomId") int classroomId);
    }
    SearchView search;
    List<ClassAnnouncementModel> classAnnouncementModels;
    ClassAnnouncementAdapter classAnnouncementAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_announcement_screen);
        RecyclerView recyclerView = findViewById(R.id.caRecyclerView);

        search = findViewById(R.id.searchView_Announcement);

        Classroom classroom = null;

        Intent intent = getIntent();
        if(intent != null){
            classroom = (Classroom) intent.getSerializableExtra("classroom");
            //classAnnouncementModels = (ArrayList<ClassAnnouncementModel>) intent.getSerializableExtra("classAnnouncements");
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://sinifdoktoruadmin.online/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetAnnouncement getAnnouncement = retrofit.create(GetAnnouncement.class);
        getAnnouncement.getAnnouncements(classroom.getClassroom_id()).enqueue(new Callback<AnnouncementsStudent>() {
            @Override
            public void onResponse(Call<AnnouncementsStudent> call, Response<AnnouncementsStudent> response) {
                if(response.isSuccessful() && response.body() != null){
                    AnnouncementsStudent models = response.body();
                    classAnnouncementModels = models.getClassAnnouncements();
                    classAnnouncementAdapter = new ClassAnnouncementAdapter(ClassAnnouncementScreen.this, (ArrayList<ClassAnnouncementModel>) classAnnouncementModels, ClassAnnouncementScreen.this);
                    recyclerView.setAdapter(classAnnouncementAdapter);
                    Toast.makeText(ClassAnnouncementScreen.this, "Duyurular Listeleniyor", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ClassAnnouncementScreen.this, "Response Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AnnouncementsStudent> call, Throwable t) {
                Toast.makeText(ClassAnnouncementScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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

        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //classAnnouncementAdapter.restoreOriginalList();
                return false;
            }
        });
    }

    public void onClassAnnouncementItemClick(ClassAnnouncementModel clickedItem, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        View overlayView = inflater.inflate(R.layout.dialog_ann_detail, null);

        EditText title = overlayView.findViewById(R.id.announcement_detail_name);
        EditText details = overlayView.findViewById(R.id.announcement_detail_content);
        EditText teacherName = overlayView.findViewById(R.id.announcement_detail_teacher);

        title.setEnabled(false);
        details.setEnabled(false);
        teacherName.setEnabled(false);


        title.setText(clickedItem.getTitle());
        details.setText(clickedItem.getDetails());
        teacherName.setText(clickedItem.getTeacher().getName());

        builder.setView(overlayView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
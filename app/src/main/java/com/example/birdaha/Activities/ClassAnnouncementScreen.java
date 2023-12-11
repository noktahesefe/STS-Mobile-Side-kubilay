package com.example.birdaha.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdaha.Adapters.ClassAnnouncementAdapter;
import com.example.birdaha.General.ClassAnnouncementModel;
import com.example.birdaha.R;

import java.util.ArrayList;

public class ClassAnnouncementScreen extends AppCompatActivity {
    SearchView search;
    ArrayList<ClassAnnouncementModel> classAnnouncementModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_announcement_screen);
        RecyclerView recyclerView = findViewById(R.id.caRecyclerView);

        search = findViewById(R.id.searchView2);

        setClassAnnouncementModels();
        ClassAnnouncementAdapter classAnnouncementAdapter = new ClassAnnouncementAdapter(this, classAnnouncementModels);
        recyclerView.setAdapter(classAnnouncementAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                classAnnouncementAdapter.restoreOriginalList();
                return false;
            }
        });


    }
    private void setClassAnnouncementModels(){

        String[] titles = getResources().getStringArray(R.array.Announcements);
        for (int i = 0; i < titles.length; i++) {
            classAnnouncementModels.add(new ClassAnnouncementModel(titles[i]));
        }

    }

}
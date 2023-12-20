package com.example.birdaha.Activities;

import android.content.Intent;
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
import com.example.birdaha.General.GeneralAnnouncement;
import com.example.birdaha.R;
import com.example.birdaha.Utilities.ClassAnnouncementViewInterface;

import java.util.ArrayList;

public class ClassAnnouncementScreen extends AppCompatActivity implements ClassAnnouncementViewInterface {
    SearchView search;
    ArrayList<ClassAnnouncementModel> classAnnouncementModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_announcement_screen);
        RecyclerView recyclerView = findViewById(R.id.caRecyclerView);

        search = findViewById(R.id.searchView2);

        Intent intent = getIntent();
        if(intent != null){
            classAnnouncementModels = (ArrayList<ClassAnnouncementModel>) intent.getSerializableExtra("classAnnouncements");
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

        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //classAnnouncementAdapter.restoreOriginalList();
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
    public void onClassAnnouncementItemClick(ClassAnnouncementModel clickedItem, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        View overlayView = inflater.inflate(R.layout.class_announcement_overlay_layout, null);
        TextView title = overlayView.findViewById(R.id.announcement_detail_name);
        TextView details = overlayView.findViewById(R.id.announcement_detail_info);
        title.setText(clickedItem.getTitle());
        details.setText(clickedItem.getDetails());
        builder.setView(overlayView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
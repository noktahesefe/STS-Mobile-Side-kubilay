package com.example.birdaha.Activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdaha.Adapters.ClassAnnouncementAdapter;
import com.example.birdaha.General.ClassAnnouncementModel;
import com.example.birdaha.R;
import com.example.birdaha.Utilities.ClassAnnouncementViewInterface;
import com.example.birdaha.Utilities.ClassroomHomeworkViewInterface;

import java.util.ArrayList;

public class ClassRoomAnnouncementScreen extends AppCompatActivity implements ClassAnnouncementViewInterface {

    SearchView search;

    ArrayList<ClassAnnouncementModel> classAnnouncementModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_announcement_screen);
        RecyclerView recyclerView = findViewById(R.id.caRecyclerView_classroom);

        search = findViewById(R.id.searchView2);

        setClassAnnouncementModels();
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

        String[] titles = getResources().getStringArray(R.array.ClassroomAnnouncements);
        for (int i = 0; i < titles.length; i++) {
            classAnnouncementModels.add(new ClassAnnouncementModel(titles[i]));
        }

    }
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

    public void onClassAnnouncementItemClick(int position, View view) {
        ClassAnnouncementModel classAnnouncementModel = classAnnouncementModels.get(position);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        View overlayView = inflater.inflate(R.layout.class_announcement_overlay_layout, null);
        TextView title = overlayView.findViewById(R.id.announcement_detail_name);
        TextView details = overlayView.findViewById(R.id.announcement_detail_info);
        title.setText(classAnnouncementModel.getTitle());
        details.setText(classAnnouncementModel.getDetails());
        builder.setView(overlayView);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }
}
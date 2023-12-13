package com.example.birdaha.Activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdaha.Adapters.HomeworkAdapter;
import com.example.birdaha.General.HwModel;
import com.example.birdaha.R;

import java.util.ArrayList;

public class ClassroomHomeworkScreen extends AppCompatActivity {
    SearchView search;

    ArrayList<HwModel> hwModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_homework_screen);
        RecyclerView recyclerView = findViewById(R.id.hwRecyclerView_classroom);
        search = findViewById(R.id.searchView);

        setHwModules();
        HomeworkAdapter homeworkAdapter = new HomeworkAdapter(this, hwModels);
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
                homeworkAdapter.search(newText);
                return true;
            }
        });

        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                homeworkAdapter.restoreOriginalList();
                return false;
            }
        });
    }

    private void setHwModules(){

        String[] titles = getResources().getStringArray(R.array.ClassroomHomeworks);
        for (int i = 0; i < titles.length; i++) {
            hwModels.add(new HwModel(titles[i]));
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
}
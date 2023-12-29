package com.example.birdaha.Activities;

import android.app.AlertDialog;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.CompoundButton;
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
import com.example.birdaha.General.StudentModel;
import com.example.birdaha.R;
import com.example.birdaha.Utilities.ClassroomHomeworkViewInterface;

import java.net.ConnectException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * This activity displays a list of homework modules.
 */
public class HomeWorkScreen extends AppCompatActivity implements ClassroomHomeworkViewInterface {
    SearchView search;
    ArrayList<HwModel> hwModels;

    RecyclerView recyclerView;
    HomeworkAdapter homeworkAdapter;
    private Context context;
    private ClassroomHomeworkViewInterface homeworkViewInterface;

    private ArrayList<HwModel> expiredHws;
    private ArrayList<HwModel> ongoingHws;

    private AlertDialog filterDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work_screen);

        search = findViewById(R.id.searchView_homework);

        recyclerView = findViewById(R.id.hwRecyclerView);
        expiredHws = new ArrayList<>();
        ongoingHws = new ArrayList<>();

        context = this;
        homeworkViewInterface = this;

        Intent intent = getIntent();
        if (intent != null) {
            hwModels = (ArrayList<HwModel>) intent.getSerializableExtra("homeworks");
        }

        for(HwModel o : hwModels)
        {
            LocalDateTime today = LocalDateTime.now();
            LocalDateTime localDate = LocalDate.parse(o.getDue_date()).atStartOfDay();

            if(localDate.isAfter(today))
                ongoingHws.add(o);
            else
                expiredHws.add(o);
        }

        sortListByDate(hwModels);

        HomeworkAdapter homeworkAdapter = new HomeworkAdapter(context, hwModels, homeworkViewInterface);
        recyclerView.setAdapter(homeworkAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

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
    }

    // This method is called when the user clicks on the filter icon
    private void showOverlay() {

        if(filterDialog == null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = LayoutInflater.from(this);
            View overlayView = inflater.inflate(R.layout.filter_overlay, null);
            builder.setView(overlayView);

            filterDialog = builder.create();

            // Set the dialog window attributes to make it a small overlay
            WindowManager.LayoutParams layoutParams = filterDialog.getWindow().getAttributes();

            layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.gravity = Gravity.TOP | Gravity.CENTER;
            filterDialog.getWindow().setAttributes(layoutParams);

            // Find the checkboxes in the overlay layout

            CheckBox checkBox1 = overlayView.findViewById(R.id.checkBox);
            /*else
                overlayView.findViewById(R.id.checkBox).setEnabled(checkBox1.isEnabled());*/


            CheckBox checkBox2 = overlayView.findViewById(R.id.checkBox2);
            /*else
                overlayView.findViewById(R.id.checkBox2).setEnabled(checkBox2.isEnabled());*/

            checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    System.out.println("cb1 = " + isChecked);
                    hwModels.clear();

                    if(checkBox2.isChecked())
                        hwModels.addAll(expiredHws);

                    if(isChecked)
                        hwModels.addAll(ongoingHws);


                    if(!isChecked && !checkBox2.isChecked())
                    {
                        hwModels.addAll(expiredHws);
                        hwModels.addAll(ongoingHws);
                    }


                    sortListByDate(hwModels);
                    HomeworkAdapter homeworkAdapter = new HomeworkAdapter(context, hwModels, homeworkViewInterface);
                    recyclerView.setAdapter(homeworkAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            });

            checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    System.out.println("cb2 = " + isChecked);
                    hwModels.clear();

                    if(checkBox1.isChecked())
                        hwModels.addAll(ongoingHws);

                    if(isChecked)
                        hwModels.addAll(expiredHws);


                    if(!isChecked && !checkBox1.isChecked())
                    {
                        hwModels.addAll(expiredHws);
                        hwModels.addAll(ongoingHws);
                    }

                    sortListByDate(hwModels);
                    HomeworkAdapter homeworkAdapter = new HomeworkAdapter(context, hwModels, homeworkViewInterface);
                    recyclerView.setAdapter(homeworkAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            });

        }

        // Add any additional customization or logic to the checkboxes here

        filterDialog.show();
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

/*        byte[] imageBytes = Base64.decode(clickedItem.getImage(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes,0, imageBytes.length);
        Glide.with(HomeWorkScreen.this)
                .load(decodedImage)
                .into(imageView);

        // If the clickedItem has no image, do not open the full screen view
        if(!clickedItem.getImage().equals("")){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(HomeWorkScreen.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    dialog.setContentView(R.layout.dialog_full_screen_image);

                    ImageView fullScreenImage = dialog.findViewById(R.id.fullScreenImageView);
                    fullScreenImage.setImageBitmap(decodedImage);
                    fullScreenImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }*/

        editButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        // Set the inflated view as the custom view for the AlertDialog
        builder.setView(overlayView);

        // Create an AlertDialog object from the builder
        AlertDialog dialog = builder.create();

        // Show the AlertDialog
        dialog.show();
    }

    private void sortListByDate(ArrayList<HwModel> list){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime today = LocalDateTime.now();

        // Comparator oluşturulması
        Comparator<HwModel> dateComparator = (date1, date2) -> {
            LocalDateTime localDate1 = LocalDate.parse(date1.getDue_date(), formatter).atStartOfDay();
            LocalDateTime localDate2 = LocalDate.parse(date2.getDue_date(), formatter).atStartOfDay();

            if (localDate1.isAfter(today) && localDate2.isAfter(today)) {
                return localDate1.compareTo(localDate2);
            } else if (localDate1.isBefore(today)) {
                return 1;
            } else if (localDate2.isBefore(today)) {
                return -1;
            } else {
                return localDate1.compareTo(localDate2);
            }
        };

        // Listeyi tarihe göre sıralama
        Collections.sort(list, dateComparator);
    }
}
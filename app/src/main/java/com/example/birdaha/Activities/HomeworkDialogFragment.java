package com.example.birdaha.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.birdaha.Classrooms.Classroom;
import com.example.birdaha.General.HwModel;
import com.example.birdaha.General.UpdateRespond;
import com.example.birdaha.R;
import com.example.birdaha.Users.Teacher;
import com.google.gson.Gson;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeworkDialogFragment extends DialogFragment {


    private EditText hw_title, hw_due_date, hw_content, hw_course_name;

    private Teacher teacher;
    private Classroom classroom;
    private String image;
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.full_screen_hw_adding_dialog, container, false);

        hw_title = view.findViewById(R.id.hw_title);
        hw_content = view.findViewById(R.id.hw_content);
        hw_due_date = view.findViewById(R.id.dateEditText);

        // Set up the date picker dialog
        hw_due_date.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(), // Use getActivity() as the context
                    (view1, year1, monthOfYear, dayOfMonth) -> {
                        // Format the date in your preferred format (yyyy-MM-dd)
                        String date = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        hw_due_date.setText(date);
                    }, year, month, day);
            datePickerDialog.show();
        });

        // Initialize the close button
        ImageButton closeButton = view.findViewById(R.id.fullscreen_dialog_close);
        closeButton.setOnClickListener(v -> dismiss());

        // Initialize the action button
        TextView actionButton = view.findViewById(R.id.fullscreen_dialog_action);
        actionButton.setOnClickListener(v -> {

            String title = hw_title.getText().toString();
            String content = hw_content.getText().toString();
            String due_date = hw_due_date.getText().toString();
            HwModel hwModel = new HwModel(classroom.getClassroom_id(),teacher.getTeacher_id(),teacher.getCourse().getName(),due_date,title,content,image);
            hwModel.setGetImage(image);

            // Retrofit call
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://sinifdoktoruadmin.online/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ClassroomHomeworkScreen.AddHomework addHomework = retrofit.create(ClassroomHomeworkScreen.AddHomework.class);
            addHomework.addHomework(hwModel).enqueue(new Callback<UpdateRespond>() {
                @Override
                public void onResponse(Call<UpdateRespond> call, Response<UpdateRespond> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Handle successful response
                        Toast.makeText(getActivity(), "Ödev başarıyla eklendi", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle unsuccessful response
                        Log.d("ResponseError", new Gson().toJson(response.body()));
                    }
                }

                @Override
                public void onFailure(Call<UpdateRespond> call, Throwable t) {
                    // Handle failure
                    Log.d("Error", t.getMessage());
                }
            });


            dismiss(); // Dismiss the dialog after action
        });

        TextView dialog_title = view.findViewById(R.id.hw_title);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            teacher = (Teacher) getArguments().getSerializable("teacher");
            classroom = (Classroom) getArguments().getSerializable("classroom");
        }
    }





    // The system calls this only when creating the layout in a dialog.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        // Handle the dialog dismiss event
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        // Handle the dialog cancel event
    }



}
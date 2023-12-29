package com.example.birdaha.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.birdaha.Classrooms.Classroom;
import com.example.birdaha.General.ClassAnnouncementModel;
import com.example.birdaha.General.HomeworksAndAnnouncements;
import com.example.birdaha.General.HomeworksAndAnnouncementsTeacher;
import com.example.birdaha.General.HwModel;
import com.example.birdaha.General.StudentModel;
import com.example.birdaha.R;
import com.example.birdaha.Users.Teacher;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class ClassroomScreen extends AppCompatActivity {

    interface GetHomeworkAndAnnouncement{
        @GET("/api/v1/teacher/homeworks/announcements/{classroomId}")
        Call<HomeworksAndAnnouncementsTeacher> getHomeworksAndAnnouncements(@Path("classroomId") int classroomId);
    }

    Button classroomAnnouncementsButton;
    Button classroomHomeworksButton;

    TextView classroomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_screen);

        Intent intent2 = getIntent();
        if(intent2 != null){
            Classroom classroom = (Classroom) intent2.getSerializableExtra("classroom");
            classroomName = findViewById(R.id.classroom_info);
            classroomName.setText(classroom.getName());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://sinifdoktoruadmin.online/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            GetHomeworkAndAnnouncement requestHwAndAnnouncement = retrofit.create(GetHomeworkAndAnnouncement.class);
            requestHwAndAnnouncement.getHomeworksAndAnnouncements(classroom.getClassroom_id()).enqueue(new Callback<HomeworksAndAnnouncementsTeacher>() {
                @Override
                public void onResponse(Call<HomeworksAndAnnouncementsTeacher> call, Response<HomeworksAndAnnouncementsTeacher> response) {
                    if(response.isSuccessful() && response.body() != null){
                        HomeworksAndAnnouncementsTeacher models = response.body();
                        List<ClassAnnouncementModel> announcementModelList = models.getClassAnnouncementModels();
                        List<HwModel> homeworkModelList = models.getHomeworks();
                        List<StudentModel> students = models.getStudents();

                        Log.d("Respond",new Gson().toJson(response.body()));

                        classroomAnnouncementsButton = findViewById(R.id.classroom_announcements);

                        classroomAnnouncementsButton.setOnClickListener(v -> {
                            Intent getIntent = getIntent();
                            if(getIntent != null){
                                Teacher teacher = (Teacher) getIntent.getSerializableExtra("teacher");
                                Intent intent = new Intent(ClassroomScreen.this, ClassRoomAnnouncementScreen.class);
                                intent.putExtra("teacher",teacher);
                                intent.putExtra("classroom",classroom);
                                intent.putExtra("announcements",(Serializable) announcementModelList);
                                startActivity(intent);
                            }


                        });

                        classroomHomeworksButton = findViewById(R.id.classroom_homeworks);

                        classroomHomeworksButton.setOnClickListener(v -> {
                            Intent getIntent = getIntent();
                            if(getIntent != null){
                                Teacher teacher = (Teacher) getIntent.getSerializableExtra("teacher");
                                Intent intent = new Intent(ClassroomScreen.this, ClassroomHomeworkScreen.class);
                                intent.putExtra("teacher",teacher);
                                intent.putExtra("classroom",classroom);
                                intent.putExtra("homeworks",(Serializable) homeworkModelList);
                                intent.putExtra("students", (Serializable) students);
                                startActivity(intent);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<HomeworksAndAnnouncementsTeacher> call, Throwable t) {
                    Log.d("Error",t.getMessage());
                }
            });


        }
    }
}
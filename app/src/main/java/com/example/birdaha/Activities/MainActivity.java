package com.example.birdaha.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.birdaha.Classrooms.Classroom;
import com.example.birdaha.Classrooms.Lecture;
import com.example.birdaha.R;
import com.example.birdaha.Users.LoginRequest;
import com.example.birdaha.Users.Parent;
import com.example.birdaha.Users.Student;
import com.example.birdaha.Users.Teacher;
import com.example.birdaha.Users.User;
import com.example.birdaha.Users.UserRespond;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * This class represents the main login screen of the application.
 * It allows users to enter their username and password and login as either a Student, Teacher, or Parent.
 */
public class MainActivity extends AppCompatActivity {

    interface RequestUser {
        /*@GET("/api/v1/login")
        Call<User> getUser();*/

        @POST("api/v1/login")
        Call<UserRespond> postUser(@Body LoginRequest loginRequest);

    }

    EditText username;
    EditText password;
    CheckBox checkBox;
    MaterialButton loginButton;

    /**
     * Called when the activity is created. Initializes the user interface and sets up event handlers
     * for the login button.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the content view to the main login layout

        // Find and initialize UI elements
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        checkBox = (CheckBox) findViewById(R.id.saveLoginCheckBox);
        loginButton = (MaterialButton) findViewById(R.id.loginButton);

        // Set a click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://sinifdoktoruadmin.online/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RequestUser requestUser = retrofit.create(RequestUser.class);
                LoginRequest loginRequest = new LoginRequest(username.getText().toString(), password.getText().toString());
                System.out.println(new Gson().toJson(loginRequest).toString());

                requestUser.postUser(loginRequest).enqueue(new Callback<UserRespond>() {
                    @Override
                    public void onResponse(Call<UserRespond> call, Response<UserRespond> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            UserRespond respond = response.body();
                            User user = respond.getUser();
                            Log.d("Respond", new Gson().toJson(respond.getUser()).toString());
                            switch (response.code()) {
                                case 201:
                                    List<Classroom> classrooms = respond.getUser().getClassrooms();
                                    Lecture course = respond.getUser().getCourse();
                                    Log.d("Course", course.getName());
                                    Log.d("Course ID", String.valueOf(course.getCourse_id()));
                                    System.out.println(user.getTeacher_image());
                                    Teacher teacher = new Teacher(user.getName(), user.getTeacher_id(), course, classrooms);
                                    Intent intent = new Intent(MainActivity.this, TeacherMainActivity.class);
                                    intent.putExtra("user", teacher);
                                    Toast.makeText(MainActivity.this, "Teacher logged in", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    break;
                                case 202:
                                    Classroom classroom = respond.getUser().getClassroom();
                                    Log.d("Classroom", classroom.getName());
                                    Student student = new Student(user.getName(), user.getStudent_id(), classroom, user.getSchool_no());
                                    Intent intent2 = new Intent(MainActivity.this, StudentMainActivity.class);
                                    intent2.putExtra("user", student);
                                    Toast.makeText(MainActivity.this, "Student logged in", Toast.LENGTH_SHORT).show();
                                    startActivity(intent2);
                                    break;
                                case 203:
                                    Parent parent = new Parent(user.getName(), user.getParent_id(), user.getStudents());
                                    Intent intent3 = new Intent(MainActivity.this, ParentMainActivity.class);
                                    intent3.putExtra("user", parent);
                                    Toast.makeText(MainActivity.this, "Parent logged in", Toast.LENGTH_SHORT).show();
                                    startActivity(intent3);
                                    break;
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Kullanıcı adı veya şifre hatalı", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserRespond> call, Throwable t) {
                        Log.d("ERROR", t.getMessage());
                    }
                });
            }
        });
    }

    /**
     * Checks whether the edit texts for username and password are empty or not.
     *
     * @return True if both fields are not empty; otherwise, false.
     */
    public boolean checkEditText() {
        String name = username.getText().toString();
        String pass = password.getText().toString();
        return !name.isEmpty() && !pass.isEmpty();
    }

}

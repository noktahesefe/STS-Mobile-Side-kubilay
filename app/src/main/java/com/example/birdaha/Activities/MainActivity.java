package com.example.birdaha.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.birdaha.R;
import com.example.birdaha.Users.Parent;
import com.example.birdaha.Users.Student;
import com.example.birdaha.Users.Teacher;
import com.example.birdaha.Users.User;
import com.google.android.material.button.MaterialButton;

/**
 * This class represents the main login screen of the application.
 * It allows users to enter their username and password and login as either a Student, Teacher, or Parent.
 */
public class MainActivity extends AppCompatActivity {
    EditText username; // Input field for username
    EditText password; // Input field for password
    CheckBox checkBox; // Checkbox for saving login information
    MaterialButton loginButton; // Button to initiate the login process

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
                if (checkEditText()) { // Check if the username and password fields are not empty
                    User user = identifyUser(); // Identify the user based on the entered username
                    if (user == null) {
                        Toast.makeText(MainActivity.this, "Invalid username.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Start the appropriate activity based on the user type
                        if (user instanceof Student) {
                            Toast.makeText(MainActivity.this, "Student logged in!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, StudentMainActivity.class);
                            intent.putExtra("user", user); // Send the user object information to StudentMainActivity
                            startActivity(intent);
                        } else if (user instanceof Teacher) {
                            Toast.makeText(MainActivity.this, "Teacher logged in!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, TeacherMainActivity.class);
                            intent.putExtra("user", user); // Send the user object information to TeacherMainActivity
                            startActivity(intent);
                        } else if (user instanceof Parent) {
                            Toast.makeText(MainActivity.this, "Parent logged in!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, ParentMainActivity.class);
                            intent.putExtra("user", user); // Send the user object information to ParentMainActivity
                            startActivity(intent);
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter your username and password!", Toast.LENGTH_SHORT).show();
                }
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

    /**
     * Identifies the user type based on the entered username's first character.
     *
     * @return A User object representing the identified user, or null if the username is invalid.
     */
    public User identifyUser() {
        String name = username.getText().toString();
        char firstChar = name.charAt(0);
        if (firstChar == 'T') {
            return new Teacher(username.getText().toString(), password.getText().toString());
        } else if (firstChar == 'S') {
            return new Student(username.getText().toString(), password.getText().toString());
        } else if (firstChar == 'P') {
            return new Parent(username.getText().toString(), password.getText().toString());
        } else {
            return null; // Invalid username
        }
    }
}

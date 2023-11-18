package com.example.birdaha.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.birdaha.R;

public class StudentProfile extends AppCompatActivity {
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1;
    TextView nameSurname, classroom, schoolNumber;
    Button changeProfilePicture;
    Button homeworks;
    Button announcements;
    ImageView profilePicture;
    boolean isGranted = false;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        profilePicture.setImageURI(selectedImage);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        nameSurname = (TextView) findViewById(R.id.student_name_surname);
        classroom = (TextView) findViewById(R.id.student_classroom);
        schoolNumber = (TextView) findViewById(R.id.student_school_number);

        changeProfilePicture = (Button) findViewById(R.id.student_gallery);
        homeworks = (Button) findViewById(R.id.student_homeworks);
        announcements = (Button) findViewById(R.id.student_announcements);

        profilePicture = (ImageView) findViewById(R.id.student_profilePicture);

        changeProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndOpenGallery(Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_READ_EXTERNAL_STORAGE);
            }
        });
    }

    private void checkPermissionAndOpenGallery(String permission, int requestCode){
        if(!isGranted){
            ActivityCompat.requestPermissions(StudentProfile.this, new String[] {permission}, requestCode);
        }
        else{
            openGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with changing the profile picture
                openGallery();
            } else {
                // Permission denied, inform the user and ask again
                new AlertDialog.Builder(this)
                        .setTitle("İzin gerekiyor")
                        .setMessage("Galerinize erişim sağlamak için izin gerekiyor!")
                        .setPositiveButton("İzin ver", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                isGranted = true;
                                openGallery();
                            }
                        })
                        .setNegativeButton("Reddet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(StudentProfile.this, "İzin verilmedi. Galeriye erişilemiyor!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){ // Let user select image from gallery
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            profilePicture.setImageURI(selectedImage);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }
}
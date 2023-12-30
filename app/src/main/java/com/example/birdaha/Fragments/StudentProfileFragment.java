package com.example.birdaha.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import com.example.birdaha.Activities.ClassAnnouncementScreen;
import com.example.birdaha.Activities.StudentHomeworkScreen;

import com.example.birdaha.Classrooms.Classroom;
import com.example.birdaha.General.ClassAnnouncementModel;
import com.example.birdaha.General.HomeworksAndAnnouncements;
import com.example.birdaha.General.HwModel;
import com.example.birdaha.R;
import com.example.birdaha.Users.Student;
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

/**
 * The StudentProfileFragment class represents a fragment displaying a student's profile.
 * This fragment inflates a layout to display the student's profile information
 * and handles any necessary view setup.
 */
public class StudentProfileFragment extends Fragment {

    interface GetHomeworkAndAnnouncement{
        @GET("/api/v1/homeworks/announcements/{classroomId}/{studentId}")
        Call<HomeworksAndAnnouncements> getHomeworksAndAnnouncements(
                @Path("classroomId") int classroomId,
                @Path("studentId") int studentId
        );
    }

    // UI elements
    TextView nameSurname, classroom, schoolNumber;
    Button changeProfilePicture;
    Button homeworksButton;
    Button announcementsButton;
    ImageView profilePicture;
    boolean isGranted = false;


    // Activity result launcher for requesting gallery access permission
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), this::handlePermissionResult);

    // Activity result launcher for launching the image picker
    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                requireActivity();
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        Glide.with(this)
                                .load(selectedImage)
                                .circleCrop()
                                .into(profilePicture);
                        //profilePicture.setImageURI(selectedImage);
                    }
                }
            });

    /**
     * Handles the result of the permission request for gallery access.
     *
     * @param isGranted True if the permission is granted, false otherwise.
     */
    private void handlePermissionResult(boolean isGranted) {
        if (this.isGranted) {
            openGallery();
        } else {
            // Permission denied, inform the user and ask again
            new AlertDialog.Builder(requireActivity(), R.style.AlertDialogTheme)
                    .setTitle("İzin gerekiyor")
                    .setMessage("Galerinize erişim sağlamak için izin gerekiyor!")
                    .setPositiveButton("İzin ver", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            StudentProfileFragment.this.isGranted = true;
                            openGallery();
                        }
                    })
                    .setNegativeButton("Reddet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(requireActivity(), "İzin verilmedi. Galeriye erişilemiyor!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }

    /**
     * Key to retrieve the title content.
     */
    private static final String KEY_TITLE="Content";

    /**
     * Empty constructor for the StudentProfileFragment.
     */
    public StudentProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of the StudentProfileFragment.
     *
     * @param student Title content to be displayed.
     * @return A new instance of StudentProfileFragment.
     */
    public static StudentProfileFragment newInstance(Student student) {
        StudentProfileFragment fragment = new StudentProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable("student",student);
        //args.putString(KEY_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called when the fragment is created.
     *
     * @param savedInstanceState A Bundle containing the saved state of the fragment,
     *                            or null if there is no saved state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Creates and returns the view hierarchy associated with the fragment.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState A Bundle containing the saved state of the fragment,
     *                           or null if there is no saved state.
     * @return The root View of the fragment's layout.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            Student student = (Student) bundle.getSerializable("student");
            nameSurname = view.findViewById(R.id.student_name_surname_info);
            classroom = view.findViewById(R.id.student_classroom_info);
            schoolNumber = view.findViewById(R.id.student_school_number_info);
            nameSurname.setText(student.getName());
            Classroom classroom1 = student.getClassroom();
            classroom.setText(String.valueOf(classroom1.getName()));
            schoolNumber.setText(String.valueOf(student.getSchool_no()));

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://sinifdoktoruadmin.online/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            GetHomeworkAndAnnouncement requestUser = retrofit.create(GetHomeworkAndAnnouncement.class);
            requestUser.getHomeworksAndAnnouncements(classroom1.getClassroom_id(), student.getStudent_id()).enqueue(new Callback<HomeworksAndAnnouncements>() {
                @Override
                public void onResponse(Call<HomeworksAndAnnouncements> call, Response<HomeworksAndAnnouncements> response) {
                    if(response.isSuccessful() && response.body() != null){
                        HomeworksAndAnnouncements models = response.body();
                        Log.d("Respond",new Gson().toJson(response.body()));
                        List<HwModel> homeworkModels = models.getHomeworks();
                        List<ClassAnnouncementModel> announcementModels = models.getClassAnnouncements();

                        homeworksButton = view.findViewById(R.id.student_homeworks);

                        homeworksButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(requireActivity(), StudentHomeworkScreen.class);
                                intent.putExtra("homeworks", (Serializable) homeworkModels);
                                startActivity(intent);
                            }
                        });

                        announcementsButton = (Button) view.findViewById(R.id.student_announcements);
                        announcementsButton.setOnClickListener(v -> {
                            Intent intent = new Intent(requireActivity(), ClassAnnouncementScreen.class);
                            intent.putExtra("classAnnouncements",(Serializable) announcementModels);
                            startActivity(intent);
                        });
                    }
                }

                @Override
                public void onFailure(Call<HomeworksAndAnnouncements> call, Throwable t) {
                    Log.d("Error",t.getMessage());
                }
            });

            /*homeworks = (Button) view.findViewById(R.id.student_homeworks);

            homeworks.setOnClickListener(v -> {
                Intent intent = new Intent(requireActivity(), HomeWorkScreen.class);
                startActivity(intent);
            });*/
        }
        //nameSurname = (TextView) view.findViewById(R.id.student_name_surname);
        //classroom = (TextView) view.findViewById(R.id.student_classroom);
        //schoolNumber = (TextView) view.findViewById(R.id.student_school_number);

        changeProfilePicture = (Button) view.findViewById(R.id.student_gallery);



        profilePicture = (ImageView) view.findViewById(R.id.student_profilePicture);



        changeProfilePicture.setOnClickListener(v -> checkPermissionAndOpenGallery());
        return view;
    }

    /**
     * Checks if the app has the necessary permission to read external storage and opens the gallery if permission is granted.
     * If the permission is not granted, a permission request will be initiated.
     */
    public void checkPermissionAndOpenGallery() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, proceed with changing the profile picture
            openGallery();
        } else {
            // Request permission
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    /**
     * Opens the gallery for the user to select a profile picture.
     * Initiates the image picker activity using an explicit intent for picking images from external storage.
     */
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    /**
     * Called immediately after onCreateView() has returned, but before any saved state has been restored
     * in to the view. This gives subclasses a chance to initialize themselves once they know their view
     * hierarchy has been completely created.
     *
     * @param view               The View returned by onCreateView().
     * @param savedInstanceState A Bundle containing the saved state of the fragment,
     *                           or null if there is no saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*String title = getArguments().getString(KEY_TITLE);
        ((TextView)view.findViewById(R.id.title)).setText(title);*/


    }
}
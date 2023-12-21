package com.example.birdaha.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.birdaha.R;
import com.example.birdaha.Users.Teacher;

/**
 * The TeacherProfileFragment class represents a fragment displaying a teacher's profile.
 * This fragment inflates a layout to display the teacher's profile information
 * and handles any necessary view setup.
 */
public class TeacherProfileFragment extends Fragment {

    TextView nameSurname;
    TextView lectures;
    Button changeProfilePicture;
    Button classes;
    ImageView profilePicture;
    View teacherClassroomsContainer;
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
                            TeacherProfileFragment.this.isGranted = true;
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
     * Empty constructor for the TeacherProfileFragment.
     */
    public TeacherProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of the TeacherProfileFragment.
     *
     * @param teacher Title content to be displayed.
     * @return A new instance of TeacherProfileFragment.
     */
    public static TeacherProfileFragment newInstance(Teacher teacher) {
        TeacherProfileFragment fragment = new TeacherProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable("teacher",teacher);
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
        View view = inflater.inflate(R.layout.fragment_teacher_profile, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            Teacher teacher = (Teacher) bundle.getSerializable("teacher");
            nameSurname = view.findViewById(R.id.teacher_name_surname_info);
            lectures = view.findViewById(R.id.teacher_lectures_info);
            changeProfilePicture = view.findViewById(R.id.teacher_gallery);
            classes = view.findViewById(R.id.teacher_classes);
            profilePicture = view.findViewById(R.id.teacher_profilePicture);

            nameSurname.setText(teacher.getName());
            lectures.setText(teacher.getCourse().getName());

            teacherClassroomsContainer = view.findViewById(R.id.teacher_classes_container);
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.teacher_classes_container,new TeacherClassroomsFragment(teacher,teacher.getClassrooms()))
                    .addToBackStack(null)
                    .commit();
            teacherClassroomsContainer.setVisibility(View.INVISIBLE);
        }


        changeProfilePicture.setOnClickListener(v -> checkPermissionAndOpenGallery());



        classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teacherClassroomsContainer.getVisibility() == View.INVISIBLE){
                    teacherClassroomsContainer.setVisibility(View.VISIBLE);
                }
                else{
                    teacherClassroomsContainer.setVisibility(View.INVISIBLE);
                }
            }
        });

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
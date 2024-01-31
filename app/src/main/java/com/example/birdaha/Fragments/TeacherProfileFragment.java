package com.example.birdaha.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.birdaha.General.ProfilePictureRespond;
import com.example.birdaha.General.SendProfilePictureTeacher;
import com.example.birdaha.Helper.ProfilePictureChangeEvent;
import com.example.birdaha.R;
import com.example.birdaha.Users.Student;
import com.example.birdaha.Users.Teacher;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * The TeacherProfileFragment class represents a fragment displaying a teacher's profile.
 * This fragment inflates a layout to display the teacher's profile information
 * and handles any necessary view setup.
 */
public class TeacherProfileFragment extends Fragment {

    // Interface for adding and deleting profile pictures
    interface AddProfilePicture{
        @POST("api/v1/teacher/add/image")
        Call<ProfilePictureRespond> addProfilePicture(@Body SendProfilePictureTeacher teacher);

        @POST("api/v1/teacher/add/image")
        Call<ProfilePictureRespond> deleteProfilePicture(@Body SendProfilePictureTeacher teacher);
    }

    // UI elements
    TextView nameSurname;
    TextView lectures;
    Button changeProfilePicture;
    Button classes;
    ImageButton deleteProfilePictureButton;
    ImageView profilePicture;
    View teacherClassroomsContainer;
    private String image;

    // Activity result launcher for picking an image from the gallery
    private ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            result -> {
                // Handle the selected image result
                if(result != null){
                    Uri imageUri = result;
                    try{
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imageUri);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        image = Base64.encodeToString(byteArray,Base64.DEFAULT);
                        Bundle bundle = getArguments();
                        if(bundle != null) {
                            Teacher teacher = (Teacher) bundle.getSerializable("teacher");
                            Log.d("teacherid", String.valueOf(teacher.getTeacher_id()));
                            SendProfilePictureTeacher sendPP = new SendProfilePictureTeacher(image, teacher.getTeacher_id());
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://sinifdoktoruadmin.online/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            AddProfilePicture sendProfilePicture = retrofit.create(AddProfilePicture.class);
                            sendProfilePicture.addProfilePicture(sendPP).enqueue(new Callback<ProfilePictureRespond>() {
                                @Override
                                public void onResponse(Call<ProfilePictureRespond> call, Response<ProfilePictureRespond> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        ProfilePictureRespond respond = response.body();
                                        String combinedData = teacher.getTeacher_id() + "|" + image;
                                        SharedPreferences preferences = requireContext().getSharedPreferences("TeacherPrefs", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        String key = "teacher_profile_data_" + teacher.getTeacher_id();
                                        editor.putString(key, combinedData);
                                        editor.apply();
                                        Glide.with(requireContext())
                                                .load(bitmap)
                                                .circleCrop()
                                                .into(profilePicture);
                                        Toast.makeText(requireActivity(), "Profil Fotoğrafı Güncellendi", Toast.LENGTH_SHORT).show();
                                        deleteProfilePictureButton.setVisibility(View.VISIBLE);
                                        ProfilePictureChangeEvent event = new ProfilePictureChangeEvent(true,false);
                                        EventBus.getDefault().post(event);
                                    } else {
                                        Toast.makeText(requireActivity(), "Response Unsuccessful" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ProfilePictureRespond> call, Throwable t) {
                                    Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
    );


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
            deleteProfilePictureButton = view.findViewById(R.id.deleteProfilePictureButton);


            nameSurname.setText(teacher.getName());
            lectures.setText(teacher.getCourse().getName());

            teacherClassroomsContainer = view.findViewById(R.id.teacher_classes_container);
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.teacher_classes_container,new TeacherClassroomsFragment(teacher,teacher.getClassrooms()))
                    .addToBackStack(null)
                    .commit();
            teacherClassroomsContainer.setVisibility(View.INVISIBLE);

            profilePicture = view.findViewById(R.id.teacher_profilePicture);
            SharedPreferences preferences = requireContext().getSharedPreferences("TeacherPrefs",Context.MODE_PRIVATE);
            String key = "teacher_profile_data_" + teacher.getTeacher_id();
            String combinedData = preferences.getString(key,"");
            String[] dataParts = combinedData.split("\\|");
            if(TextUtils.isEmpty(combinedData)){
                deleteProfilePictureButton.setVisibility(View.INVISIBLE);
            }
            if(dataParts.length == 2){
                int teacherId = Integer.parseInt(dataParts[0]);
                String encodedImage = dataParts[1];
                if(teacher.getTeacher_id() == teacherId){
                    byte[] byteArray = Base64.decode(encodedImage,Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);
                    Glide.with(requireActivity())
                            .load(bitmap)
                            .circleCrop()
                            .into(profilePicture);
                }
            }
        }

        changeProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryLauncher.launch("image/*");
            }
        });


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

        deleteProfilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                builder.setTitle("Profil Fotoğrafını Sil");
                builder.setMessage("Profil fotoğrafını silmek istediğinize emin misiniz?");
                builder.setPositiveButton("Sil", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Teacher teacher  = (Teacher) bundle.getSerializable("teacher");
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://sinifdoktoruadmin.online/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        SendProfilePictureTeacher teacherDelete = new SendProfilePictureTeacher(null,teacher.getTeacher_id());
                        AddProfilePicture deleteProfilePicture = retrofit.create(AddProfilePicture.class);
                        deleteProfilePicture.deleteProfilePicture(teacherDelete).enqueue(new Callback<ProfilePictureRespond>() {
                            @Override
                            public void onResponse(Call<ProfilePictureRespond> call, Response<ProfilePictureRespond> response) {
                                if(response.isSuccessful() && response.body() != null){
                                    Toast.makeText(requireActivity(), "Profil fotoğrafı başarıyla silindi", Toast.LENGTH_SHORT).show();
                                    SharedPreferences preferences = getContext().getSharedPreferences("TeacherPrefs",Context.MODE_PRIVATE);
                                    String key = "teacher_profile_data_" + teacher.getTeacher_id();
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.remove(key);
                                    editor.apply();
                                    Glide.with(requireActivity())
                                            .clear(profilePicture);
                                    deleteProfilePictureButton.setVisibility(View.INVISIBLE);
                                    ProfilePictureChangeEvent event = new ProfilePictureChangeEvent(false,true);
                                    EventBus.getDefault().post(event);
                                }
                                else{
                                    Toast.makeText(requireActivity(), "Hata oluştu" + response.code(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ProfilePictureRespond> call, Throwable t) {
                                Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        return view;
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
    }
}
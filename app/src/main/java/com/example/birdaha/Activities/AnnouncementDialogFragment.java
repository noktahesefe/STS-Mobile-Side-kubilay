package com.example.birdaha.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.DialogFragment;

import com.example.birdaha.Adapters.ClassAnnouncementAdapter;
import com.example.birdaha.Classrooms.Classroom;
import com.example.birdaha.General.ClassAnnouncementModel;
import com.example.birdaha.General.UpdateRespond;
import com.example.birdaha.R;
import com.example.birdaha.Users.Teacher;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A DialogFragment for adding class announcements in a fullscreen dialog.
 */
public class AnnouncementDialogFragment extends DialogFragment {

    // UI elements
    private EditText announcement_title, announcement_content;

    // Data entities
    private Teacher teacher;
    private Classroom classroom;

    // Image URL for the announcement
    private String image;

    private ClassAnnouncementAdapter classAnnouncementAdapter;

    public AnnouncementDialogFragment(ClassAnnouncementAdapter classAnnouncementAdapter) {
        this.classAnnouncementAdapter = classAnnouncementAdapter;
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.full_screen_announcement_adding_dialog, container, false);

        announcement_title = view.findViewById(R.id.lectureNameEditText);
        announcement_content = view.findViewById(R.id.add_announcement_content);

        ImageButton closeButton = view.findViewById(R.id.fullscreen_dialog_close);
        closeButton.setOnClickListener(v -> dismiss());

        TextView actionButton = view.findViewById(R.id.fullscreen_dialog_action);
        actionButton.setOnClickListener(v -> {

            if(TextUtils.isEmpty(announcement_title.getText())){
                Toast.makeText(requireContext(), "Duyuru adı ekleyiniz", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = announcement_title.getText().toString();
            String content = announcement_content.getText().toString();
            ClassAnnouncementModel announcement = new ClassAnnouncementModel(title, content, classroom.getClassroom_id(), teacher.getTeacher_id());

            classAnnouncementAdapter.getClassAnnouncementModels().add(announcement);
            classAnnouncementAdapter.notifyDataSetChanged();

            announcement.setTeacher(teacher);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://sinifdoktoruadmin.online/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ClassRoomAnnouncementScreen.MakeAnnouncement postAnnouncement = retrofit.create(ClassRoomAnnouncementScreen.MakeAnnouncement.class);
            postAnnouncement.makeAnnouncement(announcement).enqueue(new Callback<UpdateRespond>() {
                @Override
                public void onResponse(Call<UpdateRespond> call, Response<UpdateRespond> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(getActivity(), "Duyuru başarıyla yapıldı", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Hata oluştu!" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateRespond> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                }
            });
            dismiss();
        });
        return view;
    }

    /**
     * Called when the fragment is created.
     *
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            teacher = (Teacher) getArguments().getSerializable("teacher");
            classroom = (Classroom) getArguments().getSerializable("classroom");
        }

    }

    /**
     * The system calls this only when creating the layout in a dialog.
     *
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The created Dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }

    /**
     * Called when the dialog is dismissed.
     *
     * @param dialog The dialog that was dismissed.
     */
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        // Handle the dialog dismiss event
    }

    /**
     * Called when the dialog is canceled.
     *
     * @param dialog The dialog that was canceled.
     */
    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        // Handle the dialog cancel event
    }
}

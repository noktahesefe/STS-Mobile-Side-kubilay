package com.example.birdaha.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.birdaha.General.Event;
import com.example.birdaha.R;

import java.util.ArrayList;

public class HomePageFragment extends Fragment {

    private static final String KEY_TITLE = "Content";
    private ArrayList<Event> eventList = new ArrayList<>();

    public HomePageFragment() {
        // Required empty public constructor
    }

    public static HomePageFragment newInstance(String param1) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String title = getArguments().getString(KEY_TITLE);
            // Use 'title' as needed
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        // Add SlideModels and corresponding Events
        slideModels.add(new SlideModel(R.drawable.icardi2, "Icardi", ScaleTypes.CENTER_CROP));
        eventList.add(new Event("Icardi", R.drawable.icardi2, "Details about Icardi"));

        slideModels.add(new SlideModel(R.drawable.icardi, "Icardi baba", ScaleTypes.FIT));
        eventList.add(new Event("Icardi baba", R.drawable.icardi, "Details about Icardi baba"));

        slideModels.add(new SlideModel(R.drawable.img,"Etkinlik 1", ScaleTypes.CENTER_CROP));
        eventList.add(new Event("Etkinlik 1", R.drawable.img, "Etkinlik 1 içeriği"));

        slideModels.add(new SlideModel(R.drawable.img_1,"Etkinlik 2", ScaleTypes.FIT));
        eventList.add(new Event("Etkinlik 2", R.drawable.img_1, "Etkinlik 2 içeriği"));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT);

        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                if (vibrator != null && vibrator.hasVibrator()) {
                    VibrationEffect effect = VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE);
                    vibrator.vibrate(effect);
                }

                Event currentEvent = eventList.get(i);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View overlayView = inflater.inflate(R.layout.event_detail_overlay, null);

                ImageView imageView = overlayView.findViewById(R.id.event_detail_image);
                TextView titleView = overlayView.findViewById(R.id.event_detail_title);
                TextView detailView = overlayView.findViewById(R.id.event_detail_detail);

                imageView.setImageResource(currentEvent.getImageResource());
                titleView.setText(currentEvent.getTitle());
                detailView.setText(currentEvent.getDetails());

                builder.setView(overlayView);
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void doubleClick(int position) {
                // Implement your logic for double click here
            }
        });

        getChildFragmentManager().beginTransaction()
                .replace(R.id.announcement_container, new GeneralAnnouncementFragment())
                .commit();
    }
}

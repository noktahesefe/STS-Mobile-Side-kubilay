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
import com.example.birdaha.R;

import java.util.ArrayList;

/**
 * The HomePageFragment class represents the fragment displaying the home page content.
 * This fragment is responsible for inflating the layout and setting up the view components
 * required for displaying the home page.
 */
public class HomePageFragment extends Fragment {

    private static final String KEY_TITLE = "Content";

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
        // Here you can handle the arguments if needed
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
        slideModels.add(new SlideModel(R.drawable.icardi2, "Icardi", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.icardi, "Icardi baba", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img,"Etkinlik 1", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img_1,"Etkinlik 2", ScaleTypes.FIT));


        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT);

        imageSlider.setItemClickListener(new ItemClickListener() {
    @Override
        public void onItemSelected(int i) {

        // Get the Vibrator service
        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            // Create a vibration effect
            // You can customize this effect as per your preference
            VibrationEffect effect = VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE);
            vibrator.vibrate(effect);
        }
            SlideModel currentEvent = slideModels.get(i);
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            View overlayView = inflater.inflate(R.layout.event_detail_overlay, null);
            ImageView imageView = overlayView.findViewById(R.id.event_detail_image);
            TextView detail = overlayView.findViewById(R.id.event_detail);
            imageView.setImageResource(currentEvent.getImagePath());
            detail.setText(currentEvent.getTitle());

            builder.setView(overlayView);

            AlertDialog dialog = builder.create();
            dialog.show();
    }

    @Override
    public void doubleClick(int position) {
        // Implement your logic for double click here
    }
});

        // Fragment transaction should be here
        getChildFragmentManager().beginTransaction()
                .replace(R.id.announcement_container, new GeneralAnnouncementFragment())
                .commit();
    }


    private void showImageOverlay(String imageUrlOrResource) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.events_view, null);
        ImageView imageView = dialogView.findViewById(R.id.event_imageView);

    }

}
package com.example.birdaha.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
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
        slideModels.add(new SlideModel(R.drawable.icardi2, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.icardi, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img_1, ScaleTypes.FIT));


        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        // Fragment transaction should be here
        getChildFragmentManager().beginTransaction()
                .replace(R.id.announcement_container, new GeneralAnnouncementFragment())
                .commit();
    }
}

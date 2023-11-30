package com.example.birdaha.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.birdaha.R;

public class EventDetailFragment extends Fragment {

    public EventDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);

        ImageView imageView = view.findViewById(R.id.event_image);
        TextView details = view.findViewById(R.id.event_detail);

        Bundle args = getArguments();
        if(args != null){
            int imageSource = args.getInt("image");
            String detail = args.getString("details");

            imageView.setImageResource(imageSource);
            details.setText(detail);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateBack();
            }
        });

        return view;
    }

    private void navigateBack(){
        FragmentManager manager = getParentFragmentManager();
        manager.popBackStack();
    }
}
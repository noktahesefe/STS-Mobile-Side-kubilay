package com.example.birdaha.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.birdaha.R;


public class GeneralAnnouncementDetailFragment extends Fragment {

    public GeneralAnnouncementDetailFragment() {
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
        View view = inflater.inflate(R.layout.fragment_general_announcement_detail, container, false);

        TextView title = view.findViewById(R.id.announcement_title);
        TextView details = view.findViewById(R.id.announcement_details);

        Bundle args = getArguments();
        if(args != null){
            String titleString = args.getString("title", "");
            String contentString = args.getString("details", "");

            title.setText(titleString);
            details.setText(contentString);
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
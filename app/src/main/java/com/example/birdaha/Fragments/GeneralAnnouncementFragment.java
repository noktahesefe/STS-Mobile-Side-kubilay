package com.example.birdaha.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.birdaha.Adapters.GeneralAnnouncementAdapter;
import com.example.birdaha.General.GeneralAnnouncement;
import com.example.birdaha.R;
import com.example.birdaha.Utilities.GeneralAnnouncementViewInterface;

import java.util.ArrayList;
import java.util.List;


public class GeneralAnnouncementFragment extends Fragment implements GeneralAnnouncementViewInterface {
    private RecyclerView generalAnnouncementrecyclerView;
    private List<GeneralAnnouncement> generalAnnouncements = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_announcement, container, false);

        generalAnnouncementrecyclerView = view.findViewById(R.id.general_announcement_recyclerView);
        generalAnnouncementrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));

        generalAnnouncements.add(new GeneralAnnouncement("Duyuru 1", "Duyuru 1 içeriği"));
        generalAnnouncements.add(new GeneralAnnouncement("Duyuru 2", "Duyuru 2 içeriği"));
        generalAnnouncements.add(new GeneralAnnouncement("Duyuru 3", "Duyuru 3 içeriği"));
        generalAnnouncements.add(new GeneralAnnouncement("Duyuru 4", "Duyuru 4 içeriği"));
        generalAnnouncements.add(new GeneralAnnouncement("Duyuru 5", "Duyuru 5 içeriği"));
        generalAnnouncements.add(new GeneralAnnouncement("Duyuru 6", "Duyuru 6 içeriği"));
        generalAnnouncements.add(new GeneralAnnouncement("Duyuru 7", "Duyuru 7 içeriği"));
        generalAnnouncements.add(new GeneralAnnouncement("Duyuru 8", "Duyuru 8 içeriği"));

        GeneralAnnouncementAdapter adapter = new GeneralAnnouncementAdapter(getActivity(),generalAnnouncements,this);
        generalAnnouncementrecyclerView.setAdapter(adapter);

        return view;

    }

    @Override
    public void onGeneralAnnouncementItemClick(int position) {
        GeneralAnnouncement current = generalAnnouncements.get(position);

        GeneralAnnouncementDetailFragment detailFragment = new GeneralAnnouncementDetailFragment();

        Bundle args = new Bundle();
        args.putString("title",current.getTitle());
        args.putString("details", current.getDetails());
        detailFragment.setArguments(args);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.announcement_container, detailFragment)
                .addToBackStack("Announcement Fragment") // Allows user to navigate back
                .setReorderingAllowed(true)
                .commit();

        generalAnnouncements.clear(); // This line might change
    }
}
package com.example.birdaha.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.birdaha.Adapters.EventAdapter;
import com.example.birdaha.General.Event;
import com.example.birdaha.R;
import com.example.birdaha.Utilities.EventRecyclerViewInterface;

import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment implements EventRecyclerViewInterface {
    private RecyclerView eventRecyclerView;
    private List<Event> events = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event,container,false);

        eventRecyclerView = view.findViewById(R.id.event_recyclerView);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));

        events.add(new Event("Etkinlik 1",R.drawable.img,"Etkinlik 1 içeriği"));
        events.add(new Event("Etkinlik 2", R.drawable.img_1,"Etkinlik 2 içeriği"));

        EventAdapter adapter = new EventAdapter(getActivity(),events,this);
        eventRecyclerView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onEventItemClick(int position) { // This will be done later
        Event currentEvent = events.get(position);

        EventDetailFragment eventDetailFragment = new EventDetailFragment();

        Bundle args = new Bundle();
        args.putInt("image",currentEvent.getImageResource());
        args.putString("details",currentEvent.getDetails());
        eventDetailFragment.setArguments(args);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.event_container, eventDetailFragment)
                .addToBackStack("Event Fragment")
                .setReorderingAllowed(true)
                .commit();

        events.clear(); // This line might change
    }
}
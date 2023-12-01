package com.example.birdaha.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdaha.General.Event;
import com.example.birdaha.R;
import com.example.birdaha.Utilities.EventRecyclerViewInterface;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private final EventRecyclerViewInterface eventRecyclerViewInterface;
    private Context context;
    private List<Event> events;

    public EventAdapter(Context context, List<Event> events, EventRecyclerViewInterface eventRecyclerViewInterface){
        this.context = context;
        this.events = events;
        this.eventRecyclerViewInterface = eventRecyclerViewInterface;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventViewHolder(LayoutInflater.from(context).inflate(R.layout.events_view,parent,false),eventRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event currentEvent =events.get(position);

        holder.image.setImageResource(currentEvent.getImageResource());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    public class EventViewHolder extends RecyclerView.ViewHolder{
        ImageView image;

        public EventViewHolder(@NonNull View itemView, EventRecyclerViewInterface eventRecyclerViewInterface) {
            super(itemView);

            image = itemView.findViewById(R.id.event_imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(eventRecyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            eventRecyclerViewInterface.onEventItemClick(pos, itemView);
                        }
                    }
                }
            });
        }
    }
}

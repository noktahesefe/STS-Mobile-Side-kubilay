package com.example.birdaha.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdaha.General.GeneralAnnouncement;
import com.example.birdaha.R;
import com.example.birdaha.Utilities.GeneralAnnouncementViewInterface;

import java.util.List;

public class GeneralAnnouncementAdapter extends RecyclerView.Adapter<GeneralAnnouncementAdapter.GeneralAnnouncementViewHolder>{
    private final GeneralAnnouncementViewInterface generalAnnouncementViewInterface;
    private Context context;
    private List<GeneralAnnouncement> generalAnnouncements;

    public GeneralAnnouncementAdapter(Context context, List<GeneralAnnouncement> generalAnnouncements, GeneralAnnouncementViewInterface generalAnnouncementViewInterface){
        this.context = context;
        this.generalAnnouncements = generalAnnouncements;
        this.generalAnnouncementViewInterface = generalAnnouncementViewInterface;
    }

    @NonNull
    @Override
    public GeneralAnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GeneralAnnouncementViewHolder(LayoutInflater.from(context).inflate(R.layout.general_announcement_view,parent,false),generalAnnouncementViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull GeneralAnnouncementViewHolder holder, int position) {
        GeneralAnnouncement currentGeneralAnnouncement = generalAnnouncements.get(position);

        holder.title.setText(currentGeneralAnnouncement.getTitle());
    }

    @Override
    public int getItemCount() {
        return generalAnnouncements.size();
    }
    public class GeneralAnnouncementViewHolder extends RecyclerView.ViewHolder{
        TextView title;

        public GeneralAnnouncementViewHolder(@NonNull View itemView, GeneralAnnouncementViewInterface generalAnnouncementViewInterface) {
            super(itemView);

            title = itemView.findViewById(R.id.general_announcement_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(generalAnnouncementViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            generalAnnouncementViewInterface.onGeneralAnnouncementItemClick(pos, itemView);
                        }
                    }
                }
            });
        }
    }
}

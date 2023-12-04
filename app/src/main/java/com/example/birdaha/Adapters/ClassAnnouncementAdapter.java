package com.example.birdaha.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdaha.General.ClassAnnouncementModel;
import com.example.birdaha.R;

import java.util.ArrayList;

public class ClassAnnouncementAdapter extends RecyclerView.Adapter<ClassAnnouncementAdapter.ClassAnnouncementViewHolder> {

    Context context;

    ArrayList<ClassAnnouncementModel> classAnnouncementModels;

    public ClassAnnouncementAdapter(Context context, ArrayList<ClassAnnouncementModel> classAnnouncementModels){
        this.context = context;
        this.classAnnouncementModels = classAnnouncementModels;
    }


    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public ClassAnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row,parent,false);

        return new ClassAnnouncementViewHolder(view);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ClassAnnouncementAdapter.ClassAnnouncementViewHolder holder, int position) {
        holder.textViewTitle.setText(classAnnouncementModels.get(position).getTitle());
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return classAnnouncementModels.size();
    }

    public static class ClassAnnouncementViewHolder extends RecyclerView.ViewHolder{


        TextView textViewTitle;
        CardView cardView;
        Context context;



        public ClassAnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the textViewTitle variable with the view from the layout with id textView
            textViewTitle = itemView.findViewById(R.id.textView2);

            // Initialize the cardView variable with the view from the layout with id cardView
            cardView = itemView.findViewById(R.id.cardView2);

            // Set a click listener on the cardView
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create an AlertDialog.Builder object with the context of the itemView
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());

                    // Create a LayoutInflater object from the itemView's context
                    LayoutInflater inflater = LayoutInflater.from(itemView.getContext());

                    // Inflate the overlay_layout.xml file into a View object
                    View overlayView = inflater.inflate(R.layout.class_announcement_overlay_layout, null);

                    // Set the inflated view as the custom view for the AlertDialog
                    builder.setView(overlayView);

                    // Create an AlertDialog object from the builder
                    AlertDialog dialog = builder.create();

                    // Show the AlertDialog
                    dialog.show();
                }
            });
        }
    }
}

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
import java.util.Locale;

public class ClassAnnouncementAdapter extends RecyclerView.Adapter<ClassAnnouncementAdapter.ClassAnnouncementViewHolder> {

    Context context;

    ArrayList<ClassAnnouncementModel> classAnnouncementModels;
    ArrayList<ClassAnnouncementModel> filteredList;

    public ClassAnnouncementAdapter(Context context, ArrayList<ClassAnnouncementModel> classAnnouncementModels){
        this.context = context;
        this.classAnnouncementModels = classAnnouncementModels;
        this.filteredList = classAnnouncementModels;
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
    public void onBindViewHolder(@NonNull ClassAnnouncementViewHolder holder, int position) {
        if (position >= 0 && position < filteredList.size()) {
            holder.textViewTitle.setText(filteredList.get(position).getTitle());
            holder.itemView.setVisibility(View.VISIBLE); // Show the item
        } else {
            holder.itemView.setVisibility(View.INVISIBLE); // Hide the item
        }
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return classAnnouncementModels.size();
    }

    public void search(String query){
        ArrayList<ClassAnnouncementModel> searchList = new ArrayList<>();
        if(query.isEmpty()){
            searchList.addAll(classAnnouncementModels);
        }
        else{
            String filterPattern = query.toLowerCase(Locale.getDefault()).trim();
            for(ClassAnnouncementModel model : classAnnouncementModels){
                if(model.getTitle().toLowerCase(Locale.getDefault()).contains(filterPattern)){
                    searchList.add(model);
                }
            }
        }
        filteredList  = searchList;
        notifyDataSetChanged();
    }

    public void restoreOriginalList() {
        filteredList.clear();
        filteredList.addAll(classAnnouncementModels);
        notifyDataSetChanged();
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

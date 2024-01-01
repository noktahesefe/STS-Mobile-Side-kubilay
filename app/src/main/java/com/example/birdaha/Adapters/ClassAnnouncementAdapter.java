package com.example.birdaha.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdaha.General.ClassAnnouncementModel;
import com.example.birdaha.General.HwModel;
import com.example.birdaha.R;
import com.example.birdaha.Utilities.ClassAnnouncementViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClassAnnouncementAdapter extends RecyclerView.Adapter<ClassAnnouncementAdapter.ClassAnnouncementViewHolder> implements Filterable {

    Context context;
    private final ClassAnnouncementViewInterface classAnnouncementViewInterface;
    ArrayList<ClassAnnouncementModel> classAnnouncementModels;
    ArrayList<ClassAnnouncementModel> classAnnouncementModelsFiltered;

    public ClassAnnouncementAdapter(Context context, ArrayList<ClassAnnouncementModel> classAnnouncementModels,ClassAnnouncementViewInterface classAnnouncementViewInterface){
        this.context = context;
        this.classAnnouncementModels = classAnnouncementModels;
        this.classAnnouncementModelsFiltered = classAnnouncementModels;
        this.classAnnouncementViewInterface = classAnnouncementViewInterface;
    }

    /**
     * Called when the RecyclerView needs a new ViewHolder to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ClassAnnouncementViewHolder that holds a View for class announcement items.
     */
    @NonNull
    @Override
    public ClassAnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_teacher_announcement, parent, false);
        return new ClassAnnouncementViewHolder(view, classAnnouncementViewInterface);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ClassAnnouncementViewHolder holder, int position) {
        ClassAnnouncementModel current = classAnnouncementModels.get(position);
        holder.textViewName.setText(current.getTitle());
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(classAnnouncementViewInterface != null){
                    int pos = classAnnouncementModels.indexOf(current);
                    classAnnouncementViewInterface.onClassAnnouncementItemClick(classAnnouncementModels.get(pos),v);
                }
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of class announcement items.
     */
    @Override
    public int getItemCount() {
        return classAnnouncementModels.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.values = classAnnouncementModelsFiltered;
                    filterResults.count = classAnnouncementModelsFiltered.size();
                } else {
                    String searchStr = constraint.toString().toLowerCase();
                    List<ClassAnnouncementModel> classAnnouncementModels1 = new ArrayList<>();
                    for (ClassAnnouncementModel model : classAnnouncementModelsFiltered) {
                        if (model.getTitle().toLowerCase().contains(searchStr)) {
                            classAnnouncementModels1.add(model);
                        }
                    }
                    filterResults.values = classAnnouncementModels1;
                    filterResults.count = classAnnouncementModels1.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                classAnnouncementModels = (ArrayList<ClassAnnouncementModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    /**
     * This class represents the ViewHolder for individual class announcement items.
     */
    public static class ClassAnnouncementViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;

        public ClassAnnouncementViewHolder(@NonNull View itemView, ClassAnnouncementViewInterface classAnnouncementViewInterface) {
            super(itemView);

            // Initialize the textViewTitle variable with the view from the layout with id textView
            textViewName = itemView.findViewById(R.id.announcement_title);

        }
    }
}

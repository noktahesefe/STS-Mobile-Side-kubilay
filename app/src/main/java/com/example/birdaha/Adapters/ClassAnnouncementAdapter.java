package com.example.birdaha.Adapters;

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
import com.example.birdaha.Utilities.ClassAnnouncementViewInterface;

import java.util.ArrayList;
import java.util.Locale;

/**
 * This class represents the adapter for displaying class announcements in a RecyclerView.
 */
public class ClassAnnouncementAdapter extends RecyclerView.Adapter<ClassAnnouncementAdapter.ClassAnnouncementViewHolder> {

    Context context;
    private final ClassAnnouncementViewInterface classAnnouncementViewInterface;
    ArrayList<ClassAnnouncementModel> classAnnouncementModels;
    ArrayList<ClassAnnouncementModel> filteredList;

    /**
     * Constructor for the ClassAnnouncementAdapter class.
     *
     * @param context                    The context in which the adapter is used.
     * @param classAnnouncementModels    The list of class announcement models to display.
     * @param classAnnouncementViewInterface The interface to handle class announcement item clicks.
     */
    public ClassAnnouncementAdapter(Context context, ArrayList<ClassAnnouncementModel> classAnnouncementModels, ClassAnnouncementViewInterface classAnnouncementViewInterface) {
        this.context = context;
        this.classAnnouncementModels = classAnnouncementModels;
        this.filteredList = classAnnouncementModels;
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
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
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
        if (position >= 0 && position < filteredList.size()) {
            holder.textViewTitle.setText(filteredList.get(position).getTitle());
            holder.itemView.setVisibility(View.VISIBLE); // Show the item
        } else {
            holder.itemView.setVisibility(View.INVISIBLE); // Hide the item
        }
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

    /**
     * Filter the list of class announcement items based on the provided query.
     *
     * @param query The search query to filter the list.
     */
    public void search(String query) {
        ArrayList<ClassAnnouncementModel> searchList = new ArrayList<>();
        if (query.isEmpty()) {
            searchList.addAll(classAnnouncementModels);
        } else {
            String filterPattern = query.toLowerCase(Locale.getDefault()).trim();
            for (ClassAnnouncementModel model : classAnnouncementModels) {
                if (model.getTitle().toLowerCase(Locale.getDefault()).contains(filterPattern)) {
                    searchList.add(model);
                }
            }
        }
        filteredList = searchList;
        notifyDataSetChanged();
    }

    /**
     * Restore the original list of class announcement items.
     */
    public void restoreOriginalList() {
        filteredList.clear();
        filteredList.addAll(classAnnouncementModels);
        notifyDataSetChanged();
    }

    /**
     * This class represents the ViewHolder for individual class announcement items.
     */
    public static class ClassAnnouncementViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        CardView cardView;

        /**
         * Constructor for ClassAnnouncementViewHolder.
         *
         * @param itemView                      The view item for the class announcement item.
         * @param classAnnouncementViewInterface The interface to handle class announcement item clicks.
         */
        public ClassAnnouncementViewHolder(@NonNull View itemView, ClassAnnouncementViewInterface classAnnouncementViewInterface) {
            super(itemView);

            // Initialize the textViewTitle variable with the view from the layout with id homework_detail_name
            textViewTitle = itemView.findViewById(R.id.homework_detail_title);

            // Initialize the cardView variable with the view from the layout with id cardView2
            cardView = itemView.findViewById(R.id.cardView2);

            // Set a click listener on the cardView
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        classAnnouncementViewInterface.onClassAnnouncementItemClick(pos, cardView);
                    }
                }
            });
        }
    }
}

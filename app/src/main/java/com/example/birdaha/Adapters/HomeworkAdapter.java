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

import com.example.birdaha.General.HwModel;
import com.example.birdaha.R;
import com.example.birdaha.Utilities.ClassroomHomeworkViewInterface;

import java.util.ArrayList;
import java.util.Locale;

/**
 * This class represents the adapter for displaying homework items in a RecyclerView.
 */
public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.HomeworkViewHolder> {

    private final ClassroomHomeworkViewInterface homeworkViewInterface;
    Context context;
    ArrayList<HwModel> hwModels;
    ArrayList<HwModel> filteredList;

    /**
     * Constructor for the HomeworkAdapter class.
     *
     * @param context               The context in which the adapter is used.
     * @param hwModels              The list of homework models to display.
     * @param homeworkViewInterface The interface to handle homework item clicks.
     */
    public HomeworkAdapter(Context context, ArrayList<HwModel> hwModels, ClassroomHomeworkViewInterface homeworkViewInterface) {
        this.context = context;
        this.hwModels = hwModels;
        this.filteredList = hwModels;
        this.homeworkViewInterface = homeworkViewInterface;
    }

    @NonNull
    @Override
    public HomeworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row2, parent, false);
        return new HomeworkViewHolder(view, homeworkViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkViewHolder holder, int position) {
        if (position >= 0 && position < filteredList.size()) {
            holder.textViewTitle.setText(filteredList.get(position).getTitle());
            holder.itemView.setVisibility(View.VISIBLE); // Show the item
        } else {
            holder.itemView.setVisibility(View.INVISIBLE); // Hide the item
        }
    }

    @Override
    public int getItemCount() {
        return hwModels.size();
    }

    /**
     * Filter the list of homework items based on the provided query.
     *
     * @param query The search query to filter the list.
     */
    public void search(String query) {
        ArrayList<HwModel> searchList = new ArrayList<>();
        if (query.isEmpty()) {
            searchList.addAll(hwModels);
        } else {
            String filterPattern = query.toLowerCase(Locale.getDefault()).trim();
            for (HwModel model : hwModels) {
                if (model.getTitle().toLowerCase(Locale.getDefault()).contains(filterPattern)) {
                    searchList.add(model);
                }
            }
        }
        filteredList = searchList;
        notifyDataSetChanged();
    }

    /**
     * Restore the original list of homework items.
     */
    public void restoreOriginalList() {
        filteredList.clear();
        filteredList.addAll(hwModels);
        notifyDataSetChanged();
    }

    /**
     * This class represents the ViewHolder for individual homework items.
     */
    public static class HomeworkViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        CardView cardView;

        /**
         * Constructor for HomeworkViewHolder.
         *
         * @param itemView              The view item for the homework item.
         * @param homeworkViewInterface The interface to handle homework item clicks.
         */
        public HomeworkViewHolder(@NonNull View itemView, ClassroomHomeworkViewInterface homeworkViewInterface) {
            super(itemView);

            // Initialize the textViewTitle variable with the view from the layout with id textView
            textViewTitle = itemView.findViewById(R.id.textView);

            // Initialize the cardView variable with the view from the layout with id cardView
            cardView = itemView.findViewById(R.id.cardView);

            // Set a click listener on the cardView
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (homeworkViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            homeworkViewInterface.onClassroomHomeworkItemClick(pos, cardView);
                        }
                    }
                }
            });
        }
    }
}
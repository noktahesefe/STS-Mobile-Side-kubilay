package com.example.birdaha.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdaha.General.HwModel;
import com.example.birdaha.R;
import com.example.birdaha.Utilities.ClassroomHomeworkViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.HomeworkViewHolder> implements Filterable{
    private final ClassroomHomeworkViewInterface homeworkViewInterface;
    Context context;
    ArrayList<HwModel> hwModels;
    ArrayList<HwModel> hwModelsFiltered;

    public HomeworkAdapter(Context context, ArrayList<HwModel> hwModels, ClassroomHomeworkViewInterface homeworkViewInterface){
        this.context = context;
        this.hwModels = hwModels;
        this.hwModelsFiltered = hwModels;
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
        HwModel current = hwModels.get(position);
        holder.textViewTitle.setText(current.getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homeworkViewInterface != null) {
                    int pos = hwModels.indexOf(current);
                    if (pos != -1) {
                        homeworkViewInterface.onClassroomHomeworkItemClick(hwModels.get(pos), v);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hwModels.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.values = hwModelsFiltered;
                    filterResults.count = hwModelsFiltered.size();
                }
                else{
                    String searchStr = constraint.toString().toLowerCase();
                    List<HwModel> hwmodel = new ArrayList<>();
                    for(HwModel model : hwModelsFiltered){
                        if(model.getTitle().toLowerCase().contains(searchStr)){
                            hwmodel.add(model);
                        }
                    }
                    filterResults.values = hwmodel;
                    filterResults.count = hwmodel.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                hwModels = (ArrayList<HwModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
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
            textViewTitle = itemView.findViewById(R.id.studentName);

            // Initialize the cardView variable with the view from the layout with id cardView
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
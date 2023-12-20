package com.example.birdaha.Adapters;

import android.app.AlertDialog;
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
import com.example.birdaha.Utilities.ClassroomHomeworkViewInterface;

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

        return new ClassAnnouncementViewHolder(view, classAnnouncementViewInterface);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ClassAnnouncementViewHolder holder, int position) {
        ClassAnnouncementModel current = classAnnouncementModels.get(position);
        holder.textViewTitle.setText(current.getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
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
     * @return
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
                if(constraint == null || constraint.length() == 0){
                    filterResults.values = classAnnouncementModelsFiltered;
                    filterResults.count = classAnnouncementModelsFiltered.size();
                }
                else{
                    String searchStr = constraint.toString().toLowerCase();
                    List<ClassAnnouncementModel> classAnnouncementModels1 = new ArrayList<>();
                    for(ClassAnnouncementModel model : classAnnouncementModelsFiltered){
                        if(model.getTitle().toLowerCase().contains(searchStr)){
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

    /*public void search(String query){
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
    }*/

    public static class ClassAnnouncementViewHolder extends RecyclerView.ViewHolder{


        TextView textViewTitle;
        CardView cardView;

        public ClassAnnouncementViewHolder(@NonNull View itemView, ClassAnnouncementViewInterface classAnnouncementViewInterface) {
            super(itemView);

            // Initialize the textViewTitle variable with the view from the layout with id textView
            textViewTitle = itemView.findViewById(R.id.homework_detail_name);

            // Initialize the cardView variable with the view from the layout with id cardView
            cardView = itemView.findViewById(R.id.cardView2);

            // Set a click listener on the cardView
            /*cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        classAnnouncementViewInterface.onClassAnnouncementItemClick(pos,cardView);
                    }
                }
            });*/
        }
    }
}

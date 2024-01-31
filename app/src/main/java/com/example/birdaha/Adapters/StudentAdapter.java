package com.example.birdaha.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdaha.General.HwModel;
import com.example.birdaha.General.StudentModel;
import com.example.birdaha.R;
import com.example.birdaha.Users.Student;
import com.example.birdaha.Utilities.HomeworkStudentsViewInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * The StudentAdapter class is an adapter for the RecyclerView that displays a list of students.
 * It binds the data to the views in each ViewHolder and provides a way to handle student item click events
 * through the HomeworkStudentsViewInterface. It also supports filtering based on the student name.
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private Context context;
    private ArrayList<StudentModel> students;
    private ArrayList<StudentModel> studentsFiltered;
    private final HomeworkStudentsViewInterface studentViewInterface;

    /**
     * Constructor for the StudentAdapter.
     *
     * @param context                 The context of the calling activity or fragment.
     * @param students                The list of student items to be displayed in the RecyclerView.
     * @param studentViewInterface    The interface to handle student item click events.
     */
    public StudentAdapter(Context context, ArrayList<StudentModel> students, HomeworkStudentsViewInterface studentViewInterface) {
        this.context = context;
        this.students = students;
        this.studentsFiltered = students;
        this.studentViewInterface = studentViewInterface;
    }

    /**
     * Inflates the layout for each item in the RecyclerView.
     *
     * @param parent   The parent view group.
     * @param viewType The type of view.
     * @return A new instance of the StudentViewHolder.
     */
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row3, parent, false);
        return new StudentViewHolder(view, studentViewInterface);
    }

    /**
     * Binds the data to the views in each ViewHolder.
     *
     * @param holder   The ViewHolder to bind the data to.
     * @param position The position of the item in the data set.
     */
    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        StudentModel currentStudent = students.get(position);
        holder.studentNameTextView.setText(currentStudent.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(studentViewInterface != null)
                {
                    int pos = students.indexOf(currentStudent);
                    if(pos != -1) {
                        try {
                            studentViewInterface.onHomeworkStudentItemClick(students.get(pos), v);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            }
        });
    }

    /**
     * Returns the total number of items in the data set.
     *
     * @return The total number of items.
     */
    @Override
    public int getItemCount() {
        return students.size();
    }

    /**
     * Returns a filter that can be used to constrain data with a filtering pattern.
     *
     * @return A filter for student items.
     */
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.values = studentsFiltered;
                    filterResults.count = studentsFiltered.size();
                }
                else{
                    String searchStr = constraint.toString().toLowerCase();
                    List<StudentModel> studentModel = new ArrayList<>();
                    for(StudentModel model : studentsFiltered){
                        if(model.getName().toLowerCase().contains(searchStr)){
                            studentModel.add(model);
                        }
                    }
                    filterResults.values = studentModel;
                    filterResults.count = studentModel.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                students = (ArrayList<StudentModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    /**
     * This class represents the ViewHolder for individual class announcement items.
     */
    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView studentNameTextView;
        CardView cardView;

        /**
         * Constructor for StudentViewHolder.
         *
         * @param itemView              The view item for the student item.
         * @param studentViewInterface The interface to handle student item clicks.
         */
        public StudentViewHolder(@NonNull View itemView, HomeworkStudentsViewInterface studentViewInterface) {
            super(itemView);

            // Initialize the textViewTitle variable with the view from the layout with id homework_detail_name
            studentNameTextView = itemView.findViewById(R.id.studentName);

            // Initialize the cardView variable with the view from the layout with id cardView2
            cardView = itemView.findViewById(R.id.cardView3);
        }
    }

}

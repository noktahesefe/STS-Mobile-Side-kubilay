package com.example.birdaha.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdaha.R;
import com.example.birdaha.Users.Student;
import com.example.birdaha.Utilities.HomeworkStudentsViewInterface;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    Context context;

    ArrayList<Student> students;

    private final HomeworkStudentsViewInterface studentViewInterface;


    public StudentAdapter(Context context, ArrayList<Student> students, HomeworkStudentsViewInterface studentViewInterface) {
        this.context = context;
        this.students = students;
        this.studentViewInterface = studentViewInterface;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row3, parent, false);
        return new StudentViewHolder(view, studentViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student currentStudent = students.get(position);
        holder.studentNameTextView.setText(currentStudent.getName());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }





    /**
     * This class represents the ViewHolder for individual class announcement items.
     */
    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView studentNameTextView;
        CardView cardView;


        public StudentViewHolder(@NonNull View itemView, HomeworkStudentsViewInterface studentViewInterface) {
            super(itemView);

            // Initialize the textViewTitle variable with the view from the layout with id homework_detail_name
            studentNameTextView = itemView.findViewById(R.id.studentName);

            // Initialize the cardView variable with the view from the layout with id cardView2
            cardView = itemView.findViewById(R.id.cardView3);

            // Set a click listener on the cardView
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        studentViewInterface.onHomeworkStudentItemClick(pos, cardView);
                    }
                }
            });
        }
    }

}

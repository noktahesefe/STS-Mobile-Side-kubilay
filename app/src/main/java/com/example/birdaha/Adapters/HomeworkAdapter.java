package com.example.birdaha.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdaha.Activities.ClassroomHomeworkScreen;
import com.example.birdaha.General.HwModel;
import com.example.birdaha.General.UpdateRespond;
import com.example.birdaha.R;
import com.example.birdaha.Users.Teacher;
import com.example.birdaha.Utilities.ClassroomHomeworkViewInterface;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The HomeworkAdapter class is an adapter for the RecyclerView that displays a list of homework items.
 * It binds the data to the views in each ViewHolder and provides a way to handle homework item click events
 * through the ClassroomHomeworkViewInterface. It also supports filtering based on the homework title.
 */
public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.HomeworkViewHolder> implements Filterable {
    private final ClassroomHomeworkViewInterface homeworkViewInterface;
    Context context;
    ArrayList<HwModel> hwModels;
    ArrayList<HwModel> hwModelsFiltered;
    private final Teacher currentTeacher;

    /**
     * Constructor for the HomeworkAdapter.
     *
     * @param context               The context of the calling activity or fragment.
     * @param hwModels              The list of homework items to be displayed in the RecyclerView.
     * @param homeworkViewInterface The interface to handle homework item click events.
     * @param teacher               The current teacher object.
     */
    public HomeworkAdapter(Context context, ArrayList<HwModel> hwModels, ClassroomHomeworkViewInterface homeworkViewInterface, Teacher teacher) {
        this.context = context;
        this.hwModels = hwModels;
        this.hwModelsFiltered = hwModels;
        this.homeworkViewInterface = homeworkViewInterface;
        this.currentTeacher = teacher;
    }

    /**
     * Inflates the layout for each item in the RecyclerView.
     *
     * @param parent   The parent view group.
     * @param viewType The type of view.
     * @return A new instance of the HomeworkViewHolder.
     */
    @NonNull
    @Override
    public HomeworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_teacher_homework, parent, false);
        return new HomeworkViewHolder(view, homeworkViewInterface);
    }

    /**
     * Binds the data to the views in each ViewHolder.
     *
     * @param holder   The ViewHolder to bind the data to.
     * @param position The position of the item in the data set.
     */
    @Override
    public void onBindViewHolder(@NonNull HomeworkViewHolder holder, int position) {
        ZoneId turkeyZone = ZoneId.of("Europe/Istanbul");
        LocalDate localDate = LocalDate.now(turkeyZone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = localDate.format(formatter);

        HwModel current = hwModels.get(position);
        holder.textViewname.setText(current.getTitle());
        holder.textViewname.setBackground((current.getDue_date().compareTo(formattedDateTime) < 0) ? context.getDrawable(R.drawable.darkgray_round_background) : context.getDrawable(R.drawable.blue_round_background));
        holder.textViewname.setOnClickListener(new View.OnClickListener() {
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

        if (current.getTeacher_id() == currentTeacher.getTeacher_id()) {
            holder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (homeworkViewInterface != null) {
                        int pos = hwModels.indexOf(current);
                        if (pos != -1) {
                            homeworkViewInterface.onClassroomHomeworkEditClick(hwModels.get(pos), v);
                        }
                    }
                }
            });
        }

        if (current.getTeacher_id() == currentTeacher.getTeacher_id()) {
            System.out.println("Homework name:" + current.getTitle());
            System.out.println("Teacher Id:" + currentTeacher.getTeacher_id());
            System.out.println("Homework Teacher Id: " + current.getTeacher_id());
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Ödevi Sil");
                    builder.setMessage("Ödevi silmek istediğinize emin misiniz?");
                    builder.setPositiveButton("Sil", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://sinifdoktoruadmin.online/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            ClassroomHomeworkScreen.AddHomework deleteHomework = retrofit.create(ClassroomHomeworkScreen.AddHomework.class);
                            deleteHomework.deleteHomework(current.getHomework_id()).enqueue(new Callback<UpdateRespond>() {
                                @Override
                                public void onResponse(Call<UpdateRespond> call, Response<UpdateRespond> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        Toast.makeText(context, "Ödev başarıyla silindi!", Toast.LENGTH_SHORT).show();
                                        hwModels.remove(current);
                                        notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(context, "Hata oluştu!" + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<UpdateRespond> call, Throwable t) {
                                    Log.d("Error", t.getMessage());
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            });
        }
    }

    /**
     * Returns the total number of items in the data set.
     *
     * @return The total number of items.
     */
    @Override
    public int getItemCount() {
        return hwModels.size();
    }

    /**
     * Returns a filter that can be used to constrain data with a filtering pattern.
     *
     * @return A filter for homework items.
     */
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.values = hwModelsFiltered;
                    filterResults.count = hwModelsFiltered.size();
                } else {
                    String searchStr = constraint.toString().toLowerCase();
                    List<HwModel> hwmodel = new ArrayList<>();
                    for (HwModel model : hwModelsFiltered) {
                        if (model.getTitle().toLowerCase().contains(searchStr)) {
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
     * ViewHolder class representing each item in the RecyclerView.
     */
    public static class HomeworkViewHolder extends RecyclerView.ViewHolder {

        TextView textViewname;
        ImageButton editButton;
        ImageButton deleteButton;

        /**
         * Constructor for HomeworkViewHolder.
         *
         * @param itemView              The view item for the homework item.
         * @param homeworkViewInterface The interface to handle homework item clicks.
         */

        public HomeworkViewHolder(@NonNull View itemView, ClassroomHomeworkViewInterface homeworkViewInterface) {
            super(itemView);


            // Initialize the cardView variable with the view from the layout with id cardView
            textViewname = itemView.findViewById(R.id.homework_title);
            editButton = itemView.findViewById(R.id.edit_icon_button);
            deleteButton = itemView.findViewById(R.id.delete_icon_button);
        }
    }
}
package com.example.birdaha.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.birdaha.R;
import com.example.birdaha.Users.Student;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

/**
 * The CustomExpandableListAdapter class is a custom implementation of BaseExpandableListAdapter
 * for managing and displaying expandable lists. It is specifically designed to work with a list of
 * students grouped under a single title. The adapter handles the creation of group and child views
 * for the expandable list.
 */
public class CustomExpandableListAdapter extends BaseExpandableListAdapter {


    private Context context;
    private String title;
    private Map<String, List<Student>> listItem;

    /**
     * Constructor for the CustomExpandableListAdapter.
     *
     * @param context  The context in which the adapter is used.
     * @param title    The title representing the group in the expandable list.
     * @param listItem The map containing the list of students grouped under the specified title.
     */
    public CustomExpandableListAdapter(Context context, String title, Map<String, List<Student>> listItem) {
        this.context = context;
        this.title = title;
        this.listItem = listItem;
    }

    /**
     * Returns the number of groups in the expandable list.
     *
     * @return The number of groups in the expandable list.
     */
    @Override
    public int getGroupCount() {
        return 1;
    }

    /**
     * Returns the number of children in a specific group.
     *
     * @param groupPosition The position of the group.
     * @return The number of children in the specified group.
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return listItem.get(title).size();
    }

    /**
     * Returns the title of a specific group.
     *
     * @param groupPosition The position of the group.
     * @return The title of the specified group.
     */
    @Override
    public Object getGroup(int groupPosition) {
        return title;
    }

    /**
     * Returns the child at a specific position within a group.
     *
     * @param groupPosition The position of the group.
     * @param childPosition The position of the child within the group.
     * @return The child at the specified position.
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listItem.get(title).get(childPosition);
    }

    /**
     * Returns the ID for a specific group.
     *
     * @param groupPosition The position of the group.
     * @return The ID of the specified group.
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Returns the ID for a specific child.
     *
     * @param groupPosition The position of the group.
     * @param childPosition The position of the child within the group.
     * @return The ID of the specified child.
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * Indicates whether the IDs of groups and children are stable across changes in the data set.
     *
     * @return True if the IDs are stable, false otherwise.
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Returns the view for the group at the specified position.
     *
     * @param groupPosition The position of the group.
     * @param isExpanded    Whether the group is currently expanded or collapsed.
     * @param convertView   The old view to reuse if possible.
     * @param parent        The parent that this view will eventually be attached to.
     * @return The view for the group at the specified position.
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String) getGroup(groupPosition);
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.list_group, null);

        TextView txtTitle = (TextView) convertView.findViewById(R.id.TextView_listTitle);
        //txtTitle.setTypeface(null, Typeface.BOLD);
        txtTitle.setText(title);
        return convertView;
    }

    /**
     * Returns the view for the child at the specified position within the group.
     *
     * @param groupPosition The position of the group.
     * @param childPosition The position of the child within the group.
     * @param isLastChild   Whether the child is the last child within the group.
     * @param convertView   The old view to reuse if possible.
     * @param parent        The parent that this view will eventually be attached to.
     * @return The view for the child at the specified position.
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String title = (String) getChild(groupPosition, childPosition).toString();
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);

        TextView txtChild = (TextView) convertView.findViewById(R.id.TextView_expandableListItem);
        ImageView imageView = convertView.findViewById(R.id.student_image);
        SharedPreferences preferences = context.getSharedPreferences("ParentPrefs", Context.MODE_PRIVATE);
        Student student = (Student) getChild(groupPosition, childPosition);
        String key = "parent_student_data_" + student.getStudent_id();
        String combinedData = preferences.getString(key, "");
        String[] dataParts = combinedData.split("\\|");
        if (dataParts[1].equals("null")) {
            System.out.println("null");
            Glide.with(context)
                    .load(R.drawable.ic_account_circle_white_24)
                    .into(imageView);
        } else {
            System.out.println("not null");
            if (dataParts.length == 2) {
                int studentId = Integer.parseInt(dataParts[0]);
                String encodedImage = dataParts[1];
                if (student.getStudent_id() == studentId) {
                    byte[] byteArray = Base64.decode(encodedImage, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    Glide.with(context)
                            .load(bitmap)
                            .circleCrop()
                            .into(imageView);
                }
            }
        }
        //txtChild.setTypeface(null, Typeface.BOLD);
        txtChild.setText(title);
        return convertView;
    }

    /**
     * Indicates whether the child at the specified position within the group is selectable.
     *
     * @param groupPosition The position of the group.
     * @param childPosition The position of the child within the group.
     * @return True if the child is selectable, false otherwise.
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

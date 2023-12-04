package com.example.birdaha.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.birdaha.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {


    private Context context;
    private String title;
    private Map<String, List<String>> listItem;

    public CustomExpandableListAdapter(Context context, String title, Map<String, List<String>> listItem) {
        this.context = context;
        this.title = title;
        this.listItem = listItem;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listItem.get(title).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return title;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listItem.get(title).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String) getGroup(groupPosition);
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.list_group, null);

        TextView txtTitle = (TextView) convertView.findViewById(R.id.TextView_listTitle);
        //txtTitle.setTypeface(null, Typeface.BOLD);
        txtTitle.setText(title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String title = (String) getChild(groupPosition, childPosition);
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);

        TextView txtChild = (TextView) convertView.findViewById(R.id.TextView_expandableListItem);
        //txtChild.setTypeface(null, Typeface.BOLD);
        txtChild.setText(title);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

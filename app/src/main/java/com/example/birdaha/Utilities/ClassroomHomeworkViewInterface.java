package com.example.birdaha.Utilities;

import android.view.View;

import com.example.birdaha.General.HwModel;


/**
 * This interface defines the callback method for handling clicks on classroom homework items.
 */
public interface ClassroomHomeworkViewInterface {

    /**
     * Called when a classroom homework item is clicked.
     *
     * @param position The position of the clicked item in the list.
     * @param view     The View associated with the clicked item.
     */
    void onClassroomHomeworkItemClick(int position, View view);
}

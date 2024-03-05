package studenttrackingsystem.Utilities;

import android.view.View;

import studenttrackingsystem.General.HwModel;

public interface ClassroomHomeworkViewInterface {
    void onClassroomHomeworkItemClick(HwModel clickedItem, View view);
    void onClassroomHomeworkEditClick(HwModel clickedItem, View view);
}

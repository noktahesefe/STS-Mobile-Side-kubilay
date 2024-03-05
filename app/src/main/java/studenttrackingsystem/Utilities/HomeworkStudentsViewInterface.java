package studenttrackingsystem.Utilities;

import android.view.View;

import studenttrackingsystem.General.StudentModel;

public interface HomeworkStudentsViewInterface {
    void onHomeworkStudentItemClick(StudentModel clickedItem, View view) throws InterruptedException;
}

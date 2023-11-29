package com.example.birdaha.Helper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.birdaha.Interface.NavigationManager;
import com.example.birdaha.R;

public class FragmentNavigationManager implements NavigationManager {

    private static FragmentNavigationManager mInstance;

    private FragmentManager mFragmentManager;
    private AppCompatActivity activity;

    public static FragmentNavigationManager getmInstance(AppCompatActivity activity)
    {
        if(mInstance == null)
            mInstance = new FragmentNavigationManager();

        mInstance.configure(activity);
        return mInstance;
    }

    private void configure(AppCompatActivity activity)
    {
        activity = activity;
        mFragmentManager = activity.getSupportFragmentManager();
    }

    @Override
    public void showFragment(Fragment fragmentContent, boolean b) {
        FragmentManager fm = mFragmentManager;
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.container, fragmentContent);
        ft.addToBackStack(null);

        if(b)
            ft.commitAllowingStateLoss();
        else
            ft.commit();

        fm.executePendingTransactions();
    }
}

package com.mrkostua.mathalarm.AlarmSettingsOptions;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

import com.mrkostua.mathalarm.R;

/**
 * Created by Администратор on 01.12.2017.
 */

public class FragmentHelper {
    private Activity activity;

    public FragmentHelper(Activity activity) {
        this.activity = activity;
    }

    public void loadFragment(Fragment fragment) {
        android.app.FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit(); // save the changes
    }
}

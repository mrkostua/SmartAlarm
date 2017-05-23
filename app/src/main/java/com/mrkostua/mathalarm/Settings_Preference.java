package com.mrkostua.mathalarm;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class Settings_Preference extends PreferenceActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(ShowLogs.LOG_STATUS)ShowLogs.i("Settings_preference onCreate");
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new Fragment_Preference()).commit();
    }

    public static class Fragment_Preference extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_preference);
        }
    }




}



package com.example.mathalarm;

import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.mathalarm.Alarms.MathAlarm.MainMathAlarm;

import java.util.Locale;


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



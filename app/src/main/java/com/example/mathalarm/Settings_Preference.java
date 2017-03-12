package com.example.mathalarm;

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


public class Settings_Preference extends PreferenceActivity implements Preference.OnPreferenceChangeListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(MainMathAlarm.TAG,"Settings_preference onCreate");
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new Fragment_Preference()).commit();

        final ListPreference listPreference = (ListPreference) findPreference("languages_listPreference");
        //listPreference.setOnPreferenceChangeListener(this);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.i(MainMathAlarm.TAG," Settings_Preference + onPreferenceChange");
        return false;
    }


    public static class Fragment_Preference extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_preference);

        }
    }




}



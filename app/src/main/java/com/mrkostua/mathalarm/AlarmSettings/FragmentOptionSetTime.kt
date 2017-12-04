package com.mrkostua.mathalarm.AlarmSettings

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import com.mrkostua.mathalarm.ConstantValues
import com.mrkostua.mathalarm.R

class FragmentOptionSetTime : Fragment(), SettingsFragmentInterface {
    private lateinit var fragmentView: View
    private lateinit var tpSetAlarmTime: TimePicker
    override var settingsOptionIndex = ConstantValues.alarmSettingsOptionsList.indexOf(FragmentOptionSetTime())


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
        fragmentView = inflater.inflate(R.layout.fragment_option_set_time, container, false)
        initializeViews(fragmentView)
        return fragmentView
    }

    private fun initializeViews(view: View) {
        tpSetAlarmTime = view.findViewById(R.id.tpSetAlarmTime) as TimePicker
    }

    override fun saveSettingsInSharedPreferences() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


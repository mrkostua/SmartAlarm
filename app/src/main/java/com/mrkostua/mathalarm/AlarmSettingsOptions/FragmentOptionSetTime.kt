package com.mrkostua.mathalarm.AlarmSettingsOptions

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrkostua.mathalarm.ConstantValues
import com.mrkostua.mathalarm.R

class FragmentOptionSetTime : Fragment(), AlarmSettingsOptionsHelper {
    private lateinit var fragmentView: View
    override var settingsOptionIndex = ConstantValues.alarmSettingsOptionsMap[FragmentOptionSetTime()] ?: -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
        fragmentView = inflater.inflate(R.layout.activity_time_option, container, false)
        return fragmentView
    }

    override fun saveSettingsInSharedPreferences() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


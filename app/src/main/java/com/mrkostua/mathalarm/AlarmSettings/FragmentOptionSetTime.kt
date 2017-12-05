package com.mrkostua.mathalarm.AlarmSettings

import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrkostua.mathalarm.AlarmObject
import com.mrkostua.mathalarm.ConstantValues
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.SharedPreferencesAlarmData
import com.mrkostua.mathalarm.Tools.NotificationTools
import kotlinx.android.synthetic.main.fragment_option_set_time.*


class FragmentOptionSetTime : Fragment(), SettingsFragmentInterface {
    override var settingsOptionIndex = ConstantValues.alarmSettingsOptionsList.indexOf(FragmentOptionSetTime())

    private val activityContext = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        this.context
    else
        activity

    private val sharedPreferencesAlarmData = SharedPreferencesAlarmData(activityContext)

    private val notificationTools = NotificationTools(activityContext)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
        saveSettingsInSharedPreferences()

        return inflater.inflate(R.layout.fragment_option_set_time, container, false)
    }

    override fun saveSettingsInSharedPreferences() {
        tpSetAlarmTime.setOnTimeChangedListener({ timePocker, hourOfDay, minute ->
            showTimeUntilAlarmBoom(hourOfDay, minute)
            sharedPreferencesAlarmData.saveLastAlarmData(AlarmObject(hourOfDay, minute))

        })

    }

    private fun showTimeUntilAlarmBoom(hourOfDay: Int, minutes: Int) {
        val (hoursUntilBoob, minutesUntilBoom) = notificationTools.getTimeUntilAlarmBoob(hourOfDay, minutes)

        notificationTools.showToastTimeToAlarmBoom(hourOfDay, minutes)
        tvTimeUntilAlarmBoom.text = resources.getString(R.string.timeUntilAlarmBoom, hoursUntilBoob, minutesUntilBoom)

    }


}


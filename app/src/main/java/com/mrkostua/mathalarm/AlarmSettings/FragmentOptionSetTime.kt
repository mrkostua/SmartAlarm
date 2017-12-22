package com.mrkostua.mathalarm.AlarmSettings

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrkostua.mathalarm.KotlinActivitiesInterface
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.NotificationTools
import com.mrkostua.mathalarm.Tools.PreferencesConstants
import com.mrkostua.mathalarm.Tools.SharedPreferencesHelper
import com.mrkostua.mathalarm.Tools.SharedPreferencesHelper.set
import kotlinx.android.synthetic.main.fragment_option_set_time.*


class FragmentOptionSetTime : Fragment(), SettingsFragmentInterface, KotlinActivitiesInterface {
    private val TAG = this.javaClass.simpleName

    override lateinit var fragmentContext: Context

    private lateinit var sharedPreferencesAlarmData: SharedPreferences

    private lateinit var notificationTools: NotificationTools

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
        initializeDependOnContextVariables()
        saveSettingsInSharedPreferences()
        return inflater.inflate(R.layout.fragment_option_set_time, container, false)
    }

    override fun initializeDependOnContextVariables() {
        fragmentContext = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            this.context
        else
            activity.applicationContext

        notificationTools = NotificationTools(fragmentContext)
        sharedPreferencesAlarmData = SharedPreferencesHelper.customSharedPreferences(fragmentContext, PreferencesConstants.ALARM_SP_NAME.getKeyValue())
    }

    override fun saveSettingsInSharedPreferences() {
        tpSetAlarmTime.setOnTimeChangedListener({ timePicker, hourOfDay, minute ->
            showTimeUntilAlarmBoom(hourOfDay, minute)
            sharedPreferencesAlarmData[PreferencesConstants.ALARM_HOURS.getKeyValue()] = hourOfDay
            sharedPreferencesAlarmData[PreferencesConstants.ALARM_MINUTES.getKeyValue()] = minute

        })

    }

    private fun showTimeUntilAlarmBoom(hourOfDay: Int, minutes: Int) {
        val (hoursUntilBoob, minutesUntilBoom) = notificationTools.getTimeUntilAlarmBoob(hourOfDay, minutes)

        notificationTools.showToastTimeToAlarmBoom(hourOfDay, minutes)
        tvTimeUntilAlarmBoom.text = resources.getString(R.string.timeUntilAlarmBoom, hoursUntilBoob, minutesUntilBoom)

    }


}


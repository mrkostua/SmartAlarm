package com.mrkostua.mathalarm.AlarmSettings

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import com.mrkostua.mathalarm.KotlinActivitiesInterface
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.*
import com.mrkostua.mathalarm.Tools.SharedPreferencesHelper.get
import com.mrkostua.mathalarm.Tools.SharedPreferencesHelper.set
import kotlinx.android.synthetic.main.fragment_option_set_time.*


class FragmentOptionSetTime : Fragment(), SettingsFragmentInterface, KotlinActivitiesInterface, TimePicker.OnTimeChangedListener {
    private val TAG = this.javaClass.simpleName

    override lateinit var fragmentContext: Context

    private lateinit var sharedPreferencesHelper: SharedPreferences

    private lateinit var notificationTools: NotificationTools

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ShowLogs.log(TAG, "onCreateView")

        initializeDependOnContextVariables()
        return inflater?.inflate(R.layout.fragment_option_set_time, container, false)
    }

    override fun initializeDependOnContextVariables() {
        fragmentContext = activity.applicationContext

        notificationTools = NotificationTools(fragmentContext)
        sharedPreferencesHelper = SharedPreferencesHelper.customSharedPreferences(fragmentContext, PreferencesConstants.ALARM_SP_NAME.getKeyValue())

        initializeTimePicker()
        saveSettingsInSharedPreferences()
    }
    //todo
    override fun onTimeChanged(timePicker: TimePicker?, hourOfDay: Int, minute: Int) {
        ShowLogs.log(TAG,"onTimeChanged")
        showTimeUntilAlarmBoom(hourOfDay, minute)
        sharedPreferencesHelper[PreferencesConstants.ALARM_HOURS.getKeyValue()] = hourOfDay
        sharedPreferencesHelper[PreferencesConstants.ALARM_MINUTES.getKeyValue()] = minute
    }

    override fun saveSettingsInSharedPreferences() {
    }


    private fun showTimeUntilAlarmBoom(hourOfDay: Int, minutes: Int) {
        val (hoursUntilBoob, minutesUntilBoom) = notificationTools.getTimeUntilAlarmBoob(hourOfDay, minutes)

        notificationTools.showToastMessage("Done!")
        tvTimeUntilAlarmBoom.text = resources.getString(R.string.timeUntilAlarmBoom, hoursUntilBoob, minutesUntilBoom)

    }

    private fun initializeTimePicker() {
        if (!AlarmTools.isFirstAlarmCreation(sharedPreferencesHelper)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tpSetAlarmTime.hour = sharedPreferencesHelper[PreferencesConstants.ALARM_HOURS.getKeyValue(), PreferencesConstants.ALARM_HOURS.getDefaultIntValue()] ?:
                        PreferencesConstants.ALARM_HOURS.getDefaultIntValue()
                tpSetAlarmTime.minute = sharedPreferencesHelper[PreferencesConstants.ALARM_MINUTES.getKeyValue(), PreferencesConstants.ALARM_MINUTES.getDefaultIntValue()] ?:
                        PreferencesConstants.ALARM_HOURS.getDefaultIntValue()

                tvTimeUntilAlarmBoom.text = resources.getString(R.string.timeUntilAlarmBoom, tpSetAlarmTime.hour, tpSetAlarmTime.minute)
            } else {
                ShowLogs.log(TAG," initializeTimePicker  sdk is lower than 25")
                //todo implement for Api < 25
            }
        }
    }

}


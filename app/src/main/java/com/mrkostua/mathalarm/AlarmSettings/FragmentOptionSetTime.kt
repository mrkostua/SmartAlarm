package com.mrkostua.mathalarm.AlarmSettings

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrkostua.mathalarm.Interfaces.KotlinActivitiesInterface
import com.mrkostua.mathalarm.Interfaces.SettingsFragmentInterface
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.NotificationTools
import com.mrkostua.mathalarm.Tools.PreferencesConstants
import com.mrkostua.mathalarm.Tools.SharedPreferencesHelper
import com.mrkostua.mathalarm.Tools.SharedPreferencesHelper.get
import com.mrkostua.mathalarm.Tools.SharedPreferencesHelper.set
import com.mrkostua.mathalarm.Tools.ShowLogs
import kotlinx.android.synthetic.main.fragment_option_set_time.*

/**
 * TODO test on with 2 timePicker styles and maybe improve some design
 */
class FragmentOptionSetTime : Fragment(), SettingsFragmentInterface, KotlinActivitiesInterface {
    private val TAG = this.javaClass.simpleName

    override lateinit var fragmentContext: Context

    private lateinit var sharedPreferencesHelper: SharedPreferences
    private lateinit var notificationTools: NotificationTools

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContext = activity.applicationContext
        initializeDependOnContextVariables(fragmentContext)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_option_set_time, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        ShowLogs.log(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        initializeDependOnViewVariables(view)
        notificationTools.showToastMessage(getString(R.string.alarmTimeLimitationMessage))

    }

    override fun onResume() {
        super.onResume()
        initializeTimePicker()
        saveSettingsInSharedPreferences()
    }

    override fun initializeDependOnContextVariables(context: Context) {
        notificationTools = NotificationTools(fragmentContext)
        sharedPreferencesHelper = SharedPreferencesHelper.customSharedPreferences(fragmentContext, PreferencesConstants.ALARM_SP_NAME.getKeyValue())

    }

    override fun initializeDependOnViewVariables(view: View?) {
    }

    override fun saveSettingsInSharedPreferences() {
        tpSetAlarmTime.setOnTimeChangedListener({ timePicker, hourOfDay, minute ->
            showTimeUntilAlarmBoom(hourOfDay, minute)
            sharedPreferencesHelper[PreferencesConstants.ALARM_HOURS.getKeyValue()] = hourOfDay
            sharedPreferencesHelper[PreferencesConstants.ALARM_MINUTES.getKeyValue()] = minute
        })
    }

    private fun showTimeUntilAlarmBoom(hourOfDay: Int, minutes: Int) {
        val (hoursUntilBoob, minutesUntilBoom) = notificationTools.getTimeUntilAlarmBoob(hourOfDay, minutes)
        tvTimeUntilAlarmBoom.text = resources.getString(R.string.timeUntilAlarmBoom, hoursUntilBoob, minutesUntilBoom)

    }

    @Suppress("DEPRECATION")
    private fun initializeTimePicker() {
        val hours = sharedPreferencesHelper[PreferencesConstants.ALARM_HOURS.getKeyValue(), PreferencesConstants.ALARM_HOURS.getDefaultIntValue()] ?:
                PreferencesConstants.ALARM_HOURS.getDefaultIntValue()
        val minutes = sharedPreferencesHelper[PreferencesConstants.ALARM_MINUTES.getKeyValue(), PreferencesConstants.ALARM_MINUTES.getDefaultIntValue()] ?:
                PreferencesConstants.ALARM_MINUTES.getDefaultIntValue()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tpSetAlarmTime.hour = hours
            tpSetAlarmTime.minute = minutes
            tvTimeUntilAlarmBoom.text = resources.getString(R.string.timeUntilAlarmBoom, tpSetAlarmTime.hour, tpSetAlarmTime.minute)

        } else {
            tpSetAlarmTime.currentHour = hours
            tpSetAlarmTime.currentMinute = minutes

        }
        showTimeUntilAlarmBoom(hours, minutes)
    }

}


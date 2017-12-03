package com.mrkostua.mathalarm

import android.app.Fragment
import com.mrkostua.mathalarm.AlarmSettingsOptions.FragmentOptionSetTime

import java.util.HashMap

/**
 * @author Kostiantyn on 07.11.2017.
 */

object ConstantValues {
    /**
     * LOG_DEBUG_STATUS true for debug mode, false for production.
     */
    val LOG_DEBUG_STATUS = true


    val ALARM_RINGTONE_NAMES = arrayOf("mechanic_clock", "energy", "loud", "digital_clock")

    //actions for alarm receiver
    val SNOOZE_ACTION = "alarm_snooze"
    val DISMISS_ACTION = "alarm_dismiss"
    val START_NEW_ALARM_ACTION = "alarm_start_new"

    val ALARM_SHARED_PREFERENCE_NAME = "LAST_ALARM_DATA"
    val LAST_ALARM_HOURS = "hours"
    val LAST_ALARM_MINUTES = "minutes"

    val CUSTOM_ALARM_SETTINGS_HOURS = 8
    val CUSTOM_ALARM_SETTINGS_MINUTES = 15


    val alarmSettingsOptionsList: MutableList<Fragment> = ArrayList()

    init {
        alarmSettingsOptionsList.add(0, FragmentOptionSetTime())
    }

}

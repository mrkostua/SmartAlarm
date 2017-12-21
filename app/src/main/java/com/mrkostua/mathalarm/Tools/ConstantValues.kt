package com.mrkostua.mathalarm.Tools

import android.app.Fragment
import com.mrkostua.mathalarm.AlarmSettings.FragmentOptionSetMessage
import com.mrkostua.mathalarm.AlarmSettings.FragmentOptionSetTime
import com.mrkostua.mathalarm.AlarmSettings.FragmentSettingsOptionSetRingtone

/**
 * @author Kostiantyn on 07.11.2017.
 */

object ConstantValues {
    /**
     * LOG_DEBUG_STATUS true for debug mode, false for production.
     */
    val LOG_DEBUG_STATUS = true


    val ALARM_RINGTONE_NAMES = arrayOf("mechanic_clock", "energy", "loud", "digital_clock")
    val CUSTOM_ALARM_RINGTONE = "energy"

    //actions for alarm receiver
    val SNOOZE_ACTION = "alarm_snooze"
    val DISMISS_ACTION = "alarm_dismiss"
    val START_NEW_ALARM_ACTION = "alarm_start_new"
    val CUSTOM_ALARM_SETTINGS_HOURS = 8
    val CUSTOM_ALARM_SETTINGS_MINUTES = 15

    val INTENT_KEY_WHICH_FRAGMENT_TO_LOAD_FIRST = "whichFragmentToLoadFirst"
    val alarmSettingsOptionsList: MutableList<Fragment> = ArrayList()

    init {
        alarmSettingsOptionsList.add(0, FragmentOptionSetTime())
        alarmSettingsOptionsList.add(1, FragmentSettingsOptionSetRingtone())
        alarmSettingsOptionsList.add(2, FragmentOptionSetMessage())
    }
}


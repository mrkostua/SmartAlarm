package com.mrkostua.mathalarm

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

    //actions for alarm receiver
    val SNOOZE_ACTION = "alarm_snooze"
    val DISMISS_ACTION = "alarm_dismiss"
    val START_NEW_ALARM_ACTION = "alarm_start_new"

    val ALARM_SHARED_PREFERENCE_NAME = "LAST_ALARM_DATA"
    val LAST_ALARM_HOURS = "hours"
    val LAST_ALARM_MINUTES = "minutes"
    val SHARED_PREFERENCES_KEY_TEXT_MESSAGE = "text_message"
    val SHARED_PREFERENCES_KEY_TEXT_RINGTONE_RES_ID = "ringtoneResId";

    val CUSTOM_ALARM_SETTINGS_HOURS = 8
    val CUSTOM_ALARM_SETTINGS_MINUTES = 15
    val CUSTOM_ALARM_TEXT_MESSAGE = "Good morning fucker"
    val CUSTOM_ALARM_RINGTONE_ID = 0
    //todo change ringtone custom id or replace it is initialization

    val SHARED_PREFERENCES_WRONG_TIME_VALUE = -1
    val INTENT_KEY_WHICH_FRAGMENT_TO_LOAD_FIRST = "whichFragmentToLoadFirst"
    val alarmSettingsOptionsList: MutableList<Fragment> = ArrayList()

    init {
        alarmSettingsOptionsList.add(0, FragmentOptionSetTime())
        alarmSettingsOptionsList.add(1, FragmentSettingsOptionSetRingtone())
        alarmSettingsOptionsList.add(2, FragmentOptionSetMessage())
    }

}

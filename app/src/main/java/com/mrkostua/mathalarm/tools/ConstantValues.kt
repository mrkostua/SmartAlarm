package com.mrkostua.mathalarm.tools

import android.app.Fragment
import com.mrkostua.mathalarm.alarmSettings.FragmentOptionSetDeepSleepMusic
import com.mrkostua.mathalarm.alarmSettings.FragmentOptionSetMessage
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.FragmentOptionSetRingtone
import com.mrkostua.mathalarm.alarmSettings.optionSetTime.FragmentOptionSetTime
import com.mrkostua.mathalarm.alarmSettings.mainSettings.AlarmSettingsNames

/**
 * @author Kostiantyn on 07.11.2017.
 */

object ConstantValues {
    /**
     * LOG_DEBUG_STATUS true for debug mode, false for production.
     */
    val LOG_DEBUG_STATUS = true


    val ALARM_RINGTONE_NAMES = arrayOf("ringtone_mechanic_clock", "ringtone_energy", "ringtone_loud", "ringtone_digital_clock")
    val CUSTOM_ALARM_RINGTONE = "ringtone_energy"

    //actions for alarm receiver
    val SNOOZE_ACTION = "alarm_snooze"
    val DISMISS_ACTION = "alarm_dismiss"
    val START_NEW_ALARM_ACTION = "alarm_start_new"
    val CUSTOM_ALARM_SETTINGS_HOURS = 8
    val CUSTOM_ALARM_SETTINGS_MINUTES = 15

    val PREFERENCES_WRONG_VALUE = -1
    val INTENT_KEY_WHICH_FRAGMENT_TO_LOAD_FIRST = "whichFragmentToLoadFirst"
    val alarmSettingsOptionsList: MutableList<Fragment> = ArrayList()

    init {
        alarmSettingsOptionsList.add(AlarmSettingsNames.OPTION_SET_TIME.getKeyValue(), FragmentOptionSetTime())
        alarmSettingsOptionsList.add(AlarmSettingsNames.OPTION_SET_RINGTONE.getKeyValue(), FragmentOptionSetRingtone())
        alarmSettingsOptionsList.add(AlarmSettingsNames.OPTION_SET_MESSAGE.getKeyValue(), FragmentOptionSetMessage())
        alarmSettingsOptionsList.add(AlarmSettingsNames.OPTION_SET_DEEP_SLEEP_MUSIC.getKeyValue(), FragmentOptionSetDeepSleepMusic())
    }
}


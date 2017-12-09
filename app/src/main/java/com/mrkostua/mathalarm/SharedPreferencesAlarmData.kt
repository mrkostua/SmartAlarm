package com.mrkostua.mathalarm

import android.content.Context
import android.content.SharedPreferences

/**
 * @author Kostiantyn on 21.11.2017.
 */
class SharedPreferencesAlarmData(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(ConstantValues.ALARM_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    private val hours: Int = 0
    private val minutes: Int = 0
    private val message: String = ""
    private val ringtoneResId: Int = 0

    val alarmHours: Int
        get() = sharedPreferences.getInt(ConstantValues.LAST_ALARM_HOURS, -1)

    val alarmMinutes: Int
        get() = sharedPreferences.getInt(ConstantValues.LAST_ALARM_MINUTES, -1)

    val alarmTextMessage: String
        get() = sharedPreferences.getString(ConstantValues.SHARED_PREFERENCES_KEY_TEXT_MESSAGE, ConstantValues.CUSTOM_ALARM_TEXT_MESSAGE)

    val ringtoneResID: Int
        get() = sharedPreferences.getInt(ConstantValues.SHARED_PREFERENCES_KEY_TEXT_RINGTONE_RES_ID,ConstantValues.CUSTOM_ALARM_RINGTONE_ID )


    fun saveLastAlarmData(alarmObject: AlarmObject) {
        editor.putInt(ConstantValues.LAST_ALARM_HOURS,
                if (alarmObject.hours != -1) alarmObject.hours else alarmHours)
        editor.putInt(ConstantValues.LAST_ALARM_MINUTES,
                if (alarmObject.minutes != -1) alarmObject.minutes else alarmMinutes)
        editor.putString(ConstantValues.LAST_ALARM_MINUTES,
                if (alarmObject.textMessage == ConstantValues.CUSTOM_ALARM_TEXT_MESSAGE) alarmObject.textMessage else alarmTextMessage)

        editor.apply()
    }

}

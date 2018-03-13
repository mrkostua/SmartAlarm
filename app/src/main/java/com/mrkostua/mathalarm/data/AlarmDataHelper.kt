package com.mrkostua.mathalarm.data

import android.content.SharedPreferences
import com.mrkostua.mathalarm.tools.PreferencesConstants
import com.mrkostua.mathalarm.extensions.get
import com.mrkostua.mathalarm.extensions.set
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 3/12/2018.
 */
class AlarmDataHelper @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun saveAlarmTimeInSP(hourOfDay: Int, minutes: Int) {
        sharedPreferences[PreferencesConstants.ALARM_HOURS.getKeyValue()] = hourOfDay
        sharedPreferences[PreferencesConstants.ALARM_MINUTES.getKeyValue()] = minutes
    }

    fun getAlarmTimeFromSP(): Pair<Int, Int> {
        return Pair(sharedPreferences[PreferencesConstants.ALARM_HOURS.getKeyValue(), PreferencesConstants.ALARM_HOURS.getDefaultIntValue(), null],
                sharedPreferences[PreferencesConstants.ALARM_MINUTES.getKeyValue(), PreferencesConstants.ALARM_MINUTES.getDefaultIntValue(), null])
    }
}
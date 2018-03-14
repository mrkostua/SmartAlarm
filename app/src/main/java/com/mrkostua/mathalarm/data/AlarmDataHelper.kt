package com.mrkostua.mathalarm.data

import android.content.SharedPreferences
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneManagerHelper
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneObject
import com.mrkostua.mathalarm.extensions.get
import com.mrkostua.mathalarm.extensions.set
import com.mrkostua.mathalarm.tools.PreferencesConstants
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 3/12/2018.
 */
class AlarmDataHelper @Inject constructor(private val sharedPreferences: SharedPreferences, private val ringtoneManagerHelper: RingtoneManagerHelper) {
    private val TAG = this.javaClass.simpleName

    fun saveAlarmTimeInSP(hourOfDay: Int, minutes: Int) {
        sharedPreferences[PreferencesConstants.ALARM_HOURS.getKeyValue()] = hourOfDay
        sharedPreferences[PreferencesConstants.ALARM_MINUTES.getKeyValue()] = minutes
    }

    fun getAlarmTimeFromSP(): Pair<Int, Int> {
        return Pair(sharedPreferences[PreferencesConstants.ALARM_HOURS.getKeyValue(), PreferencesConstants.ALARM_HOURS.getDefaultIntValue(), null],
                sharedPreferences[PreferencesConstants.ALARM_MINUTES.getKeyValue(), PreferencesConstants.ALARM_MINUTES.getDefaultIntValue(), null])
    }


    //TODO move this code to Model layer as DB implement it using Room library ( can be useful in the future in case of implementing list of all set alarms)
     fun getRingtonesForPopulation(): ArrayList<RingtoneObject> {
        val ringtonesList = ArrayList<RingtoneObject>()
        ringtonesList.add(RingtoneObject("ringtone_mechanic_clock", 2))
        ringtonesList.add(RingtoneObject("ringtone_energy", 1))
        ringtonesList.add(RingtoneObject("ringtone_loud", 3))
        ringtonesList.addAll(ringtoneManagerHelper.getDefaultAlarmRingtonesList())
        return ringtonesList
    }
}
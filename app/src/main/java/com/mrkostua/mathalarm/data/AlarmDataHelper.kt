package com.mrkostua.mathalarm.data

import android.content.SharedPreferences
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneManagerHelper
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneObject
import com.mrkostua.mathalarm.alarms.mathAlarm.AlarmObject
import com.mrkostua.mathalarm.extensions.get
import com.mrkostua.mathalarm.extensions.set
import com.mrkostua.mathalarm.tools.PreferencesConstants
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Kostiantyn Prysiazhnyi on 3/12/2018.
 */
@Singleton
class AlarmDataHelper @Inject constructor(private val sharedPreferences: SharedPreferences, private val ringtoneManagerHelper: RingtoneManagerHelper) {
    private val TAG = this.javaClass.simpleName

    fun getAlarmDataObject(): AlarmObject {
        val time = getTimeFromSP()
        return AlarmObject(time.first, time.second, getTextMessageFromSP(), getRingtoneFromSP())
    }

    fun saveTimeInSP(hourOfDay: Int, minutes: Int) {
        sharedPreferences[PreferencesConstants.ALARM_HOURS.getKeyValue()] = hourOfDay
        sharedPreferences[PreferencesConstants.ALARM_MINUTES.getKeyValue()] = minutes
    }

    fun getTimeFromSP(): Pair<Int, Int> =
            Pair(sharedPreferences[PreferencesConstants.ALARM_HOURS.getKeyValue(), PreferencesConstants.ALARM_HOURS.getDefaultIntValue()],
                    sharedPreferences[PreferencesConstants.ALARM_MINUTES.getKeyValue(), PreferencesConstants.ALARM_MINUTES.getDefaultIntValue()])

    fun saveRingtoneInSP(ringtoneName: String) {
        sharedPreferences[PreferencesConstants.ALARM_RINGTONE_NAME.getKeyValue()] = ringtoneName

    }

    fun getRingtoneFromSP(): String =
            sharedPreferences[PreferencesConstants.ALARM_RINGTONE_NAME.getKeyValue(),
                    PreferencesConstants.ALARM_RINGTONE_NAME.defaultRingtoneName]

    fun getTextMessageFromSP(): String = sharedPreferences[PreferencesConstants.ALARM_TEXT_MESSAGE.getKeyValue(),
            PreferencesConstants.ALARM_TEXT_MESSAGE.defaultTextMessage]


    //TODO move this code to Model layer as DB implement it using Room library ( can be useful in the future in case of implementing list of all set alarms)
    fun getRingtonesForPopulation(): ArrayList<RingtoneObject> {
        val ringtonesList = ArrayList<RingtoneObject>()
        ringtonesList.add(RingtoneObject("ringtone_mechanic_clock", 2))
        ringtonesList.add(RingtoneObject("ringtone_energy", 1))
        ringtonesList.add(RingtoneObject("ringtone_loud", 3))
        ringtonesList.addAll(ringtoneManagerHelper.getDefaultAlarmRingtonesList())
        return ringtonesList
    }

    fun getSavedRingtoneAlarmObject(ringtoneList: ArrayList<RingtoneObject> = getRingtonesForPopulation()): RingtoneObject {
        val ringtoneName: String = getRingtoneFromSP()
        return ringtoneList.find { ao -> ao.name == ringtoneName }
                ?: ringtoneList[1]
    }

    fun isFirstAlarmSaving() = sharedPreferences[PreferencesConstants.ALARM_HOURS.getKeyValue(), PreferencesConstants.ALARM_HOURS.emptyPreferencesValue] ==
            PreferencesConstants.ALARM_HOURS.emptyPreferencesValue
}
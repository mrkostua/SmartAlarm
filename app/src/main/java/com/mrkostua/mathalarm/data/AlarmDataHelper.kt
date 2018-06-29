package com.mrkostua.mathalarm.data

import android.content.SharedPreferences
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneManagerHelper
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneObject
import com.mrkostua.mathalarm.alarms.mathAlarm.AlarmObject
import com.mrkostua.mathalarm.extensions.get
import com.mrkostua.mathalarm.extensions.set
import com.mrkostua.mathalarm.tools.ConstantsPreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Kostiantyn Prysiazhnyi on 3/12/2018.
 */
@Singleton
open class AlarmDataHelper @Inject constructor(private val sharedPreferences: SharedPreferences, private val ringtoneManagerHelper: RingtoneManagerHelper) {
    fun getAlarmDataObject(): AlarmObject {
        val time = getTimeFromSP()
        return AlarmObject(time.first, time.second, getTextMessageFromSP(), getRingtoneFromSP())
    }

    fun saveTimeInSP(hourOfDay: Int, minutes: Int) {
        sharedPreferences[ConstantsPreferences.ALARM_HOURS.getKeyValue()] = hourOfDay
        sharedPreferences[ConstantsPreferences.ALARM_MINUTES.getKeyValue()] = minutes
    }

    fun getTimeFromSP(): Pair<Int, Int> =
            Pair(sharedPreferences[ConstantsPreferences.ALARM_HOURS.getKeyValue(), ConstantsPreferences.ALARM_HOURS.getDefaultIntValue()],
                    sharedPreferences[ConstantsPreferences.ALARM_MINUTES.getKeyValue(), ConstantsPreferences.ALARM_MINUTES.getDefaultIntValue()])

    fun saveRingtoneInSP(ringtoneName: String) {
        sharedPreferences[ConstantsPreferences.ALARM_RINGTONE_NAME.getKeyValue()] = ringtoneName

    }

    fun getDeepWakeUpStateFromSP(): Boolean =
            sharedPreferences[ConstantsPreferences.ALARM_DEEP_WAKE_UP_STATE.getKeyValue(), ConstantsPreferences.ALARM_DEEP_WAKE_UP_STATE.getDefaultIntValue() == 1]


    fun saveDeepWakeUpStateInSP(state: Boolean) {
        sharedPreferences[ConstantsPreferences.ALARM_DEEP_WAKE_UP_STATE.getKeyValue()] = state

    }

    fun saveDeepWakeUpRingtoneInSP(ringtoneName: String) {
        sharedPreferences[ConstantsPreferences.ALARM_DEEP_WAKE_UP_RINGTONE.getKeyValue()] = ringtoneName

    }

    fun getRingtoneFromSP(): String =
            sharedPreferences[ConstantsPreferences.ALARM_RINGTONE_NAME.getKeyValue(),
                    ConstantsPreferences.ALARM_RINGTONE_NAME.defaultRingtoneName]

    open fun getTextMessageFromSP(): String = sharedPreferences[ConstantsPreferences.ALARM_TEXT_MESSAGE.getKeyValue(),
            ConstantsPreferences.ALARM_TEXT_MESSAGE.defaultTextMessage]

    open fun getRingtonesForPopulation(): ArrayList<RingtoneObject> {
        val ringtonesList = ArrayList<RingtoneObject>()
        ringtonesList.add(RingtoneObject("mechanic_clock", 2))
        ringtonesList.add(RingtoneObject("energy", 1))
        ringtonesList.add(RingtoneObject("loud", 3))
        ringtonesList.add(RingtoneObject("digital_clock", 4))
        ringtonesList.add(RingtoneObject("calm", 5))
        ringtonesList.addAll(ringtoneManagerHelper.getDefaultAlarmRingtonesList())
        return ringtonesList
    }

    open fun getSavedRingtoneAlarmOb(ringtoneList: ArrayList<RingtoneObject> = getRingtonesForPopulation()): RingtoneObject {
        val ringtoneName: String = getRingtoneFromSP()
        return ringtoneList.find { ao -> ao.name == ringtoneName }
                ?: ringtoneList[1]
    }

    fun getSavedDeepWakeUpRingtoneOb(): RingtoneObject {
        val ringtoneList = getRingtonesForPopulation()
        val ringtoneName = sharedPreferences[ConstantsPreferences.ALARM_DEEP_WAKE_UP_RINGTONE.getKeyValue(), ConstantsPreferences.ALARM_DEEP_WAKE_UP_RINGTONE.defaultDeepWakeUpRingtone]
        return ringtoneList.find { ao -> ao.name == ringtoneName }
                ?: RingtoneObject("calm", 5)
    }

    fun isFirstAlarmSaving() = sharedPreferences[ConstantsPreferences.ALARM_HOURS.getKeyValue(), ConstantsPreferences.ALARM_HOURS.emptyPreferencesValue] ==
            ConstantsPreferences.ALARM_HOURS.emptyPreferencesValue

    fun saveIsTaskExplanationShow(isShow: Boolean) {
        sharedPreferences[ConstantsPreferences.TASKS_EXPLANATION_SHOW_STATE.getKeyValue()] = isShow
    }

    fun getIsTaskExplanationShow(): Boolean = sharedPreferences[ConstantsPreferences.TASKS_EXPLANATION_SHOW_STATE.getKeyValue(), ConstantsPreferences.TASKS_EXPLANATION_SHOW_STATE.getDefaultIntValue() == 1]
}
package com.mrkostua.mathalarm.testingTools

import android.content.SharedPreferences
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneManagerHelper
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneObject
import com.mrkostua.mathalarm.data.AlarmDataHelper
import org.mockito.Mockito.mock

/**
 * @author Kostiantyn Prysiazhnyi on 5/15/2018.
 */
class AlarmDataHelperStub(private val ringtonesList: ArrayList<RingtoneObject>, private val lastSavedRingtoneIndex: Int) : AlarmDataHelper(mSharedPreferences, mRingtoneManagerHelper) {
    companion object {
        private val mRingtoneManagerHelper = mock(RingtoneManagerHelper::class.java)
        private val mSharedPreferences = mock(SharedPreferences::class.java)

    }

    override fun getRingtonesForPopulation(): ArrayList<RingtoneObject> {
        return ringtonesList
    }

    override fun getSavedRingtoneAlarmOb(ringtoneList: ArrayList<RingtoneObject>): RingtoneObject {
        return ringtonesList[lastSavedRingtoneIndex]
    }

    override fun getTextMessageFromSP(): String {
        return ""
    }

}
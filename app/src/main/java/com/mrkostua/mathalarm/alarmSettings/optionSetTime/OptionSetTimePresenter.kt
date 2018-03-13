package com.mrkostua.mathalarm.alarmSettings.optionSetTime

import com.mrkostua.mathalarm.data.AlarmDataHelper
import com.mrkostua.mathalarm.tools.TimeToAlarmStart

/**
 * @author Kostiantyn Prysiazhnyi on 3/12/2018.
 */

class OptionSetTimePresenter(private val optionSetTimeView: OptionSetTimeContract.View,
                             private val alarmDataHelper: AlarmDataHelper) : OptionSetTimeContract.Presenter {
    private val countsTimeToAlarmStart = TimeToAlarmStart()

    init {
        optionSetTimeView.presenter = this
    }

    override fun start() {

    }

    override fun getTimeUntilAlarmBoom(hourOfDay: Int, minutes: Int): Pair<Int, Int> {
        return countsTimeToAlarmStart.howMuchTimeToStart(hourOfDay, minutes)

    }

    override fun saveTime(hourOfDay: Int, minutes: Int) {
        optionSetTimeView.showTimeUntilAlarmBoom(hourOfDay, minutes)
        alarmDataHelper.saveAlarmTimeInSP(hourOfDay, minutes)

    }

    override fun getSavedHour(): Int =
            alarmDataHelper.getAlarmTimeFromSP().first


    override fun getSavedMinute(): Int =
            alarmDataHelper.getAlarmTimeFromSP().second


}

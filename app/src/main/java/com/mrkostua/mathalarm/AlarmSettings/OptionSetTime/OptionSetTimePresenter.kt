package com.mrkostua.mathalarm.AlarmSettings.OptionSetTime

import com.mrkostua.mathalarm.Alarms.MathAlarm.data.AlarmDataHelper

/**
 * @author Kostiantyn Prysiazhnyi on 3/12/2018.
 */

class OptionSetTimePresenter(private val optionSetTimeView: OptionSetTimeContract.View,
                             private val alarmDataHelper: AlarmDataHelper) : OptionSetTimeContract.Presenter {

    init {
        optionSetTimeView.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveTime(hourOfDay: Int, minutes: Int) {
        alarmDataHelper.saveTimeInSP(hourOfDay, minutes)

    }

    override fun getSavedHour(): Int =
             alarmDataHelper.getTimeFromSP().first


    override fun getSavedMinute(): Int =
             alarmDataHelper.getTimeFromSP().second


}

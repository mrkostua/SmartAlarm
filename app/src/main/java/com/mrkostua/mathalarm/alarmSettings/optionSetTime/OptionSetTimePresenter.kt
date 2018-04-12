package com.mrkostua.mathalarm.alarmSettings.optionSetTime

import com.mrkostua.mathalarm.data.AlarmDataHelper
import com.mrkostua.mathalarm.tools.AlarmTools
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 3/12/2018.
 */

class OptionSetTimePresenter @Inject constructor(private val alarmDataHelper: AlarmDataHelper) : OptionSetTimeContract.Presenter {
    private lateinit var optionSetTimeView: OptionSetTimeContract.View

    override fun takeView(view: OptionSetTimeContract.View) {
        optionSetTimeView = view
    }

    override fun start() {

    }

    override fun getTimeUntilAlarmBoom(hourOfDay: Int, minutes: Int): Pair<Int, Int> {
        return AlarmTools.getTimeToAlarmStart(hourOfDay, minutes)

    }

    override fun saveTime(hourOfDay: Int, minutes: Int) {
        optionSetTimeView.showTimeUntilAlarmBoom(hourOfDay, minutes)
        alarmDataHelper.saveTimeInSP(hourOfDay, minutes)

    }

    override fun getSavedHour(): Int =
            alarmDataHelper.getTimeFromSP().first


    override fun getSavedMinute(): Int =
            alarmDataHelper.getTimeFromSP().second


}

package com.mrkostua.mathalarm.alarmSettings.optionSetTime

import com.mrkostua.mathalarm.Interfaces.BasePresenter
import com.mrkostua.mathalarm.Interfaces.BaseView

/**
 * @author Kostiantyn Prysiazhnyi on 3/12/2018.
 */
interface OptionSetTimeContract {
    interface View : BaseView<Presenter> {
        fun initializeTimePicker(hourOfDay: Int, minutes: Int)
        fun showTimeUntilAlarmBoom(hourOfDay: Int, minutes: Int)
    }

    interface Presenter : BasePresenter<View> {
        fun saveTime(hourOfDay: Int, minutes: Int)
        fun getSavedHour(): Int
        fun getSavedMinute(): Int
        fun getTimeUntilAlarmBoom(hourOfDay: Int, minutes: Int): Pair<Int, Int>
    }

}
package com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.mrkostua.mathalarm.data.AlarmDataHelper
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 3/21/2018.
 */
/**
 * In other words, this means that a ViewModel will not be destroyed if its owner is destroyed for a
 * configuration change (e.g. rotation). The new instance of the owner will just re-connected to the existing ViewModel.
 * The purpose of the ViewModel is to acquire and keep the information that is necessary for an Activity or a Fragment.
 */
class MainAlarmViewModel @Inject constructor(private val dataHelper: AlarmDataHelper) : ViewModel() {
    val alarmTime = ObservableField<String>()

    init {
        start()
    }

    private fun start() {
        val time = dataHelper.getAlarmTimeFromSP()
        alarmTime.set(Integer.toString(time.first) + " : " + Integer.toString(time.second))
    }

}
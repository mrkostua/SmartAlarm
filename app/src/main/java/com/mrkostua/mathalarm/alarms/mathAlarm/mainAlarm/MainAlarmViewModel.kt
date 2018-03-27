package com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.mrkostua.mathalarm.data.AlarmDataHelper
import com.mrkostua.mathalarm.tools.ShowLogs
import java.util.*
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
    private val TAG = this.javaClass.simpleName
    val alarmTime = ObservableField<String>()
    val isDarkTime = ObservableBoolean()
    private val calendar = Calendar.getInstance()

    init {
        start()
    }

    private fun start() {
        val time = dataHelper.getTimeFromSP()
        alarmTime.set(Integer.toString(time.first) + " : " + Integer.toString(time.second))
        calendar.timeInMillis = System.currentTimeMillis()
        setDayOrNight()
    }

    private fun setDayOrNight() {
        isDarkTime.set(calendar.get(Calendar.HOUR_OF_DAY) !in 6 until 20)
        ShowLogs.log(TAG, "setDayOrNight : " + isDarkTime.get())

    }

    fun getCurrentDayOfWeek(): Int {
        return calendar.get(Calendar.DAY_OF_WEEK) - 1

    }

    fun isFirstAlarmCreation() = dataHelper.isFirstAlarmSaving()

    fun getAlarmDataObject() = dataHelper.getAlarmDataObject()
}
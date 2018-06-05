package com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.mrkostua.mathalarm.data.AlarmDataHelper
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 3/29/2018.
 */
class DisplayAlarmViewModel @Inject constructor(private val dataHelper: AlarmDataHelper) : ViewModel() {
    val userTextMessage: String = "\"" + dataHelper.getTextMessageFromSP() + "\""
    val isShowExplanationDialog = ObservableBoolean()

    init {
        isShowExplanationDialog.set(dataHelper.getIsTaskExplanationShow())
    }

    fun setIsShowExplanationDialog(state : Boolean){
        dataHelper.saveIsTaskExplanationShow(state)
    }
}
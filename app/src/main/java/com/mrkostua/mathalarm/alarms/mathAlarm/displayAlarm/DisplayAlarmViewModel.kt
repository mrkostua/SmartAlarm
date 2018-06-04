package com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm

import android.arch.lifecycle.ViewModel
import com.mrkostua.mathalarm.data.AlarmDataHelper
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 3/29/2018.
 */
class DisplayAlarmViewModel @Inject constructor(dataHelper: AlarmDataHelper) : ViewModel() {
    val userTextMessage: String = "\"" + dataHelper.getTextMessageFromSP() + "\""
}
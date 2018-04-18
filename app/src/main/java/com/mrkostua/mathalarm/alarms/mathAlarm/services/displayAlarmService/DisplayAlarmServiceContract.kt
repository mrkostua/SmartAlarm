package com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService

import com.mrkostua.mathalarm.tools.BaseMediaContract

/**
 * @author Kostiantyn Prysiazhnyi on 4/2/2018.
 */
interface DisplayAlarmServiceContract {
    interface Presenter : BaseMediaContract {
        fun playDeepWakeUpRingtone()
        fun getDeepWakeUpState() : Boolean
        fun start()
    }
}
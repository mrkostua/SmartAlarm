package com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService

/**
 * @author Kostiantyn Prysiazhnyi on 4/2/2018.
 */
interface DisplayAlarmServiceContract {
    interface Presenter {
        fun playAlarmRingtone()
        fun stopAlarmRingtone()
        fun releaseObjects()

    }
}
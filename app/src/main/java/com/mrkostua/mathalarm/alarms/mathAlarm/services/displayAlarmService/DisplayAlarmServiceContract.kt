package com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService

import com.mrkostua.mathalarm.Interfaces.BasePresenter
import com.mrkostua.mathalarm.Interfaces.BaseView

/**
 * @author Kostiantyn Prysiazhnyi on 4/2/2018.
 */
interface DisplayAlarmServiceContract {
    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter<View> {
        fun playAlarmRingtone()
        fun stopAlarmRingtone()
    }
}
package com.mrkostua.mathalarm.alarmSettings.optionDeepSleepMusic

import com.mrkostua.mathalarm.Interfaces.BasePresenter
import com.mrkostua.mathalarm.Interfaces.BaseView

/**
 * @author Kostiantyn Prysiazhnyi on 4/12/2018.
 */
interface OptionSetDeepWakeUpContract {
    interface View : BaseView<Presenter> {
    }

    interface Presenter : BasePresenter<View> {
        fun saveStateInSP(isChecked : Boolean)
        fun playRingtone()
        fun stopPlayingRingtone()
    }
}
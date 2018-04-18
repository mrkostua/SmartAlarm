package com.mrkostua.mathalarm.alarmSettings.optionDeepWakeUp

import com.mrkostua.mathalarm.tools.BaseMediaContract

/**
 * @author Kostiantyn Prysiazhnyi on 4/12/2018.
 */
interface OptionSetDeepWakeUpContract {

    interface Presenter : BaseMediaContract {
        fun saveStateInSP(isChecked : Boolean)
        fun getStateFromSP() : Boolean
        fun getDeepWakeUpRingtoneName() : String
        fun start()
    }
}
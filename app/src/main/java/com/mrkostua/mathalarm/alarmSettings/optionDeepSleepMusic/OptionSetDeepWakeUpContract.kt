package com.mrkostua.mathalarm.alarmSettings.optionDeepSleepMusic

/**
 * @author Kostiantyn Prysiazhnyi on 4/12/2018.
 */
interface OptionSetDeepWakeUpContract {

    interface Presenter {
        fun saveStateInSP(isChecked : Boolean)
        fun getStateFromSP() : Boolean
        fun playRingtone()
        fun stopPlayingRingtone()
        fun getDeepWakeUpRingtoneName() : String
    }
}
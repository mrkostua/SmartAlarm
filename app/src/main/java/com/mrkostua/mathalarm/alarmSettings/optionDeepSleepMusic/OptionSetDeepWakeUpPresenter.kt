package com.mrkostua.mathalarm.alarmSettings.optionDeepSleepMusic

import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.MediaPlayerHelper
import com.mrkostua.mathalarm.data.AlarmDataHelper
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 4/12/2018.
 */
class OptionSetDeepWakeUpPresenter @Inject constructor(private val dataHelper: AlarmDataHelper, private val mediaPlayer: MediaPlayerHelper) : OptionSetDeepWakeUpContract.Presenter {
    override fun playRingtone() {
        //TODO for playing deepWakeUp ringtone we need to slowly increase music volume -> it need to be implemented in here and Service during Alarm boom
        mediaPlayer.playRingtoneFromRingtoneOb(dataHelper.getSavedRingtoneAlarmObject())

    }

    override fun stopPlayingRingtone() {
        mediaPlayer.stopRingtone()
    }

    override fun getDeepWakeUpRingtoneName() =
            dataHelper.getRingtoneFromSP()

    override fun saveStateInSP(isChecked: Boolean) {
        dataHelper.saveDeepWakeUpStateInSP(isChecked)
    }

    override fun getStateFromSP(): Boolean = dataHelper.getDeepWakeUpStateFromSP()

}
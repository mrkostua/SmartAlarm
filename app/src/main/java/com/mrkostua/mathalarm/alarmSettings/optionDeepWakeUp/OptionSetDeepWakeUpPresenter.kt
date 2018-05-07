package com.mrkostua.mathalarm.alarmSettings.optionDeepWakeUp

import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.MediaPlayerHelper
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneObject
import com.mrkostua.mathalarm.data.AlarmDataHelper
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 4/12/2018.
 */
class OptionSetDeepWakeUpPresenter @Inject constructor(private val dataHelper: AlarmDataHelper, private val mediaPlayer: MediaPlayerHelper) : OptionSetDeepWakeUpContract.Presenter {
    private lateinit var ringtoneObject: RingtoneObject

    override fun start() {
        ringtoneObject = dataHelper.getSavedDeepWakeUpRingtoneOb()
    }

    override fun playRingtone() {
        mediaPlayer.playDeepWakeUpRingtone(ringtoneObject)
    }

    override fun stopPlayingRingtone() {
        mediaPlayer.stopPlaying()
    }

    override fun getDeepWakeUpRingtoneName() = ringtoneObject.name

    override fun saveStateInSP(isChecked: Boolean) {
        dataHelper.saveDeepWakeUpStateInSP(isChecked)
    }

    override fun getStateFromSP(): Boolean = dataHelper.getDeepWakeUpStateFromSP()

    override fun releaseObjects() {
        mediaPlayer.releaseMediaPlayer()
    }
}
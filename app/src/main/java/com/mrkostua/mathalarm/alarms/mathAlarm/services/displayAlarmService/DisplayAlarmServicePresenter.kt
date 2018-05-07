package com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService

import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.MediaPlayerHelper
import com.mrkostua.mathalarm.data.AlarmDataHelper
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 4/2/2018.
 */

class DisplayAlarmServicePresenter @Inject constructor(private val dataHelper: AlarmDataHelper, private val mediaPlayer: MediaPlayerHelper) : DisplayAlarmServiceContract.Presenter {
    override fun start() {
    }

    override fun playRingtone() {
        mediaPlayer.playRingtone(dataHelper.getSavedRingtoneAlarmOb(), true)
    }

    override fun getDeepWakeUpState() = dataHelper.getDeepWakeUpStateFromSP()

    override fun playDeepWakeUpRingtone() {
        mediaPlayer.playDeepWakeUpRingtone(dataHelper.getSavedDeepWakeUpRingtoneOb())
    }

    override fun stopPlayingRingtone() {
        mediaPlayer.stopPlaying()

    }

    override fun releaseObjects() {
        mediaPlayer.releaseMediaPlayer()
    }

}
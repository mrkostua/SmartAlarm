package com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService

import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.MediaPlayerHelper
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneObject
import com.mrkostua.mathalarm.data.AlarmDataHelper
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 4/2/2018.
 */

class DisplayAlarmServicePresenter @Inject constructor(private val dataHelper: AlarmDataHelper, private val mediaPlayer: MediaPlayerHelper) : DisplayAlarmServiceContract.Presenter {
    private val TAG = this.javaClass.simpleName
    override lateinit var ringtoneObject: RingtoneObject

    override fun start() {
        ringtoneObject = dataHelper.getSavedRingtoneAlarmObject()
    }

    override fun playRingtone() {
        mediaPlayer.playRingtoneFromRingtoneOb(ringtoneObject,true)
    }

    override fun getDeepWakeUpState() = dataHelper.getDeepWakeUpStateFromSP()

    override fun playDeepWakeUpRingtone() {
        mediaPlayer.playDeepWakeUpRingtone(ringtoneObject)
    }

    override fun stopPlayingRingtone() {
        mediaPlayer.stopRingtone()

    }

    override fun releaseObjects() {
        mediaPlayer.releaseMediaPlayer()
    }

}
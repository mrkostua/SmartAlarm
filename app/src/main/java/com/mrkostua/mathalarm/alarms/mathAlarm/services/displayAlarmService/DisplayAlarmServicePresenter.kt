package com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService

import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.MediaPlayerHelper
import com.mrkostua.mathalarm.data.AlarmDataHelper
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 4/2/2018.
 */

class DisplayAlarmServicePresenter @Inject constructor(private val dataHelper: AlarmDataHelper, private val mediaPlayer: MediaPlayerHelper) : DisplayAlarmServiceContract.Presenter {
    private val TAG = this.javaClass.simpleName

    override fun playAlarmRingtone() {
        val ringtoneOb = dataHelper.getSavedRingtoneAlarmObject()

        if (ringtoneOb.uri == null) {
            mediaPlayer.playRingtoneFromStringResource(ringtoneOb.name, true)

        } else {
            mediaPlayer.playRingtoneFromUri(ringtoneOb.uri, true)

        }

    }

    override fun stopAlarmRingtone() {
        mediaPlayer.stopRingtone()

    }

    override fun releaseObjects() {
        mediaPlayer.releaseMediaPlayer()
    }

}
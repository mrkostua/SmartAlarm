package com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService

import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.MediaPlayerHelper
import com.mrkostua.mathalarm.data.AlarmDataHelper
import com.mrkostua.mathalarm.tools.ShowLogs
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 4/2/2018.
 */

class DisplayAlarmServicePresenter @Inject constructor(private val dataHelper: AlarmDataHelper,
                                                       private val mediaPlayer: MediaPlayerHelper) : DisplayAlarmServiceContract.Presenter {
    private lateinit var view: DisplayAlarmServiceContract.View
    private val TAG = this.javaClass.simpleName

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun takeView(view: DisplayAlarmServiceContract.View) {
        this.view = view

    }

    override fun playAlarmRingtone() {
        val ringtoneOb = dataHelper.getSavedRingtoneAlarmObject()
        ShowLogs.log(TAG,"playAlarmRingtone ringtoneOb : " + ringtoneOb.name)

        if (ringtoneOb.uri == null) {
            mediaPlayer.playRingtoneFromStringResource(ringtoneOb.name,true)
        } else {
            mediaPlayer.playRingtoneFromUri(ringtoneOb.uri,true)
        }

    }

    override fun stopAlarmRingtone() {
        mediaPlayer.stopRingtone()

    }

    override fun releaseObjects() {
        mediaPlayer.releaseMediaPlayer()
    }

}
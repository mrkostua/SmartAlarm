package com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService

import android.media.MediaPlayer
import com.mrkostua.mathalarm.data.AlarmDataHelper
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 4/2/2018.
 */

class DisplayAlarmServicePresenter @Inject constructor(dataHelper: AlarmDataHelper,mediaPlayer: MediaPlayer) : DisplayAlarmServiceContract.Presenter {
    private lateinit var view : DisplayAlarmServiceContract.View

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun takeView(view: DisplayAlarmServiceContract.View) {
        this.view = view

    }

    override fun playAlarmRingtone() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stopAlarmRingtone() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
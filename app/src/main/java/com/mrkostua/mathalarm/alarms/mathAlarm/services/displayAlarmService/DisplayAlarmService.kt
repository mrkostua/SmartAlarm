package com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Handler
import android.os.IBinder
import android.os.Message
import com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm.DisplayAlarmActivity
import com.mrkostua.mathalarm.tools.ConstantValues
import com.mrkostua.mathalarm.tools.NotificationsTools
import dagger.android.DaggerService
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 4/1/2018.
 */

public class DisplayAlarmService : DaggerService(), DisplayAlarmServiceContract.View {
    private val TAG = this.javaClass.simpleName
    private val NOTIFICATION_ID = 2

    private val killerHandleServiceSilent = 1
    private val handler = CustomHandler()

    @Inject
    public lateinit var notificationsTools: NotificationsTools
    @Inject
    public lateinit var presenter: DisplayAlarmServiceContract.Presenter


    override fun onCreate() {
        super.onCreate()
        with(getSystemService(Context.AUDIO_SERVICE) as AudioManager) {
            //FLAG_SHOW_UI Show a toast containing the current volume.
            setStreamVolume(AudioManager.STREAM_ALARM, getStreamVolume(AudioManager.STREAM_ALARM), 0)
        }

        presenter.takeView(this)
        //TODO read more about static Handler etc. find proper solution

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startAlarmDisplayActivity()
        enableHandlerSilenceKiller()
        presenter.playAlarmRingtone()

        startForeground(NOTIFICATION_ID, notificationsTools.newNotification())
        return Service.START_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
        disableHandlerSilenceKiller()
        presenter.stopAlarmRingtone()
        presenter.releaseObjects()
        notificationsTools.cancelNotification(NOTIFICATION_ID)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun startAlarmDisplayActivity() {
        startActivity(Intent(this, DisplayAlarmActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    private fun enableHandlerSilenceKiller() {
        handler.sendMessageDelayed(handler.obtainMessage(killerHandleServiceSilent),
                ConstantValues.ALARM_TIMEOUT_MILLISECONDS)

    }

    private fun disableHandlerSilenceKiller() {
        handler.removeMessages(killerHandleServiceSilent)

    }

    private inner class CustomHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                killerHandleServiceSilent -> {
                    sendBroadcast(Intent(ConstantValues.SNOOZE_ACTION))
                    stopSelf()
                }
            }
        }
    }
}
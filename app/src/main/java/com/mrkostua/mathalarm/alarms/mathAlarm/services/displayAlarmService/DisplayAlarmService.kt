package com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Handler
import android.os.IBinder
import android.os.Message
import com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm.DisplayAlarmActivity
import com.mrkostua.mathalarm.alarms.mathAlarm.receivers.AlarmReceiver
import com.mrkostua.mathalarm.injections.scope.DisplayAlarmServiceScope
import com.mrkostua.mathalarm.tools.ConstantValues
import com.mrkostua.mathalarm.tools.NotificationsTools
import com.mrkostua.mathalarm.tools.ShowLogs
import dagger.android.DaggerService
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 4/1/2018.
 */

//TODO read more about static Handler etc. find proper solution
@DisplayAlarmServiceScope
public class DisplayAlarmService : DaggerService() {
    private val TAG = this.javaClass.simpleName
    private val notificationId = 2
    private val killerHandleServiceSilent = 1
    private val handler = CustomHandler()

    @Inject
    public lateinit var notificationsTools: NotificationsTools
    @Inject
    public lateinit var presenter: DisplayAlarmServiceContract.Presenter

    override fun onCreate() {
        super.onCreate()
        setStreamVolume()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startAlarmDisplayActivity()
        enableHandlerSilenceKiller()
        presenter.playAlarmRingtone()

        startForeground(notificationId, notificationsTools.newNotification())
        return Service.START_STICKY
    }


    override fun onDestroy() {
        ShowLogs.log(TAG, "onDestroy")
        super.onDestroy()
        disableHandlerSilenceKiller()
        presenter.stopAlarmRingtone()
        presenter.releaseObjects()
        notificationsTools.cancelNotification(notificationId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun setStreamVolume() {
        with(getSystemService(Context.AUDIO_SERVICE) as AudioManager) {
            //FLAG_SHOW_UI Show a toast containing the current volume.
            setStreamVolume(AudioManager.STREAM_ALARM, getStreamVolume(AudioManager.STREAM_ALARM), 0)
        }
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
                    ShowLogs.log(TAG, "handleMessage : stop")
                    snoozeAlarm()
                    stopSelf()
                }
            }
        }
    }

    private fun snoozeAlarm() {
        sendBroadcast(Intent(ConstantValues.SNOOZE_ACTION)
                .setClass(this, AlarmReceiver::class.java))
    }
}
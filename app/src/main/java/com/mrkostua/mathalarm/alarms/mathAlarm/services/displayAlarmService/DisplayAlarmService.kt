package com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm.DisplayAlarmActivity
import com.mrkostua.mathalarm.alarms.mathAlarm.receivers.AlarmReceiver
import com.mrkostua.mathalarm.injections.scope.DisplayAlarmServiceScope
import com.mrkostua.mathalarm.tools.ConstantValues
import com.mrkostua.mathalarm.tools.NotificationTools
import com.mrkostua.mathalarm.tools.ShowLogs
import dagger.android.DaggerService
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 4/1/2018.
 */
@DisplayAlarmServiceScope
public class DisplayAlarmService : DaggerService() {
    private val TAG = this.javaClass.simpleName
    private val notificationId = 2
    private val handleServiceSilent = 1
    private val handleDeepWakeUpFinishedPlaying = 2
    private val handler = CustomHandler()

    @Inject
    public lateinit var notificationsTools: NotificationTools
    @Inject
    public lateinit var presenter: DisplayAlarmServiceContract.Presenter

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startAlarmDisplayActivity()
        playRingtone()

        startForeground(notificationId, notificationsTools.snoozeNotification())
        return Service.START_STICKY
    }

    override fun onDestroy() {
        ShowLogs.log(TAG, "onDestroy")
        super.onDestroy()
        disableHandlerSilenceKiller(handleServiceSilent)
        disableHandlerSilenceKiller(handleDeepWakeUpFinishedPlaying)
        presenter.stopPlayingRingtone()
        presenter.releaseObjects()
        notificationsTools.cancelNotification(notificationId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun playRingtone() {
        if (presenter.getDeepWakeUpState()) {
            presenter.playDeepWakeUpRingtone()
            enableHandlerDeepWakeUp()
        } else {
            presenter.playRingtone()
            enableHandlerSilenceKiller()

        }
    }

    private fun startAlarmDisplayActivity() {
        startActivity(Intent(this, DisplayAlarmActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    private fun enableHandlerSilenceKiller() {
        handler.sendMessageDelayed(handler.obtainMessage(handleServiceSilent),
                ConstantValues.ALARM_TIMEOUT_MILLISECONDS)

    }

    private fun enableHandlerDeepWakeUp() {
        ShowLogs.log(TAG, "enableHandlerDeepWakeUp()")
        handler.sendMessageDelayed(handler.obtainMessage(handleDeepWakeUpFinishedPlaying),
                ConstantValues.ALARM_DEEP_WAKE_UP_TIMEOUT_MILLISECONDS)
    }

    private fun disableHandlerSilenceKiller(what: Int) {
        handler.removeMessages(what)

    }

    private inner class CustomHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                handleServiceSilent -> {    
                    ShowLogs.log(TAG, "handleMessage : stop")
                    snoozeAlarm()
                    stopSelf()
                }
                handleDeepWakeUpFinishedPlaying -> {
                    ShowLogs.log(TAG,"handleMessage -> handleDeepWakeUPFinishedPlaying()")
                    presenter.stopPlayingRingtone()
                    enableHandlerSilenceKiller()
                    presenter.playRingtone()
                }
            }
        }
    }

    private fun snoozeAlarm() {
        sendBroadcast(Intent(ConstantValues.SNOOZE_ACTION)
                .setClass(this, AlarmReceiver::class.java))
    }

}
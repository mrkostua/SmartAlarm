package com.mrkostua.mathalarm.alarms.mathAlarm.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.PowerManager
import com.mrkostua.mathalarm.tools.AlarmTools
import com.mrkostua.mathalarm.tools.ConstantValues
import com.mrkostua.mathalarm.tools.NotificationTools

/**
 * @author Kostiantyn Prysiazhnyi on 4/18/2018.
 */
class WakeLockService : Service() {
    private val notificationID = 25
    private val TAG = this.javaClass.simpleName
    private lateinit var partialWakeLock: PowerManager.WakeLock
    private lateinit var notificationTools: NotificationTools

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val alarmHour = intent?.getIntExtra(ConstantValues.WAKE_LOCK_HOUR_KEY, 23) ?: 23
        val alarmMin = intent?.getIntExtra(ConstantValues.WAKE_LOCK_MINUTE_KEY, 59) ?: 59
        val timeOut = intent?.getIntExtra(ConstantValues.WAKE_LOCK_TIMEOUT, 23 * 59) ?: 23*59
        createWakeLock().acquire((timeOut + 1) * 60 * 1000L)
        notificationTools = NotificationTools(this)
        startForeground(notificationID,
                notificationTools.alarmNotification(AlarmTools.getReadableTime(alarmHour, alarmMin)))

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        notificationTools.cancelNotification(notificationID)
        releaseWakeLock()
        stopSelf()
    }

    private fun releaseWakeLock() {
        if (partialWakeLock.isHeld) {
            partialWakeLock.release()

        }
    }

    private fun createWakeLock(): PowerManager.WakeLock {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        partialWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, TAG)
        return partialWakeLock

    }

    override fun onBind(intent: Intent?): IBinder? = null

}
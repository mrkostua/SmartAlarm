package com.mrkostua.mathalarm.tools

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.view.Gravity
import android.widget.Toast
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.alarms.mathAlarm.receivers.AlarmReceiver
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 4/12/2018.
 */
class NotificationTools @Inject constructor(private val context: Context) {
    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChanel()
        }
    }

    fun snoozeNotification(): Notification {
        val builder = NotificationCompat.Builder(context, ConstantValues.NOTIFICATION_CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentText(context.getString(R.string.AN_snoozeContentText))
                .setContentTitle(context.getString(R.string.AN_snoozeContentTitle))
                .setSmallIcon(R.drawable.ic_alarm_white_36dp)
                .setTicker(context.getString(R.string.AN_snoozeTicker))
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher))
                .setWhen(System.currentTimeMillis())
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(R.drawable.ic_snooze_white_36dp,
                        context.getString(R.string.AN_snoozeAction),
                        PendingIntent.getBroadcast(context, 0,
                                Intent(ConstantValues.SNOOZE_ACTION).setClass(context, AlarmReceiver::class.java),
                                0))
                .setAutoCancel(true)
        return builder.build()
    }

    fun alarmNotification(time: String): Notification {
        val builder = NotificationCompat.Builder(context, ConstantValues.NOTIFICATION_CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentText(context.getString(R.string.AN_dismissContentText, time))
                .setContentTitle(context.getString(R.string.AN_dismissContentTitle))
                .setSmallIcon(R.drawable.ic_alarm_white_36dp)
                .setTicker(context.getString(R.string.AN_dismissContentText, time))
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setWhen(System.currentTimeMillis())
                .addAction(R.drawable.ic_alarm_off_white_36dp,
                        context.getString(R.string.AN_dismissActionText),
                        PendingIntent.getBroadcast(context, 0,
                                Intent(ConstantValues.DISMISS_ACTION).setClass(context, AlarmReceiver::class.java),
                                0))
                .setAutoCancel(true)

        return builder.build()
    }

    fun cancelNotification(notificationID: Int) {
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(notificationID)
    }

    fun showToastMessage(messageText: String) {
        Toast.makeText(context, messageText, Toast.LENGTH_LONG).show()
    }

    fun showToastTimeToAlarmBoom(alarmHour: Int, alarmMinute: Int) {
        val (first, second) = AlarmTools.getTimeToAlarmStart(alarmHour, alarmMinute)
        val toastMessage = Toast.makeText(context,
                context.getString(R.string.MAP_Toast_timeLeftToAlarmStart,
                        AlarmTools.getReadableTime(first, second)), Toast.LENGTH_LONG)
        toastMessage.setGravity(Gravity.TOP or Gravity.START, 0, 0)
        toastMessage.show()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChanel() {
        val mChannel = NotificationChannel(ConstantValues.NOTIFICATION_CHANNEL_ID,
                ConstantValues.NOTIFICATION_CHANEL_NAME,
                NotificationManager.IMPORTANCE_HIGH)
        with(mChannel) {
            description = ConstantValues.NOTIFICATION_CHANEL_DESCRIPTION
            setShowBadge(false)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC

        }
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(mChannel)
    }
}
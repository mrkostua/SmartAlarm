package com.mrkostua.mathalarm.alarms.mathAlarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.mrkostua.mathalarm.alarms.mathAlarm.receivers.AlarmReceiver
import com.mrkostua.mathalarm.alarms.mathAlarm.services.WakeLockService
import com.mrkostua.mathalarm.tools.AlarmTools
import com.mrkostua.mathalarm.tools.ConstantValues
import com.mrkostua.mathalarm.tools.ShowLogs
import java.util.*

/**
 * @author Kostiantyn Prysiazhnyi on 4/11/2018.
 */
class AlarmManagerHelper constructor(private val context: Context) {
    private val TAG = this.javaClass.simpleName
    private val calendar = Calendar.getInstance()
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val newAlarmPendingIntent = PendingIntent.getBroadcast(context,
            0,
            Intent(ConstantValues.START_NEW_ALARM_ACTION).setClass(context, AlarmReceiver::class.java), 0)

    fun setNewAlarm(alarmObject: AlarmObject) {
        calendar.timeInMillis = System.currentTimeMillis()
        val currentHour = calendar[Calendar.HOUR_OF_DAY]
        val currentMinute = calendar[Calendar.MINUTE]

        val alarmHour = alarmObject.hours
        val alarmMin = alarmObject.minutes
        calendar.set(Calendar.HOUR_OF_DAY, alarmHour)
        calendar.set(Calendar.MINUTE, alarmMin)
        when {
            alarmHour > currentHour -> {
                ShowLogs.log(TAG, "h current: $currentHour alarm hour: $alarmHour  Today")
                ShowLogs.log(TAG, "min current: $currentMinute alarm min: $alarmMin  Today")

            }
            alarmHour < currentHour -> {
                ShowLogs.log(TAG, "h current: $currentHour  alarm hour: $alarmHour Next Day")
                ShowLogs.log(TAG, "min current: $currentMinute alarm min: $alarmMin  Next    Day")
                setCalendarDay(1)

            }
            alarmHour == currentHour -> {
                if (alarmMin < currentMinute) {
                    ShowLogs.log(TAG, "h current: $currentHour alarm hour: $alarmHour Next Day ")
                    ShowLogs.log(TAG, "min current: $currentMinute alarm min: $alarmMin  Next Day")
                    setCalendarDay(1)

                } else {
                    ShowLogs.log(TAG, "h current: $currentHour alarm hour: $alarmHour  Today")
                    ShowLogs.log(TAG, "min current: $currentMinute alarm min: $alarmMin  Today")

                }

            }
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, newAlarmPendingIntent)
        startWakeLockService(alarmHour, alarmMin)

    }

    fun cancelLastSetAlarm() {
        alarmManager.cancel(getCancelPendingIntent())

    }

    fun snoozeAlarm(snoozeMinutes: Int) {
        calendar.timeInMillis = System.currentTimeMillis() + snoozeMinutes * 60 * 1000
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                newAlarmPendingIntent)
        startWakeLockService(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))

    }

    private fun getCancelPendingIntent(): PendingIntent {
        return PendingIntent.getBroadcast(context,
                0,
                Intent(ConstantValues.START_NEW_ALARM_ACTION).setClass(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_CANCEL_CURRENT)
    }

    private fun startWakeLockService(alarmHour: Int, alarmMin: Int) {
        val timeout = AlarmTools.getTimeToAlarmStart(alarmHour, alarmMin)
        context.startService(Intent(context, WakeLockService::class.java)
                .putExtra(ConstantValues.WAKE_LOCK_HOUR_KEY, alarmHour)
                .putExtra(ConstantValues.WAKE_LOCK_MINUTE_KEY, alarmMin)
                .putExtra(ConstantValues.WAKE_LOCK_TIMEOUT, timeout.first * timeout.second))
    }

    private fun setCalendarDay(day: Int) {
        calendar.set(Calendar.DAY_OF_WEEK, if (calendar[Calendar.DAY_OF_WEEK] == Calendar.SATURDAY) {
            Calendar.SUNDAY
        } else {
            calendar[Calendar.DAY_OF_WEEK] + day
        })

    }
}
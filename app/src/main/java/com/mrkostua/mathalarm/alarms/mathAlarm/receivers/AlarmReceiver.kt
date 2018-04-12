package com.mrkostua.mathalarm.alarms.mathAlarm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mrkostua.mathalarm.alarms.mathAlarm.AlarmManagerHelper
import com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm.MainAlarmActivity
import com.mrkostua.mathalarm.alarms.mathAlarm.services.WakeLockService
import com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService.DisplayAlarmService
import com.mrkostua.mathalarm.tools.ConstantValues

/**
 * @author Kostiantyn Prysiazhnyi on 4/11/2018.
 */

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        when (intent?.action) {
            ConstantValues.SNOOZE_ACTION -> {
                snoozeAlarm(context)
                startMainActivity(context)

            }
            ConstantValues.DISMISS_ACTION -> {
                dismissAlarm(context)

            }
            ConstantValues.START_NEW_ALARM_ACTION -> {
                startDisplayService(context)

            }
            else -> {
                throw UnsupportedOperationException("no such intent action or intent is null")
            }
        }
    }

    private fun snoozeAlarm(context: Context) {
        context.stopService(Intent(context, DisplayAlarmService::class.java))
        AlarmManagerHelper(context).snoozeAlarm(ConstantValues.DEFAULT_SNOOZE_TIME_MIN)

    }

    private fun startMainActivity(context: Context) {
        context.startActivity(Intent(context, MainAlarmActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    private fun dismissAlarm(context: Context) {
        context.stopService(Intent(context, WakeLockService::class.java))
        AlarmManagerHelper(context).cancelLastSetAlarm()

    }

    private fun startDisplayService(context: Context) {
        context.startService(Intent(context, DisplayAlarmService::class.java))

    }
}
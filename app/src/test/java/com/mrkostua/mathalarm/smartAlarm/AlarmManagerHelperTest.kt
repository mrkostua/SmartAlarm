package com.mrkostua.mathalarm.smartAlarm

import android.app.ActivityManager
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import com.mrkostua.mathalarm.alarms.mathAlarm.AlarmManagerHelper
import com.mrkostua.mathalarm.alarms.mathAlarm.AlarmObject
import com.mrkostua.mathalarm.alarms.mathAlarm.receivers.AlarmReceiver
import com.mrkostua.mathalarm.alarms.mathAlarm.services.WakeLockService
import com.mrkostua.mathalarm.tools.ConstantValues
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowAlarmManager
import java.util.*

/**
 * @author Kostiantyn Prysiazhnyi on 5/2/2018.
 */
@RunWith(RobolectricTestRunner::class)
class AlarmManagerHelperTest {
    private lateinit var shadowAlarmManager: ShadowAlarmManager
    private lateinit var context: Context
    private lateinit var alarmManagerHelper: AlarmManagerHelper
    private lateinit var activityManager: ActivityManager

    @Before
    fun setUp() {
        context = RuntimeEnvironment.application.applicationContext
        shadowAlarmManager = shadowOf(RuntimeEnvironment.application.getSystemService(Context.ALARM_SERVICE) as AlarmManager)
        alarmManagerHelper = AlarmManagerHelper(context)
        activityManager = RuntimeEnvironment.application.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager


    }

    @Test
    fun setNewAlarmTest() {
        val alarmObject = AlarmObject(10, 20, "test message", "test ringtone")
        alarmManagerHelper.setNewAlarm(alarmObject)
        assertEquals("more than one alarm was scheduled", 1, shadowAlarmManager.scheduledAlarms.size)
        checkAlarmType()
        checkAlarmTime(alarmObject)
        checkAlarmIntent()
        checkIfWakeLockServiceRunning()

    }

    @Test
    fun cancelLastSetAlarm() {
        val penIntent = shadowOf(shadowAlarmManager.nextScheduledAlarm.operation)
        alarmManagerHelper.cancelLastSetAlarm()
        assertTrue("this pending intent wasn't canceled", penIntent.isCanceled)
        assertEquals("some alarm is still scheduled", 0, shadowAlarmManager.scheduledAlarms.size)


    }

    @Test
    fun snoozeAlarmTest() {
        //write this method in TDD style
        //1 writing simple test -> test fails
        //2 updating method in simplest way to pass the test -> test pass
        //3 making test harder -> test fails
        //go to point 2 (do until it until test is good enough)
        val snoozeTime = 5
        val expectedSnoozeTime = snoozeTime * 60 * 1000 + shadowAlarmManager.nextScheduledAlarm.alarmClockInfo.triggerTime
        alarmManagerHelper.snoozeAlarm(snoozeTime)
        assertEquals("triggerAtTime value is not as expected", expectedSnoozeTime, shadowAlarmManager.nextScheduledAlarm.triggerAtTime)

    }

    @Test
    fun checkIfOnlyOneAlarmScheduled() {
        val alarmObject = AlarmObject(2, 3, "", "")
        alarmManagerHelper.setNewAlarm(alarmObject)
        alarmManagerHelper.setNewAlarm(alarmObject)
        alarmManagerHelper.setNewAlarm(alarmObject)

        assertEquals("more than one alarm was scheduled", 1, shadowAlarmManager.scheduledAlarms.size)
    }


    private fun checkAlarmType() {
        assertNull("alarm is set before initializing new one", shadowAlarmManager.nextScheduledAlarm)
        val scheduledAlarm = shadowAlarmManager.nextScheduledAlarm
        assertEquals("the type of set alarm is incorrect", AlarmManager.RTC_WAKEUP, scheduledAlarm.type)

    }

    private fun checkAlarmTime(alarmObject: AlarmObject) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val currentHour = calendar[Calendar.HOUR_OF_DAY]
        val currentMinute = calendar[Calendar.MINUTE]
        with(calendar) {
            set(Calendar.HOUR_OF_DAY, alarmObject.hours)
            set(Calendar.MINUTE, alarmObject.minutes)
            if (alarmObject.hours < currentHour || alarmObject.hours == currentHour && alarmObject.minutes < currentMinute) {
                set(Calendar.DAY_OF_WEEK, calendar[Calendar.DAY_OF_WEEK] + 1)
            }
        }
        alarmManagerHelper.setNewAlarm(alarmObject)
        val scheduledAlarm = shadowAlarmManager.nextScheduledAlarm
        assertEquals("actual alarmManager trigger time is different from expected", calendar.timeInMillis, scheduledAlarm.triggerAtTime)

    }

    private fun checkAlarmIntent() {
        val expectedIntent = Intent(ConstantValues.START_NEW_ALARM_ACTION).setClass(context, AlarmReceiver::class.java)
        val pendingIntentShadow = shadowOf(shadowAlarmManager.nextScheduledAlarm.operation)

        assertTrue("pending intent is not broadcastIntent", pendingIntentShadow.isBroadcastIntent)
        assertEquals("only one intent is allowed", 1, pendingIntentShadow.savedIntents.size)
        assertEquals("actual intent action is wrong", expectedIntent.action, pendingIntentShadow.savedIntent.action)
        assertEquals("actual intent component is wrong", expectedIntent.component, pendingIntentShadow.savedIntent.component)
    }

    private fun checkIfWakeLockServiceRunning() {
        for (runningServiceInfo in activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (runningServiceInfo.service.className == WakeLockService::class.java.simpleName) {
                return
            }
        }
        fail("${WakeLockService::class.java.simpleName} isn't running")
    }


}
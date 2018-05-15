package com.mrkostua.mathalarm.smartAlarm

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
    private val alarmObject = AlarmObject(2, 20, "test message", "test ringtone")

    @Before
    fun setUp() {
        context = RuntimeEnvironment.application.applicationContext
        shadowAlarmManager = shadowOf(RuntimeEnvironment.application.getSystemService(Context.ALARM_SERVICE) as AlarmManager)
        alarmManagerHelper = AlarmManagerHelper(context)

    }

    @Test
    fun setNewAlarmTest() {
        assertNull("alarm is set before initializing new one", shadowAlarmManager.nextScheduledAlarm)
        alarmManagerHelper.setNewAlarm(alarmObject)
        assertEquals("more than one alarm was scheduled", 1, shadowAlarmManager.scheduledAlarms.size)

        checkAlarmType()
        checkAlarmTime(alarmObject)
        checkAlarmIntent()
        checkIfWakeLockServiceRunning()

    }

    @Test
    fun cancelLastSetAlarm() {
        alarmManagerHelper.setNewAlarm(alarmObject)
        val penIntent = shadowOf(shadowAlarmManager.nextScheduledAlarm.operation)
        alarmManagerHelper.cancelLastSetAlarm()
        assertTrue("this pending intent wasn't canceled", penIntent.isCanceled)
        assertEquals("some alarm is still scheduled", 0, shadowAlarmManager.scheduledAlarms.size)


    }

    @Test
    fun snoozeAlarmTest() {
        alarmManagerHelper.setNewAlarm(alarmObject)
        val snoozeTime = 5
        val expectedSnoozeTime = snoozeTime * 60 * 1000 + System.currentTimeMillis()
        alarmManagerHelper.snoozeAlarm(snoozeTime)
        assertEquals("triggerAtTime value is not as expected", expectedSnoozeTime, shadowAlarmManager.peekNextScheduledAlarm().triggerAtTime)

    }

    @Test
    fun checkIfOnlyOneAlarmScheduled() {
        val alarmObject = AlarmObject(2, 3, "", "")
        alarmManagerHelper.setNewAlarm(alarmObject)
        alarmManagerHelper.setNewAlarm(alarmObject)
        alarmManagerHelper.setNewAlarm(this.alarmObject)

        assertEquals("more than one alarm was scheduled", 1, shadowAlarmManager.scheduledAlarms.size)
    }

    private fun checkAlarmType() {
        val scheduledAlarm = shadowAlarmManager.peekNextScheduledAlarm()
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
            if (alarmObject.hours < currentHour || (alarmObject.hours == currentHour && alarmObject.minutes < currentMinute)) {
                set(Calendar.DAY_OF_WEEK, getSpecialCalendarDay(calendar))
            }

        }
        alarmManagerHelper.setNewAlarm(alarmObject)
        assertEquals("actual alarmManager trigger time is different from expected", calendar.timeInMillis,
                shadowAlarmManager.peekNextScheduledAlarm().triggerAtTime)

    }

    private fun checkAlarmIntent() {
        val expectedIntent = Intent(ConstantValues.START_NEW_ALARM_ACTION).setClass(context, AlarmReceiver::class.java)
        val pendingIntentShadow = shadowOf(shadowAlarmManager.peekNextScheduledAlarm().operation)

        assertTrue("pending intent is not broadcastIntent", pendingIntentShadow.isBroadcastIntent)
        assertEquals("only one intent is allowed", 1, pendingIntentShadow.savedIntents.size)
        assertEquals("actual intent action is wrong", expectedIntent.action, pendingIntentShadow.savedIntent.action)
        assertEquals("actual intent component is wrong", expectedIntent.component, pendingIntentShadow.savedIntent.component)
    }

    private fun checkIfWakeLockServiceRunning() {
        val expectedIntent = Intent(context, WakeLockService::class.java)
        assertEquals("wakeLockService wasn't started", expectedIntent.component, shadowOf(RuntimeEnvironment.application).peekNextStartedService().component)

    }

    private fun getSpecialCalendarDay(calendar: Calendar): Int =
            if (calendar[Calendar.DAY_OF_WEEK] == Calendar.SATURDAY) {
                Calendar.SUNDAY
            } else {
                calendar[Calendar.DAY_OF_WEEK] + 1
            }
}
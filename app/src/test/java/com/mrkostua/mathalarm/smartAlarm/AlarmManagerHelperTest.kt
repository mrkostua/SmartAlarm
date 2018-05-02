package com.mrkostua.mathalarm.smartAlarm

import android.app.AlarmManager
import android.content.Context
import com.mrkostua.mathalarm.alarms.mathAlarm.AlarmManagerHelper
import com.mrkostua.mathalarm.alarms.mathAlarm.AlarmObject
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
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
    private lateinit var alarmManager: AlarmManager
    private lateinit var shadowAlarmManager: ShadowAlarmManager
    private lateinit var context: Context
    private lateinit var alarmManagerHelper: AlarmManagerHelper
    private lateinit var alarmObject: AlarmObject

    @Before
    fun setUp() {
        context = RuntimeEnvironment.application.applicationContext
        alarmManager = RuntimeEnvironment.application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        shadowAlarmManager = shadowOf(alarmManager)
        alarmManagerHelper = AlarmManagerHelper(context)
        alarmObject = AlarmObject(10, 20, "test message", "test ringtone")

    }

    @Test
    fun checkAlarmTypeTest() {
        assertNull("alarm is set before setting", shadowAlarmManager.nextScheduledAlarm)
        alarmManagerHelper.setNewAlarm(alarmObject)

        val scheduledAlarm = shadowAlarmManager.nextScheduledAlarm
        assertEquals("the type of set alarm is incorrect", AlarmManager.RTC_WAKEUP, scheduledAlarm.type)

    }

    @Test
    fun checkAlarmTimeTest() {
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

    @Test
    fun checkIfOnlyOneAlarmScheduledTest() {
        alarmManagerHelper.setNewAlarm(alarmObject)
        alarmManagerHelper.setNewAlarm(alarmObject)
        alarmManagerHelper.setNewAlarm(alarmObject)

        assertEquals("more than one alarm was scheduled", 1, shadowAlarmManager.scheduledAlarms.size)
    }

}
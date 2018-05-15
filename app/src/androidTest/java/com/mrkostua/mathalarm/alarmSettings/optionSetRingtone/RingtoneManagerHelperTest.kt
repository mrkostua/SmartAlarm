package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import android.content.Context
import android.media.RingtoneManager
import android.support.test.filters.MediumTest
import android.support.test.rule.ActivityTestRule
import com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm.MainAlarmActivity
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author Kostiantyn Prysiazhnyi on 4/26/2018.
 */
@MediumTest
class RingtoneManagerHelperTest {
    @Rule
    @JvmField
    val alarmMainAlarmActivityTestRule = ActivityTestRule<MainAlarmActivity>(MainAlarmActivity::class.java)
    private lateinit var context: Context
    private lateinit var ringtoneManagerHelper: RingtoneManagerHelper
    @Before
    fun setUp() {
        context = alarmMainAlarmActivityTestRule.activity.applicationContext
        ringtoneManagerHelper = RingtoneManagerHelper(context)
    }

    @Test
    fun getDefaultAlarmRingtonesList() {
        val ringtoneManager = RingtoneManager(context)
        ringtoneManager.setType(RingtoneManager.TYPE_ALARM)
        val ringtonesCursor = ringtoneManager.cursor

        val actualList = ringtoneManagerHelper.getDefaultAlarmRingtonesList()
        if (ringtonesCursor.count == 0) {
            assertTrue(actualList.isEmpty())
        } else {
            assertFalse(actualList.isEmpty())
        }
    }
}
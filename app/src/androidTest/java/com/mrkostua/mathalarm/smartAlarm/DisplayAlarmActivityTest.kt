package com.mrkostua.mathalarm.smartAlarm

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.longClick
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.MediumTest
import android.support.test.rule.ActivityTestRule
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.SmartAlarmApp
import com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm.MainAlarmActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author Kostiantyn Prysiazhnyi on 6/2/2018.
 */
@MediumTest
class DisplayAlarmActivityTest {
    private val TAG = this.javaClass.simpleName
    @Rule
    @JvmField
    val mainAlarmActivityTestRule = ActivityTestRule<MainAlarmActivity>(MainAlarmActivity::class.java)
    private lateinit var context: Context
    @Before
    fun setUp() {
        context = InstrumentationRegistry.getTargetContext().applicationContext as SmartAlarmApp
    }

    /**
     * to run this test first change the clickListener method body of ibAdditionalSettings to
     * start DisplayAlarmActivity and bSnoozeAlarm to start MainAlarmActivity
     */
    @Test
    fun testing() {
        for (i in 0..20) {
            onView(withId(R.id.ibAdditionalSettings)).perform(click())
            Thread.sleep(1000)
            onView(withId(R.id.bSnoozeAlarm)).perform(longClick())

        }
    }
}
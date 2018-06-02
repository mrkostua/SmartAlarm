package com.mrkostua.mathalarm.smartAlarm

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.MediumTest
import android.support.test.rule.ActivityTestRule
import com.mrkostua.mathalarm.SmartAlarmApp
import com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm.DisplayAlarmActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author Kostiantyn Prysiazhnyi on 6/2/2018.
 */
@MediumTest
class DisplayAlarmActivityTest {
    @Rule
    @JvmField
    val displayAlarmActivityTestRule = ActivityTestRule<DisplayAlarmActivity>(DisplayAlarmActivity::class.java)
    private lateinit var context: Context
    @Before
    fun setUp() {
        context = InstrumentationRegistry.getTargetContext().applicationContext as SmartAlarmApp
    }

    @Test
    fun getTaskView() {
        for (index in 0..4) {
            onView(withId(index)).check(matches(isDisplayed()))
        }
        Thread.sleep(10000)
    }
}
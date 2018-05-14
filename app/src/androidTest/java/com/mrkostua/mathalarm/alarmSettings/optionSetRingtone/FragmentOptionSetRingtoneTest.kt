package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.rule.ActivityTestRule
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.SmartAlarmApp
import com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm.MainAlarmActivity
import com.mrkostua.mathalarm.data.AlarmDataHelper
import com.mrkostua.mathalarm.esspressoTools.AdditionalViewActions
import com.mrkostua.mathalarm.esspressoTools.AdditionalViewMatcher
import com.mrkostua.mathalarm.tools.ConstantsPreferences
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author Kostiantyn Prysiazhnyi on 5/11/2018.
 */
@MediumTest
public class FragmentOptionSetRingtoneTest {
    @Rule
    @JvmField
    val alarmMainAlarmActivityTestRule = ActivityTestRule<MainAlarmActivity>(MainAlarmActivity::class.java)

    private lateinit var dataHelper: AlarmDataHelper
    private lateinit var expectedRingtones: ArrayList<RingtoneObject>

    @Before
    fun setUp() {
        val app = InstrumentationRegistry.getTargetContext().applicationContext as SmartAlarmApp
        dataHelper = AlarmDataHelper(app.getSharedPreferences(ConstantsPreferences.ALARM_SP_NAME.getKeyValue(), Context.MODE_PRIVATE),
                RingtoneManagerHelper(app.applicationContext))
        expectedRingtones = dataHelper.getRingtonesForPopulation()
        onView(withId(R.id.ibAdditionalSettings)).perform(click())
        onView(withId(R.id.ibMoveForward)).perform(click())

    }

    @Test
    fun checkDisplayedContent() {
        expectedRingtones.forEachIndexed { position, it ->
            onView(withId(R.id.rvListOfRingtones))
                    .perform(scrollToPosition<RingtonesRecycleViewAdapter.RingtonesViewHolder>(position))
                    .check(ViewAssertions.matches(isDisplayed()))
        }
        //TODO maybe update MediaPlayerTest andrTest to use RecycleViewActions instead of onData
    }

    @Test
    fun onlyOneRingtoneCanBeChecked() {
        val checkedItem = 10
        for (i in 1..checkedItem) {
            onView(withId(R.id.rvListOfRingtones))
                    .perform(RecyclerViewActions.actionOnItemAtPosition<RingtonesRecycleViewAdapter.RingtonesViewHolder>(i, AdditionalViewActions.clickChildView(R.id.cbChosenAlarmRingtone)))
        }

        expectedRingtones.forEachIndexed { index, ringtoneObject ->
            println("number : $index")
            if (index == checkedItem) {
                onView(withId(R.id.rvListOfRingtones)).perform(scrollToPosition<RingtonesRecycleViewAdapter.RingtonesViewHolder>(index)).perform(click())
                        .check(matches(AdditionalViewMatcher.atPosition(index, hasDescendant(isChecked()))))
            } else {
                onView(withId(R.id.rvListOfRingtones)).perform(scrollToPosition<RingtonesRecycleViewAdapter.RingtonesViewHolder>(index)).perform(click())
                        .check(matches(AdditionalViewMatcher.atPosition(index, hasDescendant(isNotChecked()))))
            }

        }


    }

    private fun playSomeRingtone(someRingtone: String = "ringtone_energy") {
        onData(withText(someRingtone))
        onView(allOf(withId(R.id.ibPlayPauseRingtone),
                hasSibling(withText(someRingtone))))
                .perform(click())
    }

}
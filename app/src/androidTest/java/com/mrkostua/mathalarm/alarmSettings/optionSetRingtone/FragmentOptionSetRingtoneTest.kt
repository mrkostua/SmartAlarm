package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import android.content.Context
import android.support.test.InstrumentationRegistry
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author Kostiantyn Prysiazhnyi on 5/11/2018.
 */
@MediumTest
class FragmentOptionSetRingtoneTest {
    @Rule
    @JvmField
    val alarmMainAlarmActivityTestRule = ActivityTestRule<MainAlarmActivity>(MainAlarmActivity::class.java)

    private lateinit var dataHelper: AlarmDataHelper
    private lateinit var expectedRingtones: ArrayList<RingtoneObject>
    private lateinit var context: Context

    @Before
    fun setUp() {
        val app = InstrumentationRegistry.getTargetContext().applicationContext as SmartAlarmApp
        context = app.applicationContext
        dataHelper = AlarmDataHelper(app.getSharedPreferences(ConstantsPreferences.ALARM_SP_NAME.getKeyValue(), Context.MODE_PRIVATE),
                RingtoneManagerHelper(context))
        expectedRingtones = dataHelper.getRingtonesForPopulation()
        onView(withId(R.id.ibAdditionalSettings)).perform(click())
        onView(withId(R.id.ibMoveForward)).perform(click())

    }

    @Test
    fun isDisplayedContent() {
        expectedRingtones.forEachIndexed { position, it ->
            onView(withId(R.id.rvListOfRingtones))
                    .perform(scrollToPosition<RingtonesRecycleViewAdapter.RingtonesViewHolder>(position))
                    .check(ViewAssertions.matches(isDisplayed()))
        }
    }

    @Test
    fun checkInitialCheckBoxesStates() {
        val savedRingtoneName = dataHelper.getRingtoneFromSP()
        var savedRingtonePosition: Int? = null
        expectedRingtones.forEachIndexed { pos, it ->
            if (savedRingtoneName == it.name) {
                savedRingtonePosition = pos
                return@forEachIndexed
            }
        }
        if (savedRingtonePosition == null) {
            throw RuntimeException("something is wrong with ringtonesList")
        }
        expectedRingtones.forEachIndexed { index, it ->
            if (index == savedRingtonePosition) {
                onView(withId(R.id.rvListOfRingtones))
                        .perform(scrollToPosition<RingtonesRecycleViewAdapter.RingtonesViewHolder>(index))
                        .perform(click())
                        .check(matches(AdditionalViewMatcher.atPosition(index, hasDescendant(isChecked()))))
            } else {
                onView(withId(R.id.rvListOfRingtones))
                        .perform(scrollToPosition<RingtonesRecycleViewAdapter.RingtonesViewHolder>(index))
                        .perform(click())
                        .check(matches(AdditionalViewMatcher.atPosition(index, hasDescendant(isNotChecked()))))
            }
        }
    }

    @Test
    fun onlyOneRingtoneCanBeChecked() {
        val checkedItem = expectedRingtones.size / 2
        for (i in 1..checkedItem) {
            onView(withId(R.id.rvListOfRingtones))
                    .perform(RecyclerViewActions.actionOnItemAtPosition<RingtonesRecycleViewAdapter.RingtonesViewHolder>(i,
                            AdditionalViewActions.actionAtChildView(R.id.cbChosenAlarmRingtone, click())))
        }
        for (index in 0 until expectedRingtones.size) {
            if (index == checkedItem) {
                onView(withId(R.id.rvListOfRingtones))
                        .perform(scrollToPosition<RingtonesRecycleViewAdapter.RingtonesViewHolder>(index))
                        .perform(click())
                        .check(matches(AdditionalViewMatcher.atPosition(index, hasDescendant(isChecked()))))
            } else {
                onView(withId(R.id.rvListOfRingtones))
                        .perform(scrollToPosition<RingtonesRecycleViewAdapter.RingtonesViewHolder>(index))
                        .perform(click())
                        .check(matches(AdditionalViewMatcher.atPosition(index, hasDescendant(isNotChecked()))))
            }
        }
    }

    @Test
    fun checkMaxOnePlayButtonIsPause() {
        val pausedItem = expectedRingtones.size / 2
        for (i in 1..pausedItem) {
            onView(withId(R.id.rvListOfRingtones))
                    .perform(RecyclerViewActions.actionOnItemAtPosition<RingtonesRecycleViewAdapter.RingtonesViewHolder>(i,
                            AdditionalViewActions.actionAtChildView(R.id.ibPlayPauseRingtone, click())))
        }
        onView(withId(R.id.rvListOfRingtones))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RingtonesRecycleViewAdapter.RingtonesViewHolder>(pausedItem,
                        AdditionalViewActions.actionAtChildView(R.id.ibPlayPauseRingtone, click())))
        onView(withId(R.id.rvListOfRingtones))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RingtonesRecycleViewAdapter.RingtonesViewHolder>(pausedItem,
                        AdditionalViewActions.actionAtChildView(R.id.ibPlayPauseRingtone, click())))
        for (index in 0 until expectedRingtones.size) {
            if (index == pausedItem) {
                onView(withId(R.id.rvListOfRingtones))
                        .perform(scrollToPosition<RingtonesRecycleViewAdapter.RingtonesViewHolder>(index))
                        .check(matches(AdditionalViewMatcher.atPosition(index,
                                hasDescendant(withContentDescription(context.getString(R.string.contentDescription_pauseRingtone))))))
            } else {
                onView(withId(R.id.rvListOfRingtones))
                        .perform(scrollToPosition<RingtonesRecycleViewAdapter.RingtonesViewHolder>(index))
                        .check(matches(AdditionalViewMatcher.atPosition(index,
                                hasDescendant(withContentDescription(context.getString(R.string.contentDescription_playRingtone))))))
            }
        }

    }
}
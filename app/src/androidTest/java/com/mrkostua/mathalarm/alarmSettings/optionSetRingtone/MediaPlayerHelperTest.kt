package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import android.media.MediaPlayer
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm.MainAlarmActivity
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Kostiantyn Prysiazhnyi on 4/27/2018.
 */
@RunWith(AndroidJUnit4::class)
class MediaPlayerHelperTest {
    @Rule
    @JvmField
    public val alarmMainAlarmActivityTestRule = ActivityTestRule<MainAlarmActivity>(MainAlarmActivity::class.java)

    private lateinit var mediaPlayer: MediaPlayer

    @Before
    fun setUp() {
        onView(withId(R.id.ibAdditionalSettings)).perform(click())
        onView(withId(R.id.ibMoveForward)).perform(click())

        onData(withText("ringtone_loud"))
        onView(allOf(withId(R.id.ibPlayPauseRingtone),
                hasSibling(withText("ringtone_loud"))))
                .perform(click())
        mediaPlayer = MediaPlayerHelper.getMediaPlayer()
        Thread.sleep(1000)

    }

    @Test
    fun playRingtone() {
        assertTrue("mediaPlayer is not playing", mediaPlayer.isPlaying)
        assertTrue("mediaPlayer is not looping", mediaPlayer.isLooping)
        println("\n TESTSTEST \n" + mediaPlayer.trackInfo.toString())

    }

    @Test
    fun stopRingtone() {
        assertTrue("mediaPlayer is not playing", mediaPlayer.isPlaying)
        onView(allOf(withId(R.id.ibPlayPauseRingtone), hasSibling(withText("ringtone_loud"))))
                .perform(click())
        assertFalse("mediaPlayer is playing", mediaPlayer.isPlaying)
    }

    @Test
    fun playMultipleTimes() {
        onView(allOf(withId(R.id.ibPlayPauseRingtone), hasSibling(withText("0."))))
                .perform(click())
        mediaPlayer = MediaPlayerHelper.getMediaPlayer()
        println("\n TESTSTEST1 \n" + mediaPlayer.trackInfo.toString())
        Thread.sleep(1000)

        onView(allOf(withId(R.id.ibPlayPauseRingtone), hasSibling(withText("2."))))
                .perform(click())
        mediaPlayer = MediaPlayerHelper.getMediaPlayer()
        println("\n TESTSTEST2 \n" + mediaPlayer.trackInfo.toString())
        Thread.sleep(1000)

        onView(allOf(withId(R.id.ibPlayPauseRingtone), hasSibling(withText("1."))))
                .perform(click())
        mediaPlayer = MediaPlayerHelper.getMediaPlayer()
        println("\n TESTSTEST3 \n" + mediaPlayer.trackInfo.toString())
        Thread.sleep(1000)

        onView(allOf(withId(R.id.ibPlayPauseRingtone), hasSibling(withText("ringtone_loud"))))
                .perform(click())
        mediaPlayer = MediaPlayerHelper.getMediaPlayer()
        println("\n TESTSTEST4 \n" + mediaPlayer.trackInfo.toString())
        Thread.sleep(1000)




    }
}
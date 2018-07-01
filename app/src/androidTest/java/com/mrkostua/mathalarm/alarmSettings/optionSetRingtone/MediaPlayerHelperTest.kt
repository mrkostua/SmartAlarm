package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm.MainAlarmActivity
import com.mrkostua.mathalarm.tools.ConstantValues
import junit.framework.Assert.*
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Kostiantyn Prysiazhnyi on 4/27/2018.
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class MediaPlayerHelperTest {
    @Rule
    @JvmField
    val alarmMainAlarmActivityTestRule = ActivityTestRule<MainAlarmActivity>(MainAlarmActivity::class.java)

    private lateinit var context: Context
    private lateinit var mediaPlayer: MediaPlayer

    @Before
    fun setUp() {
        context = alarmMainAlarmActivityTestRule.activity.applicationContext
        onView(withId(R.id.ibAdditionalSettings)).perform(click())
        onView(withId(R.id.ibMoveForward)).perform(click())

    }

    @Test
    fun playRingtone() {
        playSomeRingtone("loud")
        mediaPlayer = MediaPlayerHelper.getMediaPlayer()
        assertTrue("mediaPlayer is not playing", mediaPlayer.isPlaying)
        assertTrue("mediaPlayer is not looping", mediaPlayer.isLooping)

    }

    @Test
    fun playRingtoneFromUri() {
        playSomeRingtone("5.")
        mediaPlayer = MediaPlayerHelper.getMediaPlayer()
        assertTrue("mediaPlayer is not playing", mediaPlayer.isPlaying)
        assertTrue("mediaPlayer is not looping", mediaPlayer.isLooping)

    }

    @Test
    fun stopRingtone() {
        playSomeRingtone()
        mediaPlayer = MediaPlayerHelper.getMediaPlayer()
        assertTrue("mediaPlayer is not playing", mediaPlayer.isPlaying)

        onData(withText("loud"))
        onView(allOf(withId(R.id.ibPlayPauseRingtone), hasSibling(withText("loud"))))
                .perform(click())
        assertFalse("mediaPlayer is playing", mediaPlayer.isPlaying)
    }

    @Test
    fun playMultipleTimes() {
        val firstRingtone = "energy"
        val secondRingtone = "loud"
        val thirdRingtone = "ringtone_mechanic_clock"
        playSomeRingtone(firstRingtone)
        mediaPlayer = MediaPlayerHelper.getMediaPlayer()
        assertEquals("playingRingtoneName is different form displaying one", MediaPlayerHelper.getPlayingRingtoneName(), firstRingtone)

        playSomeRingtone(secondRingtone)
        mediaPlayer = MediaPlayerHelper.getMediaPlayer()
        assertEquals("playingRingtoneName is different form expected", MediaPlayerHelper.getPlayingRingtoneName(), secondRingtone)

        playSomeRingtone(thirdRingtone)
        mediaPlayer = MediaPlayerHelper.getMediaPlayer()
        assertEquals("playingRingtoneName is different form expected", MediaPlayerHelper.getPlayingRingtoneName(), thirdRingtone)
        assertNotSame("playingRingtoneName is different form previous", MediaPlayerHelper.getPlayingRingtoneName(), secondRingtone)
    }

    /**
     * also checks playing ringtone with AudioStream as STREAM_ALARM
     */
    @Test
    fun playDeepWakeUpRingtoneTest() {
        onView(withId(R.id.ibMoveForward)).perform(click())
        onView(withId(R.id.ibMoveForward)).perform(click())
        onView(allOf(withId(R.id.ibSleepIcon), hasSibling(withText(context.getString(R.string.deepSleepOptionTitle)))))
                .check(ViewAssertions.matches(isDisplayed()))

        onView(withId(R.id.ibPlayPauseRingtone)).perform(click())
        mediaPlayer = MediaPlayerHelper.getMediaPlayer()
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)
        for (i in 1 until maxVolume) {
            assertEquals("streamVolume is different form expected", i, audioManager.getStreamVolume(AudioManager.STREAM_ALARM))
            assertTrue("ringtone is not playing ", mediaPlayer.isPlaying)
            Thread.sleep(ConstantValues.DEEP_WAKE_UP_VOLUME_ADJUSTMENT_MILLISECONDS)

        }
    }

    private fun playSomeRingtone(someRingtone: String = "energy") {
        onData(withText(someRingtone))
        onView(allOf(withId(R.id.ibPlayPauseRingtone),
                hasSibling(withText(someRingtone))))
                .perform(click())
    }
}
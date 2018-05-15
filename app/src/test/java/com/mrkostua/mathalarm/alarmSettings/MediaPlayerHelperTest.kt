package com.mrkostua.mathalarm.alarmSettings

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.MediaPlayerHelper
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneObject
import com.mrkostua.mathalarm.extentions.MyShadowMediaPlayer
import com.mrkostua.mathalarm.extentions.MyShadows
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config


/**
 * @author Kostiantyn Prysiazhnyi on 5/7/2018.
 */
@RunWith(RobolectricTestRunner::class)
@Config(shadows = [(MyShadowMediaPlayer::class)])
class MediaPlayerHelperTest {
    private lateinit var context: Context
    private lateinit var shadowMediaPlayer: MyShadowMediaPlayer
    private lateinit var mediaPlayerHelper: MediaPlayerHelper
    private val ringtoneName = "energy_ringtone"
    private val defaultRingtoneObject = RingtoneObject(ringtoneName)
    /**
     * The only possible solution to unitTest MediaPlayerHelper is by using Robolectric mainly,
     * Mocking MediaPlayer require additional mocking all usage of android import in this class, it is too complex and some method are static so PowerMock is required.
     * The easiest solution is to write instrumental test using Espresso framework, but it is not so clean as unitTesting and require emulator/physicalDevice.
     ** 1) try to find the problem in ShadowMediaPlayer
     * 2) try to override method which invokes errors in MyShadowMediaPlayer and test it
     * 3) find the real problem in MyShadowMediaPlayer and create issues in github repo
     */
    @Before
    fun setUp() {
        context = RuntimeEnvironment.application.applicationContext
        mediaPlayerHelper = MediaPlayerHelper(context, MediaPlayer())
        shadowMediaPlayer = MyShadows.myShadowOf(MediaPlayer())

    }

    @Test
    fun playRingtoneTest() {
        mediaPlayerHelper.playRingtone(defaultRingtoneObject)
        println("isPrepared : ${shadowMediaPlayer.isPrepared}")
        println(" isLooping : ${shadowMediaPlayer.isLooping}")
        println(" isReallyPlaying : ${shadowMediaPlayer.isReallyPlaying}")
        println(" sourceUri : ${shadowMediaPlayer.sourceUri}")
        println(" dataSource : ${shadowMediaPlayer.dataSource}")
        println(" mediaInfo : ${shadowMediaPlayer.mediaInfo}")
        println(" state : ${shadowMediaPlayer.state}")
        assertNotNull("data must be set before playing", shadowMediaPlayer.sourceUri)
        assertTrue("mp is not set to looping", shadowMediaPlayer.isLooping)
        assertTrue("mp is not playing", shadowMediaPlayer.isPlaying)
        assertTrue("playback events wasn't updated as time passes", shadowMediaPlayer.isReallyPlaying)
        assertNotSame("basic playRingtone() must not have STREAM_ALARM audio stream type", AudioManager.STREAM_ALARM, shadowMediaPlayer.theAudioStreamType)


    }

    @Test
    fun playRingtoneWithAudioStreamAlarmTest() {
        mediaPlayerHelper.playRingtone(defaultRingtoneObject, true)
        assertTrue("mp is not set to looping", shadowMediaPlayer.isLooping)
        assertTrue("mp is not playing", shadowMediaPlayer.isPlaying)
        assertTrue("playback events wasn't updated as time passes", shadowMediaPlayer.isReallyPlaying)
        assertEquals("audio stream is not ALARM_STREAM", AudioManager.STREAM_ALARM, shadowMediaPlayer.theAudioStreamType)

    }

    @Test
    fun playRingtoneMultiTimesTest() {
        mediaPlayerHelper.playRingtone(RingtoneObject("firstRingtone"))
        val firstDS = shadowMediaPlayer.dataSource
        mediaPlayerHelper.playRingtone(RingtoneObject("secondRingtone"))
        val secondDS = shadowMediaPlayer.dataSource
        mediaPlayerHelper.playRingtone(RingtoneObject("thirdRingtone"))
        val expectedDS = shadowMediaPlayer.dataSource

        assertNotSame("second playing ringtone data source wasn't set (first is still set)", firstDS, secondDS)
        assertNotSame("third playing ringtone data source wasn't set (second is still set)", secondDS, expectedDS)
        assertEquals(expectedDS, shadowMediaPlayer.dataSource)

    }

    @Test
    fun playDeepWakeUpRingtoneTest() {

    }

    @Test
    fun stopPlayingTest() {
        mediaPlayerHelper.stopPlaying()
        assertFalse("mp is playing after alarm was stopped", shadowMediaPlayer.isPlaying)
        assertFalse("playback events was updated as time passes after alarm was stopped", shadowMediaPlayer.isReallyPlaying)

    }

    @Test
    fun onErrorInvokedTest() {
        shadowMediaPlayer.invokeErrorListener(MediaPlayer.MEDIA_ERROR_UNKNOWN, MediaPlayer.MEDIA_ERROR_UNSUPPORTED)
        assertFalse("mp is playing after alarm was stopped", shadowMediaPlayer.isPlaying)
        assertFalse("playback events was updated as time passes after alarm was stopped", shadowMediaPlayer.isReallyPlaying)

    }

}
package com.mrkostua.mathalarm.alarmSettings

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.MediaPlayerHelper
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneObject
import com.mrkostua.mathalarm.extentions.MyShadowMediaPlayer
import com.mrkostua.mathalarm.extentions.MyShadows
import com.mrkostua.mathalarm.tools.ConstantValues
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowMediaPlayer
import org.robolectric.shadows.util.DataSource


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
     * TODO maybe create 2 tests from MediaPlayerHelper -> UnitTest to test logic and all other things,
     * second AndroidTest to test actual playing music (read more about it)
     * TODO write some test with simulating player scenario.
     */
    @Before
    fun setUp() {
        context = RuntimeEnvironment.application.applicationContext
        mediaPlayerHelper = MediaPlayerHelper(context)
        shadowMediaPlayer = MyShadows.myShadowOf(MediaPlayer())

        MyShadowMediaPlayer.addMediaInfo(DataSource.toDataSource(context,
                Uri.parse(ConstantValues.ANDROID_RESOURCE_PATH + context.packageName + "/raw/" + ringtoneName)),
                ShadowMediaPlayer.MediaInfo(8000,-1))

    }

    @Test
    fun playRingtoneTest() {
        mediaPlayerHelper.playRingtone(defaultRingtoneObject)
        shadowMediaPlayer.invokePreparedListener()
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
        //TODO learn how to simulate handler work
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
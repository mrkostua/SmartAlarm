package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import com.mrkostua.mathalarm.tools.ShowLogs
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 17.01.2018.
 */
class MediaPlayerHelper @Inject constructor(private val context: Context) : MediaPlayer.OnErrorListener {
    private val TAG = this.javaClass.simpleName
    private val rawType = "raw"
    private var isMpPlaying = false
    private var mediaPlayer: MediaPlayer? = null

     fun playRingtoneFromRingtoneOb(ringtoneOb : RingtoneObject) {
        if (ringtoneOb.uri == null) {
            playRingtoneFromStringResource(ringtoneOb.name, true)

        } else {
            playRingtoneFromUri(ringtoneOb.uri, true)

        }
    }


    fun playRingtoneFromStringResource(ringtoneResourceId: String, isAlarmStreamType: Boolean = false) {
        val ringtoneResourceName: Int = getRawResourceId(ringtoneResourceId)
        ShowLogs.log(TAG,"playRingtoneFromStringResource : isAlarmStream type : " + isAlarmStreamType + " and : res id : " + ringtoneResourceId)

        if (isMpPlaying) {
            mediaPlayer?.stop()
            mediaPlayer?.reset()

        }
        if (isAlarmStreamType) {
            setAlarmStream(mediaPlayer)

        }
        mediaPlayer = getNewMediaPlayer(ringtoneResourceName)
        startPlayingMusic(mediaPlayer)


        isMpPlaying = true

    }

    fun playRingtoneFromUri(ringtoneUri: Uri, isAlarmStreamType: Boolean = false) {
        ShowLogs.log(TAG,"playRingtoneFromStringResource : isAlarmStream type : " + isAlarmStreamType + " and uri : " + ringtoneUri)

        if (isMpPlaying) {
            mediaPlayer?.stop()
            mediaPlayer?.reset()

        }
        if (isAlarmStreamType) {
            setAlarmStream(mediaPlayer)

        }
        mediaPlayer = getNewMediaPlayer(ringtoneUri)
        startPlayingMusic(mediaPlayer)

        isMpPlaying = true
    }


    fun stopRingtone() {
        if (isMpPlaying) {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            isMpPlaying = false
        }
    }

    fun releaseMediaPlayer() {
        mediaPlayer?.release()
    }

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        ShowLogs.log(TAG, "getNewMediaPlayer onErrorListener what :" + what + " extra : " + extra)
        mp.stop()
        mp.release()
        mediaPlayer = null
        isMpPlaying = false
        return true
    }

    private fun startPlayingMusic(mp: MediaPlayer?) {
        mp?.isLooping = true
        mp?.start()
    }

    private fun setAlarmStream(mp: MediaPlayer?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mp?.setAudioAttributes(AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build())
        } else {
            @Suppress("DEPRECATION")
            mp?.setAudioStreamType(AudioManager.STREAM_ALARM)

        }
    }

    private fun getRawResourceId(resourceName: String): Int =
            context.resources.getIdentifier(resourceName, rawType, context.packageName)

    private inline fun <reified T : Any> getNewMediaPlayer(ringtone: T): MediaPlayer? {
        return when (T::class) {
            Int::class -> {
                ShowLogs.log(TAG, "getNewMediaPlayer int ringtone : " + (ringtone as Int).toString())
                mediaPlayer = MediaPlayer.create(context, ringtone as Int)
                mediaPlayer?.setOnErrorListener(this)
                mediaPlayer
            }
            Uri::class -> {
                ShowLogs.log(TAG, "getNewMediaPlayer int ringtone : " + (ringtone as Uri).toString())
                mediaPlayer = MediaPlayer.create(context, ringtone as Uri)
                mediaPlayer?.setOnErrorListener(this)
                mediaPlayer
            }
            else -> throw UnsupportedOperationException("Not implemented")
        }
    }
}
package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Message
import com.mrkostua.mathalarm.tools.ConstantValues
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
    private val handleAdjustVolume = 3
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private val userMaxVolume = audioManager.getStreamVolume(AudioManager.STREAM_ALARM)

    @SuppressLint("HandlerLeak")
    private val handler = object : Handler() {
        private var volumeAdjustment = 0
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                handleAdjustVolume -> {
                    ShowLogs.log(TAG, "handleMessage : handleAdjustVolume")
                    adjustVolume(volumeAdjustment)
                    ++volumeAdjustment

                }

            }
        }
    }


    fun playRingtoneFromRingtoneOb(ringtoneOb: RingtoneObject, isAlarmStreamType: Boolean = false) {
        if (ringtoneOb.uri == null) {
            playRingtoneFromStringResource(ringtoneOb.name, isAlarmStreamType)

        } else {
            playRingtoneFromUri(ringtoneOb.uri, isAlarmStreamType)

        }
    }

    fun setAlarmStreamVolume() {
        with(context.getSystemService(Context.AUDIO_SERVICE) as AudioManager) {
            setStreamVolume(AudioManager.STREAM_ALARM, getStreamVolume(AudioManager.STREAM_ALARM), 0)

        }
    }

    fun playDeepWakeUpRingtone(ringtoneOb: RingtoneObject) {
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM,
                audioManager.getStreamVolume(AudioManager.STREAM_ALARM) / 5, 0)

        playRingtoneFromRingtoneOb(ringtoneOb, true)
        sendHandlerDelayAdjustVolume()

    }

    fun stopRingtone() {
        if (isMpPlaying) {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            isMpPlaying = false
        }
        disableHandlerMessages(handleAdjustVolume)

    }

    fun releaseMediaPlayer() {
        mediaPlayer?.release()
    }

    private fun playRingtoneFromStringResource(ringtoneResourceId: String, isAlarmStreamType: Boolean = false) {
        val ringtoneResourceName: Int = getRawResourceId(ringtoneResourceId)
        ShowLogs.log(TAG, "playRingtoneFromStringResource : isAlarmStream type : " + isAlarmStreamType + " and : res id : " + ringtoneResourceId)
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

    private fun playRingtoneFromUri(ringtoneUri: Uri, isAlarmStreamType: Boolean = false) {
        ShowLogs.log(TAG, "playRingtoneFromStringResource : isAlarmStream type : " + isAlarmStreamType + " and uri : " + ringtoneUri)
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

    private fun sendHandlerDelayAdjustVolume() {
        handler.sendMessageDelayed(handler.obtainMessage(handleAdjustVolume),
                ConstantValues.DEEP_WAKE_UP_VOLUME_ADJUSTMENT_MILLISECONDS)

    }

    private fun disableHandlerMessages(what: Int) {
        handler.removeMessages(what)

    }

    private fun adjustVolume(volume: Int) {
        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) < userMaxVolume) {
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM,
                    audioManager.getStreamVolume(AudioManager.STREAM_ALARM) + 1,
                    0)

        } else {
            ShowLogs.log(TAG, "adjustVolume() volume " + volume + " is set to max")
            
        }


    }

}
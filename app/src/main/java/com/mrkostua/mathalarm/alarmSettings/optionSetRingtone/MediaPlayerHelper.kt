package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.mrkostua.mathalarm.injections.annotation.ActivityContext
import com.mrkostua.mathalarm.tools.ShowLogs
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 17.01.2018.
 */
class MediaPlayerHelper @Inject constructor(@ActivityContext private val context: Context) : MediaPlayer.OnErrorListener {
    private val TAG = this.javaClass.simpleName
    private val rawType = "raw"
    private var isMpPlaying = false
    private var mediaPlayer: MediaPlayer? = null

    fun playRingtoneFromStringResource(ringtoneResourceId: String) {
        val ringtoneResourceName: Int = getRawResourceId(ringtoneResourceId)
        if (!isMpPlaying) {
            mediaPlayer = getNewMediaPlayer(ringtoneResourceName)
            mediaPlayer?.start()

        } else {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer = getNewMediaPlayer(ringtoneResourceName)
            mediaPlayer?.start()
        }
        isMpPlaying = true
    }

    fun playRingtoneFromUri(ringtoneUri: Uri) {
        if (!isMpPlaying) {
            startPlayingMusic(ringtoneUri)
        } else {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            startPlayingMusic(ringtoneUri)
        }
        isMpPlaying = true
    }

    fun stopRingtone() {
        if (isMpPlaying) {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            isMpPlaying = false
        }
    }

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        ShowLogs.log(TAG, "getNewMediaPlayer onErrorListener what :" + what + " extra : " + extra)
        mp.stop()
        mp.release()
        mediaPlayer = null
        isMpPlaying = false
        return true
    }

    private fun startPlayingMusic(ringtoneUri: Uri) {
        mediaPlayer = getNewMediaPlayer(ringtoneUri)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    private fun getRawResourceId(resourceName: String): Int =
            context.resources.getIdentifier(resourceName, rawType, context.packageName)

    private inline fun <reified T : Any> getNewMediaPlayer(ringtone: T): MediaPlayer? {
        return when (T::class) {
            Int::class -> {
                mediaPlayer = MediaPlayer.create(context, ringtone as Int)
                mediaPlayer?.setOnErrorListener(this)
                mediaPlayer
            }
            Uri::class -> {
                mediaPlayer = MediaPlayer.create(context, ringtone as Uri)
                mediaPlayer?.setOnErrorListener(this)
                mediaPlayer
            }
            else -> throw UnsupportedOperationException("Not implemented")
        }
    }
}
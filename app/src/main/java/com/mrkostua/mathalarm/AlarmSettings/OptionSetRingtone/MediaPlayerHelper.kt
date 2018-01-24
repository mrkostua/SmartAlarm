package com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.mrkostua.mathalarm.Tools.ShowLogs

/**
 * @author Kostiantyn Prysiazhnyi on 17.01.2018.
 */
class MediaPlayerHelper(private val context: Context) : MediaPlayer.OnErrorListener {
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
            mediaPlayer = getNewMediaPlayer(ringtoneUri)
            mediaPlayer?.start()
        } else {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer = getNewMediaPlayer(ringtoneUri)
            mediaPlayer?.start()
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
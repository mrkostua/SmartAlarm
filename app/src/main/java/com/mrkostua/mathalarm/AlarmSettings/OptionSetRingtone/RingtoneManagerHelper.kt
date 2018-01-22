package com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone

import android.content.Context
import android.media.MediaPlayer
import com.mrkostua.mathalarm.Tools.ShowLogs

/**
 * @author Kostiantyn Prysiazhnyi on 17.01.2018.
 */
class RingtoneManagerHelper(private val context: Context) {
    private val TAG = this.javaClass.simpleName
    private val rawType = "raw"
    private var isMpPlaying = false
    private var mediaPlayer: MediaPlayer? = null

    fun playCustomRingtone(ringtoneResourceId: String) {
        val ringtoneResourceName = getRawResourceId(ringtoneResourceId)
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

    fun stopRingtone() {
        if (isMpPlaying) {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            isMpPlaying = false
        }
    }

    private fun getRawResourceId(resourceName: String): Int =
            context.resources.getIdentifier(resourceName, rawType, context.packageName)

    private fun getNewMediaPlayer(ringtoneResourceId: Int): MediaPlayer? {
        mediaPlayer = MediaPlayer.create(context, ringtoneResourceId)
        mediaPlayer?.setOnErrorListener { mp: MediaPlayer, what: Int, extra: Int ->
            ShowLogs.log(TAG, "getNewMediaPlayer onErrorListener what :" + what + " extra : " + extra)
            mp.stop()
            mp.release()
            mediaPlayer = null
            isMpPlaying = false
            return@setOnErrorListener true

        }
        return mediaPlayer
    }

}
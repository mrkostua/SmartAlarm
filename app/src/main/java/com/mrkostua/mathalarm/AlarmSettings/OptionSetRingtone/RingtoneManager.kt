package com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone

import android.content.Context
import android.media.MediaPlayer
import com.mrkostua.mathalarm.Tools.ShowLogs

/**
 * @author Kostiantyn Prysiazhnyi on 17.01.2018.
 */
class RingtoneManager(private val context: Context) {
    private val TAG = this.javaClass.simpleName
    private val rawType = "raw"
    private var isMpPlaying = false
    private var mediaPlayer: MediaPlayer? = null

    //1 play music -> after it can be paused so user can push it once more and music will continue playing from the place where it was paused.
    //2 If another music will be start last paused music data will be deleted,
    //onTouch outside the list music will stop, onPause pause, move to next settings fragment paused,


    fun getRawResourceId(resourceName: String): Int =
            context.resources.getIdentifier(resourceName, rawType, context.packageName)

    fun playRingtone(ringtoneResourceId: Int) {
        mediaPlayer = getNewMediaPlayer(ringtoneResourceId)
        if (!isMpPlaying) {
            mediaPlayer?.start()
        } else {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer = getNewMediaPlayer(ringtoneResourceId)
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
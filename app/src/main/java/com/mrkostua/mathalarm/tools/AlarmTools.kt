package com.mrkostua.mathalarm.tools

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageButton
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm.MainAlarmActivity
import java.util.*

/**
 * @author Kostiantyn Prysiazhnyi on 06.12.2017.
 */
public object AlarmTools {
    private val TAG = this.javaClass.simpleName

    @Suppress("DEPRECATION")
    fun getDrawable(resources: Resources, drawableId: Int): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resources.getDrawable(drawableId, null)

        } else {
            resources.getDrawable(drawableId)

        }
    }

     fun isRingtoneImagePlay(context : Context, view: ImageButton): Boolean =
            view.contentDescription == context.resources.getString(R.string.contentDescription_playRingtone)

    fun getLastFragmentIndex(): Int {
        return ConstantValues.alarmSettingsOptionsList.size - 1
    }

    fun startMainActivity(context: Context) {
        context.startActivity(Intent(context, MainAlarmActivity::class.java))
    }

    fun getTimeToAlarmStart(alarmHour: Int, alarmMinute: Int): Pair<Int, Int> {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        ShowLogs.log(TAG, " h currentTimeInMinutes: " + currentHour + "  alarm hour: " + alarmHour)
        ShowLogs.log(TAG, " min currentTimeInMinutes: " + currentMinute + "  alarm min: " + alarmMinute)

        val alarmTimeInMinutes = alarmHour * 60 + alarmMinute
        val currentTimeInMinutes: Int = currentHour * 60 + currentMinute
        val timeToStartAlarm = when {
            alarmHour > currentHour -> alarmTimeInMinutes - currentTimeInMinutes

            alarmHour < currentHour -> ConstantValues.DAY_IN_MINUTES - (currentTimeInMinutes - alarmTimeInMinutes)

            alarmHour == currentHour -> {
                if (currentMinute > alarmMinute) {
                    ConstantValues.DAY_IN_MINUTES - (currentTimeInMinutes - alarmTimeInMinutes)
                } else {
                    alarmTimeInMinutes - currentTimeInMinutes
                }
            }

            else -> throw UnsupportedOperationException("imposable action")
        }
        return Pair(covertTo24Format(timeToStartAlarm).first,
                covertTo24Format(timeToStartAlarm).second)
    }

    fun getReadableTime(hour: Int, min: Int) =
            if (hour == 0) {
                hour.toString() + "0"
            } else {
                hour.toString()
            } + " : " + if (min < 10) {
                "0" + min.toString()
            } else {
                min.toString()
            }

    private fun covertTo24Format(minToAlarmStart: Int): Pair<Int, Int> {
        val resultH = minToAlarmStart / 60
        return Pair(resultH, minToAlarmStart - resultH * 60)

    }

}
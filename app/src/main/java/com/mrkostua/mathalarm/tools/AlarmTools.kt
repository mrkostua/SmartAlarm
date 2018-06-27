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
object AlarmTools {
    @Suppress("DEPRECATION")
    fun getDrawable(resources: Resources, drawableId: Int): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resources.getDrawable(drawableId, null)

        } else {
            resources.getDrawable(drawableId)

        }
    }

    fun isRingtoneImagePlay(context: Context, view: ImageButton): Boolean =
            view.contentDescription == context.resources.getString(R.string.contentDescription_playRingtone)

    fun getLastFragmentIndex(): Int {
        return ConstantValues.alarmSettingsOptionsList.size - 1
    }

    fun startMainActivity(context: Context) {
        context.startActivity(Intent(context, MainAlarmActivity::class.java))
    }

    fun getTimeToAlarmStart(alarmHour: Int, alarmMinute: Int, calendar: Calendar = Calendar.getInstance()): Pair<Int, Int> {
        if (alarmHour !in 0..24 || alarmMinute !in 0..60) {
            throw UnsupportedOperationException("wrong alarm hour and time values only 24 hours and 60 minutes")

        }
        calendar.timeInMillis = System.currentTimeMillis()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

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
        return Pair(convertTo24Format(timeToStartAlarm).first,
                convertTo24Format(timeToStartAlarm).second)
    }

    fun getReadableTime(hour: Int, min: Int) = getReadableHour(hour) + " : " + getReadableMinute(min)

    private fun getReadableHour(hour: Int) = if (hour == 0) {
        hour.toString() + "0"
    } else {
        hour.toString()
    }

    private fun getReadableMinute(min: Int) = if (min < 10) {
        "0" + min.toString()
    } else {
        min.toString()
    }

    private fun convertTo24Format(minToAlarmStart: Int): Pair<Int, Int> {
        val resultH = minToAlarmStart / 60
        return Pair(resultH, minToAlarmStart - resultH * 60)

    }
}
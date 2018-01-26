package com.mrkostua.mathalarm.Tools

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.mrkostua.mathalarm.CountsTimeToAlarmStart
import com.mrkostua.mathalarm.R

/**
 * @author Kostiantyn Prysiazhnyi on 05.12.2017.
 */

public class NotificationTools(val context: Context) {
    private val countsTimeToAlarmStart = CountsTimeToAlarmStart()

    public fun showToastTimeToAlarmBoom(alarmHour: Int, alarmMinute: Int) {
        countsTimeToAlarmStart.HowMuchTimeToStart(alarmHour, alarmMinute)

        val toastMessage = Toast.makeText(context,
                context.getString(R.string.MAP_Toast_timeLeftToAlarmStart,
                        CountsTimeToAlarmStart.MinuteHourConvertMethod(countsTimeToAlarmStart.resultHours, countsTimeToAlarmStart.resultMinutes)), Toast.LENGTH_LONG)
        toastMessage.setGravity(Gravity.TOP or Gravity.START, 0, 0)
        toastMessage.show()

    }

    public fun getTimeUntilAlarmBoob(alarmHour: Int, alarmMinute: Int): Pair<Int, Int> {
        countsTimeToAlarmStart.HowMuchTimeToStart(alarmHour, alarmMinute)

        return Pair(countsTimeToAlarmStart.resultHours, countsTimeToAlarmStart.resultMinutes)
    }

    public fun convertTimeToReadableTime(hour: Int, minute: Int): String {
        var sHour = hour.toString()
        var sMinute = minute.toString()

        if (hour == 0)
            sHour = hour.toString() + "0"
        if (minute < 10)
            sMinute = "0" + minute

        return sHour + ":" + sMinute
    }

    public fun showToastMessage(messageText: String) {
        Toast.makeText(context, messageText, Toast.LENGTH_LONG).show()
    }

}

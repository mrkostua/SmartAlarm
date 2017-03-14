package com.example.mathalarm;

import android.util.Log;

import java.util.Calendar;


public class CountsTimeToAlarmStart {
    private int resultHours, resultMinutes;

    public void HowMuchTimeToStart( int pickedHour, int pickedMinute) {
        //
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        // day in minutes
        final int dayInMinutes = 1440;
        // Difference between current hour or minute and picked hour or minute
        int differenceCP;
        int timeToStartAlarm =0;

        //24 hour format time convert to minutes
        int picked, current;
        picked = pickedHour * 60 + pickedMinute;
        current = currentHour * 60 + currentMinute;

        if(ShowLogs.LOG_STATUS)ShowLogs.i(" h current: " + currentHour + "  alarm hour: " + pickedHour);
        if(ShowLogs.LOG_STATUS)ShowLogs.i(" min current: " + currentMinute + "  alarm min: " + pickedMinute);

        if (pickedHour>currentHour) {
            timeToStartAlarm = picked - current;
        }
        if (pickedHour < currentHour) {
            //minutes to start
            differenceCP = current - picked;
            timeToStartAlarm = dayInMinutes - differenceCP;
        }
        if(pickedHour == currentHour) {
            if (currentMinute > pickedMinute ) {
                differenceCP = current - picked;
                timeToStartAlarm = dayInMinutes - differenceCP;
            }
            else {
                timeToStartAlarm = picked - current;
            }
        }
        // convert from minutes to 24 hour format
        resultHours = timeToStartAlarm / 60;
        resultMinutes = timeToStartAlarm - resultHours * 60;
    }

    public int getResultHours ()
    {
        return resultHours;
    }
    public int getResultMinutes ()
    {
        return resultMinutes;
    }

}

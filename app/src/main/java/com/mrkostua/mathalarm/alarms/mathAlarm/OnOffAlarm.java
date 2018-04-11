package com.mrkostua.mathalarm.alarms.mathAlarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.mrkostua.mathalarm.alarms.mathAlarm.receivers.AlarmReceiver;
import com.mrkostua.mathalarm.alarms.mathAlarm.services.WakeLockService;
import com.mrkostua.mathalarm.ShowLogsOld;
import com.mrkostua.mathalarm.tools.ConstantValues;

import java.util.Calendar;

import static com.mrkostua.mathalarm.tools.TimeToAlarmStart.convertTimeToReadableTime;

//TODO refactoring!! delete all private alarm data variables and use SP data
public class OnOffAlarm {
    private Context alarmContext;

    public OnOffAlarm(Context alarmContext) {
        this.alarmContext = alarmContext;
    }

    private AlarmManager getAlarmManager() {
        return (AlarmManager) alarmContext.getSystemService(Context.ALARM_SERVICE);
    }

    private Intent GetAlarmIntentExtras() {
        Intent receiverIntent = new Intent(ConstantValues.START_NEW_ALARM_ACTION);
        receiverIntent.setClass(alarmContext, AlarmReceiver.class);
        return receiverIntent;
    }

    public void SetNewAlarm(AlarmObject alarmObject) {
        int pickedHour = alarmObject.getHours();
        int pickedMinute = alarmObject.getMinutes();
        if (ShowLogsOld.LOG_STATUS) ShowLogsOld.i("OnOffAlarm" + "  SetNewAlarm");
        //initialize alarmManager
        AlarmManager alarmManager = getAlarmManager();
        /**duplicate in the OnCreate Method (but it is necessary to refresh an instant of calendar
         * and when setting alarm on choose right type of alarm(what is based on the current and
         * picked time)
         */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        //setting calendar instance with the hour and minute that we picked on the timerPicker
        calendar.set(Calendar.HOUR_OF_DAY, pickedHour);
        calendar.set(Calendar.MINUTE, pickedMinute);

        //pendingIntent that delays the intent until the specified calendar time
        PendingIntent pendingIntent = PendingIntent.getBroadcast(alarmContext, 0, GetAlarmIntentExtras(),
                PendingIntent.FLAG_UPDATE_CURRENT);

        // If the hour picked in the TimePicker longer than hour of Current time or equal
        //and if a minute is longer or equal than the  current minute (because hour may be same , but the minute less )
        if (pickedHour > currentHour) {
            if (ShowLogsOld.LOG_STATUS)
                ShowLogsOld.i("OnOffAlarm " + "h current: " + currentHour + " alarm hour: " + pickedHour + "  Today");
            if (ShowLogsOld.LOG_STATUS)
                ShowLogsOld.i("OnOffAlarm " + "min current: " + currentMinute + " alarm min: " + pickedMinute + "  Today");
            //set the alarm manager
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        //All other cases  alarmManager.setInexactRepeating  AlarmManager.INTERVAL_DAY
        if (pickedHour < currentHour) {
            if (ShowLogsOld.LOG_STATUS)
                ShowLogsOld.i("OnOffAlarm " + "h current: " + currentHour + " alarm hour: " + pickedHour + "Next Day");
            if (ShowLogsOld.LOG_STATUS)
                ShowLogsOld.i("OnOffAlarm " + "min current: " + currentMinute + " alarm min: " + pickedMinute + "  Next Day");
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK) + 1);
            calendar.set(Calendar.HOUR_OF_DAY, pickedHour);
            calendar.set(Calendar.MINUTE, pickedMinute);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        if (pickedHour == currentHour) {
            if (pickedMinute < currentMinute) {
                if (ShowLogsOld.LOG_STATUS)
                    ShowLogsOld.i("OnOffAlarm " + "h current: " + currentHour + " alarm hour: " + pickedHour + " Next Day ");
                if (ShowLogsOld.LOG_STATUS)
                    ShowLogsOld.i("OnOffAlarm " + "min current: " + currentMinute + " alarm min: " + pickedMinute + "  Next Day");
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK) + 1);
                calendar.set(Calendar.HOUR_OF_DAY, pickedHour);
                calendar.set(Calendar.MINUTE, pickedMinute);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                if (ShowLogsOld.LOG_STATUS)
                    ShowLogsOld.i("OnOffAlarm " + "h current: " + currentHour + " alarm hour: " + pickedHour + "  Today");
                if (ShowLogsOld.LOG_STATUS)
                    ShowLogsOld.i("OnOffAlarm " + "min current: " + currentMinute + " alarm min: " + pickedMinute + "  Today");
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
    }

    public void CancelSetAlarm() {
        if (ShowLogsOld.LOG_STATUS) ShowLogsOld.i("OnOffAlarm" + "  CancelSetAlarm");
        try {
            PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(alarmContext, 0,
                    new Intent(ConstantValues.START_NEW_ALARM_ACTION).setClass(alarmContext, AlarmReceiver.class),
                    PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = getAlarmManager();
            alarmManager.cancel(cancelPendingIntent);
        } catch (Exception e) {
            if (ShowLogsOld.LOG_STATUS) ShowLogsOld.i("cancel error" + e.getMessage());
        }
    }

    //snooze alarm for 5 minutes
    public void SnoozeSetAlarm(int snoozeTime) {
        if (ShowLogsOld.LOG_STATUS) ShowLogsOld.i("OnOffAlarm" + "  SnoozeSetAlarm");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(alarmContext, 0, GetAlarmIntentExtras(),
                PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + snoozeTime);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        AlarmManager alarmManager = getAlarmManager();
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        //start service with PartialWakeLock
        Intent wakeLockIntent = new Intent(alarmContext, WakeLockService.class);
        wakeLockIntent.putExtra("alarmTimeKey", convertTimeToReadableTime(hour, min));
        if (ShowLogsOld.LOG_STATUS)
            ShowLogsOld.i("OnOffAlarm SNOOZE TIME1 :" + hour + " m :" + min);
        alarmContext.startService(wakeLockIntent);
    }

}

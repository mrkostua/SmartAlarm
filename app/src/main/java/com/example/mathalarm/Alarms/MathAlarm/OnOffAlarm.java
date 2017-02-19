package com.example.mathalarm.Alarms.MathAlarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class OnOffAlarm {
    private static final String TAG = "AlarmProcess";

    private int pickedHour, pickedMinute;
    private Context alarmContext;
    private int alarmComplexityLevel, selectedMusic;
    private boolean alarmCondition;
    private String alarmMessageText;

    public OnOffAlarm(Context alarmContext,int pickedHour,int pickedMinute,
                      int alarmComplexityLevel, int selectedMusic,
                      boolean alarmCondition, String alarmMessageText) {
        this.pickedHour = pickedHour;
        this.pickedMinute = pickedMinute;
        this.alarmContext = alarmContext;
        //alarm additional data
        this.alarmComplexityLevel = alarmComplexityLevel;
        this.selectedMusic = selectedMusic;
        this.alarmCondition = alarmCondition;
        this.alarmMessageText = alarmMessageText;
    }

    public OnOffAlarm(Context alarmContext,int alarmComplexityLevel,
                      int selectedMusic, boolean alarmCondition, String alarmMessageText) {
        this.alarmContext = alarmContext;
        //alarm additional data
        this.alarmComplexityLevel = alarmComplexityLevel;
        this.selectedMusic = selectedMusic;
        this.alarmCondition = alarmCondition;
        this.alarmMessageText = alarmMessageText;
    }

    public OnOffAlarm(Context alarmContext) {
        this.alarmContext = alarmContext;
    }

    private AlarmManager getAlarmManager (){
        return (AlarmManager) alarmContext.getSystemService(Context.ALARM_SERVICE);
    }

    public  Intent  GetAlarmIntentExtras(){
        Intent receiverIntent = new Intent(MainMathAlarm.ALARM_START_NEW);
        //sending the type of alarm 2(math)
        receiverIntent.putExtra("selectedMusic",selectedMusic)
                .putExtra("alarmMessageText",alarmMessageText)
                .putExtra("alarmComplexityLevel",alarmComplexityLevel)
                // sending the condition of alarm if true - alarm on , if false - alarm off
                .putExtra("alarmCondition", alarmCondition);
        return receiverIntent;
    }

    public void SetNewAlarm() {
        Log.i(TAG, "OnOffAlarm" +"  SetNewAlarm");

        //refresh an instant of calendar to get the exact hour
        Calendar calendar = null;
        int currentHour,currentMinute = 0;

        //initialize alarmManager
        AlarmManager alarmManager = getAlarmManager();
        /**duplicate in the OnCreate Method (but it is necessary to refresh an instant of calendar
         * and when setting alarm on choose right type of alarm(what is based on the current and
         * picked time)
         */
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        //setting calendar instance with the hour and minute that we picked on the timerPicker
        calendar.set(Calendar.HOUR_OF_DAY, pickedHour);
        calendar.set(Calendar.MINUTE, pickedMinute);

        //pendingIntent that delays the intent until the specified calendar time
        PendingIntent pendingIntent = PendingIntent.getBroadcast(alarmContext, 0, GetAlarmIntentExtras(),
                PendingIntent.FLAG_UPDATE_CURRENT);

        // If the hour picked in the TimePicker longer than hour of Current time or equal
        //and if a minute is longer or equal than the  current minute (because hour may be same , but the minute less )
        if (pickedHour > currentHour) {
            Log.i(TAG, "OnOffAlarm "+"h current: " + currentHour + " alarm hour: " + pickedHour + "  Today");
            Log.i(TAG, "OnOffAlarm "+"min current: " + currentMinute + " alarm min: " + pickedMinute + "  Today");
            //set the alarm manager
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        //All other cases  alarmManager.setInexactRepeating  AlarmManager.INTERVAL_DAY
        if (pickedHour < currentHour) {
            Log.i(TAG, "OnOffAlarm "+"h current: " + currentHour + " alarm hour: " + pickedHour + "Next Day");
            Log.i(TAG, "OnOffAlarm "+"min current: " + currentMinute + " alarm min: " + pickedMinute + "  Next Day");

            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK) + 1);
            calendar.set(Calendar.HOUR_OF_DAY, pickedHour);
            calendar.set(Calendar.MINUTE, pickedMinute);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        if (pickedHour == currentHour) {
            if (pickedMinute < currentMinute) {
                Log.i(TAG, "OnOffAlarm "+"h current: " + currentHour + " alarm hour: " + pickedHour + " Next Day ");
                Log.i(TAG, "OnOffAlarm "+"min current: " + currentMinute + " alarm min: " + pickedMinute + "  Next Day");

                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK) + 1);
                calendar.set(Calendar.HOUR_OF_DAY, pickedHour);
                calendar.set(Calendar.MINUTE, pickedMinute);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                Log.i(TAG, "OnOffAlarm "+"h current: " + currentHour + " alarm hour: " + pickedHour + "  Today");
                Log.i(TAG, "OnOffAlarm "+"min current: " + currentMinute + " alarm min: " + pickedMinute + "  Today");
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
    }



    public void CancelSetAlarm() {
        Log.i(TAG, "OnOffAlarm" +"  CancelSetAlarm");

        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(alarmContext,0,new Intent(MainMathAlarm.ALARM_START_NEW),PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = getAlarmManager();
        alarmManager.cancel(cancelPendingIntent);
    }
    //snooze alarm for 5 minutes
    public void SnoozeSetAlarm(int snoozeTime) {
        Log.i(TAG, "OnOffAlarm" +"  SnoozeSetAlarm");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(alarmContext, 0, GetAlarmIntentExtras(),
                PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + snoozeTime);

        AlarmManager alarmManager = getAlarmManager();
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

        //start service with PartialWakeLock
        Intent wakeLockIntent = new Intent(alarmContext,WakeLockService.class);
        String time = calendar.get(Calendar.HOUR_OF_DAY) + " " + calendar.get(Calendar.MINUTE);
        wakeLockIntent.putExtra("alarmTimeKey",time);
        alarmContext.startService(wakeLockIntent);
    }

}

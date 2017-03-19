package com.example.mathalarm.Alarms.MathAlarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.mathalarm.ShowLogs;

import java.util.Calendar;

import static com.example.mathalarm.CountsTimeToAlarmStart.MinuteHourConvertMethod;

class OnOffAlarm {
    private int pickedHour, pickedMinute;
    private Context alarmContext;
    private int alarmComplexityLevel, selectedMusic;
    private boolean alarmCondition;
    private String alarmMessageText;
    private int selectedDeepSleepMusic;

    //constructor for SetNewAlarm
     OnOffAlarm(Context alarmContext,int pickedHour,int pickedMinute,
                      int alarmComplexityLevel, int selectedMusic,
                      boolean alarmCondition, String alarmMessageText,int selectedDeepSleepMusic) {
        this.pickedHour = pickedHour;
        this.pickedMinute = pickedMinute;
        this.alarmContext = alarmContext;
        //alarm additional data
        this.alarmComplexityLevel = alarmComplexityLevel;
        this.selectedMusic = selectedMusic;
        this.alarmCondition = alarmCondition;
        this.alarmMessageText = alarmMessageText;
         this.selectedDeepSleepMusic = selectedDeepSleepMusic;
    }

    //constructor for SnoozeSetAlarm method( with all alarm settings)
     OnOffAlarm(Context alarmContext,int alarmComplexityLevel,
                      int selectedMusic, boolean alarmCondition, String alarmMessageText, int selectedDeepSleepMusic) {
        this.alarmContext = alarmContext;
        //alarm additional data
        this.alarmComplexityLevel = alarmComplexityLevel;
        this.selectedMusic = selectedMusic;
        this.alarmCondition = alarmCondition;
        this.alarmMessageText = alarmMessageText;
         this.selectedDeepSleepMusic = selectedDeepSleepMusic;
    }

    //constructor for CancelSetAlarm
     OnOffAlarm(Context alarmContext) {
        this.alarmContext = alarmContext;
    }

    private AlarmManager getAlarmManager (){
        return (AlarmManager) alarmContext.getSystemService(Context.ALARM_SERVICE);
    }

    private  Intent  GetAlarmIntentExtras(){
        Intent receiverIntent = new Intent(MainMathAlarm.ALARM_START_NEW);
        //sending the type of alarm 2(math)
        receiverIntent.putExtra("selectedMusic",selectedMusic)
                .putExtra("alarmMessageText",alarmMessageText)
                .putExtra("alarmComplexityLevel",alarmComplexityLevel)
                // sending the condition of alarm if true - alarm on , if false - alarm off
                .putExtra("alarmCondition", alarmCondition)
                .putExtra("selectedDeepSleepMusic",selectedDeepSleepMusic);
        return receiverIntent;
    }

     void SetNewAlarm() {
         if(ShowLogs.LOG_STATUS)ShowLogs.i( "OnOffAlarm" +"  SetNewAlarm");
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
            if(ShowLogs.LOG_STATUS)ShowLogs.i( "OnOffAlarm "+"h current: " + currentHour + " alarm hour: " + pickedHour + "  Today");
            if(ShowLogs.LOG_STATUS)ShowLogs.i( "OnOffAlarm "+"min current: " + currentMinute + " alarm min: " + pickedMinute + "  Today");
            //set the alarm manager
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        //All other cases  alarmManager.setInexactRepeating  AlarmManager.INTERVAL_DAY
        if (pickedHour < currentHour) {
            if(ShowLogs.LOG_STATUS)ShowLogs.i( "OnOffAlarm "+"h current: " + currentHour + " alarm hour: " + pickedHour + "Next Day");
            if(ShowLogs.LOG_STATUS)ShowLogs.i( "OnOffAlarm "+"min current: " + currentMinute + " alarm min: " + pickedMinute + "  Next Day");
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK) + 1);
            calendar.set(Calendar.HOUR_OF_DAY, pickedHour);
            calendar.set(Calendar.MINUTE, pickedMinute);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        if (pickedHour == currentHour) {
            if (pickedMinute < currentMinute) {
                if(ShowLogs.LOG_STATUS)ShowLogs.i( "OnOffAlarm "+"h current: " + currentHour + " alarm hour: " + pickedHour + " Next Day ");
                if(ShowLogs.LOG_STATUS)ShowLogs.i( "OnOffAlarm "+"min current: " + currentMinute + " alarm min: " + pickedMinute + "  Next Day");
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK) + 1);
                calendar.set(Calendar.HOUR_OF_DAY, pickedHour);
                calendar.set(Calendar.MINUTE, pickedMinute);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                if(ShowLogs.LOG_STATUS)ShowLogs.i( "OnOffAlarm "+"h current: " + currentHour + " alarm hour: " + pickedHour + "  Today");
                if(ShowLogs.LOG_STATUS)ShowLogs.i( "OnOffAlarm "+"min current: " + currentMinute + " alarm min: " + pickedMinute + "  Today");
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
    }

     void CancelSetAlarm() {
         if(ShowLogs.LOG_STATUS)ShowLogs.i( "OnOffAlarm" +"  CancelSetAlarm");
    try {
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(alarmContext, 0, new Intent(MainMathAlarm.ALARM_START_NEW), PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = getAlarmManager();
        alarmManager.cancel(cancelPendingIntent);
    }catch (Exception e){
        if(ShowLogs.LOG_STATUS)ShowLogs.i("cancel error" + e.getMessage());
    }
    }

    //snooze alarm for 5 minutes
     void SnoozeSetAlarm(int snoozeTime) {
         if(ShowLogs.LOG_STATUS)ShowLogs.i( "OnOffAlarm" +"  SnoozeSetAlarm");
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

        wakeLockIntent.putExtra("alarmTimeKey",MinuteHourConvertMethod(Calendar.HOUR_OF_DAY,Calendar.MINUTE));
        alarmContext.startService(wakeLockIntent);
    }

}

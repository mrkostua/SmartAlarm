package com.example.mathalarm.Alarms.MathAlarm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import com.example.mathalarm.CountsTimeToAlarmStart;
import com.example.mathalarm.firstsScreens.MainActivity;

 class MathAlarmPreview  {
    private int pickedHour, pickedMinute;
    private int selectedMusic;
    private String alarmMessageText;
    private int alarmComplexityLevel;
    private Context activityContext;

     MathAlarmPreview(Context activityContext,int pickedHour,int pickedMinute,int selectedMusic,
                            int alarmComplexityLevel,String alarmMessageText) {
        this.pickedHour = pickedHour;
        this.pickedMinute = pickedMinute;
        this.selectedMusic = selectedMusic;
        this.alarmComplexityLevel = alarmComplexityLevel;
        this.alarmMessageText = alarmMessageText;
        this.activityContext = activityContext;
    }

    //OnClick Method for Button - confirm (set Alarm on picked hour and minute)
      void ConfirmAlarmPreview_Method() {
        Log.i(MainMathAlarm.TAG, "MathAlarmPreview "+" ConfirmAlarmPreview_Method");
        OnOffAlarm onOffAlarm = new OnOffAlarm(activityContext,pickedHour,pickedMinute,alarmComplexityLevel,selectedMusic,true,alarmMessageText);
        onOffAlarm.SetNewAlarm();

        ShowToast_TimeToAlarmBoom();
        LastActions();
    }

    private void ShowToast_TimeToAlarmBoom() {
        // Method that shows calculate - How much time left until alarm start
        CountsTimeToAlarmStart countsTimeToAlarmStart = new CountsTimeToAlarmStart();
        countsTimeToAlarmStart.HowMuchTimeToStart(pickedHour,pickedMinute);

        Toast Toast_timeLeftToAlarmStart = Toast.makeText(activityContext, "   Until boom    "
                + countsTimeToAlarmStart.getResultHours() + " hours :"
                + countsTimeToAlarmStart.getResultMinutes() + "  minutes", Toast.LENGTH_LONG);
        Toast_timeLeftToAlarmStart.setGravity(Gravity.TOP | Gravity.START, 0, 0);
        Toast_timeLeftToAlarmStart.show();
    }

    private void LastActions() {
        //start service with PartialWakeLock
        Intent wakeLockIntent = new Intent(activityContext,WakeLockService.class);
        String time = pickedHour +" " + pickedMinute;
        wakeLockIntent.putExtra("alarmTimeKey",time);
        activityContext.startService(wakeLockIntent);

        //after confirming alarm configurations, user will be moved to main activity
        Intent intent  = new Intent(activityContext, MainActivity.class);
        activityContext.startActivity(intent);
    }

}


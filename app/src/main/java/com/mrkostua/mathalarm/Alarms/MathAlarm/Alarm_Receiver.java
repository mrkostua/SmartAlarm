package com.mrkostua.mathalarm.Alarms.MathAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.mrkostua.mathalarm.ShowLogs;
import com.mrkostua.mathalarm.firstsScreens.MainActivity;


public class Alarm_Receiver extends BroadcastReceiver
{
    private int selectedMusic,alarmComplexityLevel,selectedDeepSleepMusic ;
    private boolean alarmCondition;
    private String alarmMessageText;
    //Keys for SharedPreferences
    private static final String PEREFERENCEFileKey = "com.example.mathalarm.Alarms.MathAlarm.AlarmReceiverAlarmData";
    private static final String CONDITIONKey = "CONDITIONKey";
    private static final String MUSICKey = "MUSICKey";
    private static final String MESSAGETEXTKey = "MESSAGETEXTKey";
    private static final String COMPLEXITYLEVELKey = "COMPLEXITYLEVELKey";
    private static final String DEEPSLEEPMUSICKey = "DEEPSLEEPMUSICKey";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(ShowLogs.LOG_STATUS)ShowLogs.i( "Alarm_Receiver started");
        //snooze alarm for 5 minutes and stop current alarm
        if(intent.getAction().equals(MainMathAlarm.ALARM_SNOOZE_ACTION)) {
            //this stops the service no matter how many times it was started.
            context.stopService(new Intent(context,MathAlarmService.class));

            GetAlarmData(context);
            OnOffAlarm onOffAlarmSnooze = new OnOffAlarm(context,alarmComplexityLevel,selectedMusic,alarmCondition,alarmMessageText,selectedDeepSleepMusic);
            // snooze alarm for 5 minutes
            int snoozeTime =5;
            onOffAlarmSnooze.SnoozeSetAlarm(snoozeTime);

            //start MainMathActivity to hide DisplayAlarmActivity (because user had snooze alarm)
            Intent intent1 = new Intent(context,MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
        //dismiss last creating alarm and stop WakeLock service
        if(intent.getAction().equals(MainMathAlarm.ALARM_DISMISS_ACTION)) {
            OnOffAlarm onOffAlarmDisable = new OnOffAlarm(context);
            //disable alarm
            onOffAlarmDisable.CancelSetAlarm();

            //this stops the service no matter how many times it was started.
            context.stopService(new Intent(context,WakeLockService.class));
        }
        if(intent.getAction().equals(MainMathAlarm.ALARM_START_NEW)) {
             alarmCondition = intent.getExtras().getBoolean("alarmCondition",false);
             selectedMusic = intent.getExtras().getInt("selectedMusic",0);
             String defaultAlarmMessageText = "Good morning sir";
             alarmMessageText = intent.getExtras().getString("alarmMessageText",defaultAlarmMessageText);
             alarmComplexityLevel = intent.getExtras().getInt("alarmComplexityLevel",0);
            selectedDeepSleepMusic = intent.getIntExtra("selectedDeepSleepMusic",0);

            SaveAlarmData(context);
            startMathAlarmService(context);
        }
    }

    private void startMathAlarmService(Context context) {
                //An intent to the ringtone service
                Intent serviceIntent = new Intent(context, MathAlarmService.class);
                //serviceIntent.putExtra("Alarm condition", alarmCondition)
                serviceIntent.putExtra("selectedMusic", selectedMusic)
                        .putExtra("alarmMessageText", alarmMessageText)
                        .putExtra("alarmComplexityLevel", alarmComplexityLevel)
                        .putExtra("alarmCondition", alarmCondition)
                        .putExtra("selectedDeepSleepMusic",selectedDeepSleepMusic);
                context.startService(serviceIntent);
    }

    private void SaveAlarmData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PEREFERENCEFileKey,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(MUSICKey,selectedMusic);
        editor.putInt(COMPLEXITYLEVELKey,alarmComplexityLevel);
        editor.putBoolean(CONDITIONKey,alarmCondition);
        editor.putString(MESSAGETEXTKey,alarmMessageText);
        editor.putInt(DEEPSLEEPMUSICKey,selectedDeepSleepMusic);
        editor.apply();
    }

    private void GetAlarmData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PEREFERENCEFileKey,Context.MODE_PRIVATE);
        selectedMusic = sharedPreferences.getInt(MUSICKey,0);
        alarmComplexityLevel = sharedPreferences.getInt(COMPLEXITYLEVELKey,0);
        alarmCondition = sharedPreferences.getBoolean(CONDITIONKey,false);
        alarmMessageText = sharedPreferences.getString(MESSAGETEXTKey,null);
        selectedDeepSleepMusic = sharedPreferences.getInt(DEEPSLEEPMUSICKey,0);
    //clear data after receiving
        sharedPreferences.edit().clear(). apply();
    }


}

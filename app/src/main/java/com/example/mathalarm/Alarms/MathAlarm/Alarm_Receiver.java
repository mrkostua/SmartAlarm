package com.example.mathalarm.Alarms.MathAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


public class Alarm_Receiver extends BroadcastReceiver
{
    private int selectedMusic,alarmComplexityLevel;
    private boolean alarmCondition;
    private String alarmMessageText;
    //Keys for SharedPreferences
    private static final String TAG = "AlarmProcess";
    private static final String PEREFERENCEFileKey = "com.example.mathalarm.Alarms.MathAlarm.AlarmReceiverAlarmData";
    private static final String CONDITIONKey = "CONDITIONKey";
    private static final String MUSICKey = "MUSICKey";
    private static final String MESSAGETEXTKey = "MESSAGETEXTKey";
    private static final String COMPLEXITYLEVELKey = "COMPLEXITYLEVELKey";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Alarm_Receiver started");


        if(intent.getAction().equals(MainMathAlarm.ALARM_SNOOZE_ACTION)) {
            GetAlarmData(context);
             OnOffAlarm onOffAlarmSnooze = new OnOffAlarm(alarmComplexityLevel,selectedMusic,alarmCondition,alarmMessageText);
            // snooze alarm for 5 minutes
            int snoozeTime =5;
            onOffAlarmSnooze.SnoozeSetAlarm(snoozeTime);
        }
        if(intent.getAction().equals(MainMathAlarm.ALARM_SNOOZE_DISMISS)) {
            OnOffAlarm onOffAlarmDisable = new OnOffAlarm(context);
            //disable alarm
            onOffAlarmDisable.CancelSetAlarm();
        }
        if(intent.getAction().equals(MainMathAlarm.ALARM_START_NEW)) {
             alarmCondition = intent.getExtras().getBoolean("alarmCondition",false);
             selectedMusic = intent.getExtras().getInt("selectedMusic",0);
             String defaultAlarmMessageText = "Good morning sir";
             alarmMessageText = intent.getExtras().getString("alarmMessageText",defaultAlarmMessageText);
             alarmComplexityLevel = intent.getExtras().getInt("alarmComplexityLevel",0);

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
                        .putExtra("alarmCondition", alarmCondition);
                context.startService(serviceIntent);
    }
    private void SaveAlarmData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PEREFERENCEFileKey,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(MUSICKey,selectedMusic);
        editor.putInt(COMPLEXITYLEVELKey,alarmComplexityLevel);
        editor.putBoolean(CONDITIONKey,alarmCondition);
        editor.putString(MESSAGETEXTKey,alarmMessageText);
    }

    private void GetAlarmData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PEREFERENCEFileKey,Context.MODE_PRIVATE);
        selectedMusic = sharedPreferences.getInt(MUSICKey,0);
        alarmComplexityLevel = sharedPreferences.getInt(COMPLEXITYLEVELKey,0);
        alarmCondition = sharedPreferences.getBoolean(CONDITIONKey,false);
        alarmMessageText = sharedPreferences.getString(MESSAGETEXTKey,null);
    }

}

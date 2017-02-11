package com.example.mathalarm.Alarms.MathAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class Alarm_Receiver extends BroadcastReceiver
{
    private static final String TAG = "AlarmProcess";
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.i(TAG, "Alarm_Receiver started");

        Boolean alarmCondition = intent.getExtras().getBoolean("alarmCondition",false);
        int alarmName = intent.getExtras().getInt("Alarm name",0);
        int selectedMusic = intent.getExtras().getInt("selectedMusic",0);
        String defaultAlarmMessageText = "Good morning sir";
        String alarmMessageText = intent.getExtras().getString("alarmMessageText",defaultAlarmMessageText);
        int alarmComplexityLevel = intent.getExtras().getInt("alarmComplexityLevel",0);

        switch (alarmName) {
            //MathAlarm
            case 2: {
                //An intent to the ringtone service
                Intent serviceIntent = new Intent(context,MathAlarmService.class);
                //serviceIntent.putExtra("Alarm condition", alarmCondition)
                serviceIntent.putExtra("selectedMusic",selectedMusic)
                             .putExtra("alarmMessageText",alarmMessageText)
                             .putExtra("alarmComplexityLevel",alarmComplexityLevel)
                             .putExtra("alarmCondition",alarmCondition);
                context.startService(serviceIntent);
            }break;
        }

    }
}

package com.example.mathalarm.Alarms.MathAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
* @see android.support.v4.content.WakefulBroadcastReceiver  Helper for the common pattern of implementing a BroadcastReceiver that receives a device wakeup event
 and then passes the work off to a Service, while ensuring that the device does not go back to sleep during the transition.
This class takes care of creating and managing a partial wake lock for you; you must request the WAKE_LOCK permission to use it.
 */
public class Alarm_Receiver extends BroadcastReceiver
{
    private static final String TAG = "AlarmProcess";
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.i(TAG, "Alarm_Receiver started");

        //Maintain a cpu partial WakeLock until the MathAlarmService
        // (service fr playing music) can pick it up.
        AlarmPartialWakeLock.acquireCpuWakeLock(context);

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

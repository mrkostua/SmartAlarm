package com.mrkostua.mathalarm;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Администратор on 21.11.2017.
 */

public class LastAlarmData {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public LastAlarmData(Context context){
        sharedPreferences = context.getSharedPreferences(ConstantValues.ALARM_SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void saveLastAlarmData(AlarmObject alarmObject){
        editor.putInt(ConstantValues.LAST_ALARM_HOURS,alarmObject.getHours());
        editor.putInt(ConstantValues.LAST_ALARM_MINUTES,alarmObject.getMinutes());
        editor.apply();
    }

}

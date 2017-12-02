package com.mrkostua.mathalarm;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Kostiantyn on 21.11.2017.
 */

public class LastAlarmData {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static int hours;
    private static int minutes;

    public LastAlarmData(Context context){
        sharedPreferences = context.getSharedPreferences(ConstantValues.INSTANCE.getALARM_SHARED_PREFERENCE_NAME(),Context.MODE_PRIVATE);

    }

    public int getAlarmHours(){
        return sharedPreferences.getInt(ConstantValues.INSTANCE.getLAST_ALARM_HOURS(),0);
    }

    public int getAlarmMinutes(){
        return sharedPreferences.getInt(ConstantValues.INSTANCE.getLAST_ALARM_MINUTES(),0);
    }

    public void saveLastAlarmData(AlarmObject alarmObject){
        editor.putInt(ConstantValues.INSTANCE.getLAST_ALARM_HOURS(),alarmObject.getHours());
        editor.putInt(ConstantValues.INSTANCE.getLAST_ALARM_MINUTES(),alarmObject.getMinutes());
        editor.apply();
    }

}

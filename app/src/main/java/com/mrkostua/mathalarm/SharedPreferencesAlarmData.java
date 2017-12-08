package com.mrkostua.mathalarm;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Kostiantyn on 21.11.2017.
 */
//todo learn KOtlin companion object
public class SharedPreferencesAlarmData {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static int hours;
    private static int minutes;
    private static String message;


    public SharedPreferencesAlarmData(Context context) {
        sharedPreferences = context.getSharedPreferences(ConstantValues.INSTANCE.getALARM_SHARED_PREFERENCE_NAME(), Context.MODE_PRIVATE);

    }

    public int getAlarmHours() {
        return sharedPreferences.getInt(ConstantValues.INSTANCE.getLAST_ALARM_HOURS(), -1);
    }

    public int getAlarmMinutes() {
        return sharedPreferences.getInt(ConstantValues.INSTANCE.getLAST_ALARM_MINUTES(), -1);
    }

    public String getAlarmTextMessage() {
        return sharedPreferences.getString(ConstantValues.INSTANCE.getSHARED_PREFERENCES_KEY_TEXT_MESSAGE(), ConstantValues.INSTANCE.getCUSTOM_ALARM_TEXT_MESSAGE());
    }

    // TODO: 08.12.2017 compile app whiten in Kotlin (some problems with generating method and null safety)
    public void saveLastAlarmData(AlarmObject alarmObject) {
        editor.putInt(ConstantValues.INSTANCE.getLAST_ALARM_HOURS(),
                alarmObject.getHours() != -1 ? alarmObject.getHours() : getAlarmHours());
        editor.putInt(ConstantValues.INSTANCE.getLAST_ALARM_MINUTES(),
                alarmObject.getMinutes() != -1 ? alarmObject.getMinutes() : getAlarmMinutes());
        editor.putString(ConstantValues.INSTANCE.getLAST_ALARM_MINUTES(),
                alarmObject.getTextMessage().equals(ConstantValues.INSTANCE.getCUSTOM_ALARM_TEXT_MESSAGE()) ? alarmObject.getTextMessage() : getAlarmTextMessage());

        editor.apply();
    }

}

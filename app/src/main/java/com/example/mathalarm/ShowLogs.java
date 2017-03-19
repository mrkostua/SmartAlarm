package com.example.mathalarm;

import android.util.Log;
import com.example.mathalarm.Alarms.MathAlarm.MainMathAlarm;

/** @author Konstantyn
 * Created by Администратор on 14.03.2017.
 */

public  class ShowLogs {
    private static final String TAG = "MathAlarm";

    public static boolean LOG_STATUS= MainMathAlarm.LOG_DEBUG_STATUS ? true : false;

    public static void i(String logMessage) {
        Log.i(TAG," "+logMessage);
    }
}

package com.mrkostua.mathalarm;

import android.util.Log;

/** @author Konstantyn
 * Created by Администратор on 14.03.2017.
 */

public  class ShowLogsOld {
    private static final String TAG = "MathAlarm";

    public static boolean LOG_STATUS= true;

    public static void i(String logMessage) {
        Log.i(TAG," "+logMessage);
    }
}

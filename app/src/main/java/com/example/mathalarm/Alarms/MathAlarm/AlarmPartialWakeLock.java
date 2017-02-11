package com.example.mathalarm.Alarms.MathAlarm;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;
  class AlarmPartialWakeLock {
    private static PowerManager.WakeLock  sCpuWakeLock;
    private static final String TAG = "AlarmProcess";

        static void acquireCpuWakeLock (Context context) {
        //Check if WakeLock is acquired
    if(sCpuWakeLock != null) {
        Log.i(TAG," AlarmPartialWakeLock acquireCpuWakeLock already set");
        return;
    }
            Log.i(TAG," AlarmPartialWakeLock acquireCpuWakeLock started");
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        sCpuWakeLock = powerManager.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK |
                        PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.ON_AFTER_RELEASE,TAG);
        sCpuWakeLock.acquire();
    }
        static void releaseCpuWakeLock() {
            if(sCpuWakeLock!=null) {
                sCpuWakeLock.release();
                Log.i(TAG," AlarmPartialWakeLock releaseCpuWakeLock ");
                sCpuWakeLock = null;
            }
            Log.i(TAG," AlarmPartialWakeLock releaseCpuWakeLock == null nothing to stop ");
        }

}

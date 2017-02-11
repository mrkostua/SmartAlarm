package com.example.mathalarm.Alarms.MathAlarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;


public class WakeLockService extends Service
{
    private final String TAG = "AlarmProcess";
     private PowerManager.WakeLock fullWakeLock;
     private PowerManager.WakeLock partialWakeLock;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"WakeLockService  onStartCommand");
        // Called implicitly when device is about to sleep or application is backgrounded
            createWakeLocks();
            wakeDevice("partialWakeLock");

        /**if this service's process is killed while it is started.Later the system will try to re-create the service.
         * This mode makes sense for things that will be explicitly started and stopped
        * to run for arbitrary periods of time, such as a service performing background music playback. */
        return START_STICKY;
    }

    private void ReleaseWakeLocks() {
        if(fullWakeLock.isHeld()){
            fullWakeLock.release();
            fullWakeLock = null;
            Log.i(TAG,"WakeLockService  fullWakeLock released");
        }
        if(partialWakeLock.isHeld()){
            partialWakeLock.release();
            partialWakeLock = null;
            Log.i(TAG,"WakeLockService  partialWakeLock released");
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG,"WakeLockService "+" onDestroy");
        ReleaseWakeLocks();
        stopSelf();
    }

    protected void createWakeLocks(){
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        partialWakeLock=null; fullWakeLock=null;
        fullWakeLock = powerManager.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "Loneworker - FULL WAKE LOCK");
        //Wake lock flag: Turn the screen on when the wake lock is acquired.
        partialWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK|PowerManager.ACQUIRE_CAUSES_WAKEUP, "Loneworker - PARTIAL WAKE LOCK");
    }

    // Called whenever we need to wake up the device
    private void wakeDevice(String wakeLockType) {
        //keyguardLock.disableKeyguard();
        switch (wakeLockType) {

            case "partialWakeLock":
                /*Wake lock level: Ensures that the CPU is running; the screen and keyboard backlight will be allowed to go off.
                *If the user presses the power button, then the screen will be turned off but the
                 CPU will be kept on until all partial wake locks have been released.*/
                partialWakeLock.acquire();
                Log.i(TAG,"WakeLockService   partialWakeLock.acquire()");
                break;

            /*This constant was deprecated in API level 17.
            Most applications should use FLAG_KEEP_SCREEN_ON instead of this type of wake lock, as it will be
            correctly managed by the platform as the user moves between applications and doesn't require a special permission.
            Wake lock level: Ensures that the screen and keyboard backlight are on at full brightness.
            If the user presses the power button, then the FULL_WAKE_LOCK will be implicitly released by the system,
            causing both the screen and the CPU to be turned off. Contrast with PARTIAL_WAKE_LOCK.*/
            case "fullWakeLock":
                fullWakeLock.acquire();
                Log.i(TAG,"WakeLockService   fullWakeLock.acquire()");
                break;
        }
    }
}

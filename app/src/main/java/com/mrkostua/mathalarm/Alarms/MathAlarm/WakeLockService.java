package com.mrkostua.mathalarm.Alarms.MathAlarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.mrkostua.mathalarm.ShowLogsOld;

public class WakeLockService extends Service {
    private static final int  NOTIFICATION_ID = 25;
    private PowerManager.WakeLock partialWakeLock;
    private AlarmNotifications alarmNotifications = new AlarmNotifications();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("WakeLockService  onStartCommand");
        // Called implicitly when device is about to sleep or application is backgrounded
            createWakeLocks();
            wakeDevice("partialWakeLock");

        String timeToAlarmStart = intent.getExtras().getString("alarmTimeKey","00-00");
       startForeground(NOTIFICATION_ID,alarmNotifications.NewNotification(this,timeToAlarmStart));

        /**if this service's process is killed while it is started.Later the system will try to re-create the service.
         * This mode makes sense for things that will be explicitly started and stopped
        * to run for arbitrary periods of time, such as a service performing background music playback. */
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("WakeLockService "+" onDestroy");
        ReleaseWakeLocks();
        stopSelf();
        alarmNotifications.CancelNotification(this,NOTIFICATION_ID);
    }

    protected void createWakeLocks(){
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        partialWakeLock=null;
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
                if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("WakeLockService   partialWakeLock.acquire()");
                break;
        }
    }
    private void ReleaseWakeLocks() {
        if(partialWakeLock.isHeld()){
            partialWakeLock.release();
            partialWakeLock = null;
            if(ShowLogsOld.LOG_STATUS) ShowLogsOld.i("WakeLockService  partialWakeLock released");
        }
    }
}

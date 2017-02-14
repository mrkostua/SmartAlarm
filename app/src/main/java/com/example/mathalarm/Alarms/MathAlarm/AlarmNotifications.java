package com.example.mathalarm.Alarms.MathAlarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import com.example.mathalarm.R;

/**
 * Created by Администратор on 13.02.2017.
 */

  class AlarmNotifications {

    //notification for MathService
    Notification NewNotification(Context context) {
        // snooze button
        Intent snoozeIntent = new Intent(context,Alarm_Receiver.class);
        snoozeIntent.setAction(MainMathAlarm.ALARM_SNOOZE_ACTION);
        PendingIntent piSnooze = PendingIntent.getBroadcast(context,0,snoozeIntent,0);
        //dismiss button
        Intent dismissIntent = new Intent(context,Alarm_Receiver.class);
        dismissIntent.setAction(MainMathAlarm.ALARM_SNOOZE_DISMISS);
        PendingIntent piDismiss = PendingIntent.getBroadcast(context,0,dismissIntent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder

                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentText("Touch to stop alarm")
                .setContentTitle("MathAlarm")
                .setSmallIcon(R.drawable.ic_av_timer_white_24dp)
                .setTicker("MathAlarm touch to stop")
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.logo_math_alarm))
                .setWhen(System.currentTimeMillis())
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

                .setAutoCancel(true);
        Notification notification = builder.build();
        return notification;
    }
    //notification for WakeLock Service
    Notification NewNotification(Context context, String time) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentText("Alarm was set on " +time)
                .setContentTitle("MathAlarm")
                .setSmallIcon(R.drawable.ic_av_timer_white_24dp)
                .setTicker("Alarm was set on " + time)

                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.logo_math_alarm))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setWhen(System.currentTimeMillis());
        Notification notification = builder.build();
        return notification;
    }
    void CancelNotification(Context context, int notificationID) {
        NotificationManager notificationManager = getNotificationManager(context);
        notificationManager.cancel(notificationID);
    }

    private NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

}
package com.example.mathalarm.Alarms.MathAlarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import com.example.mathalarm.R;

  class AlarmNotifications {

    //notification for MathService
    Notification NewNotification(Context context) {
        // snooze button
        Intent snoozeIntent = new Intent(MainMathAlarm.ALARM_SNOOZE_ACTION);
        PendingIntent piSnooze = PendingIntent.getBroadcast(context,0,snoozeIntent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentText(context.getString(R.string.AN_snoozeContentText))
                .setContentTitle(context.getString(R.string.AN_snoozeContentTitle))
                .setSmallIcon(R.drawable.ic_alarm_white_36dp)
                .setTicker(context.getString(R.string.AN_snoozeTicker))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.alarm_icon_test))
                .setWhen(System.currentTimeMillis())
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(R.drawable.ic_snooze_white_36dp,context.getString(R.string.AN_snoozeAction),piSnooze)
                .setAutoCancel(true);
        return builder.build();
    }

    //notification for WakeLock Service
    Notification NewNotification(Context context, String time) {
        //dismiss button
        Intent dismissIntent = new Intent(MainMathAlarm.ALARM_DISMISS_ACTION);
        PendingIntent piDismiss = PendingIntent.getBroadcast(context,0,dismissIntent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentText(context.getString(R.string.AN_dismissContentText,time))
                .setContentTitle(context.getString(R.string.AN_dismissContentTitle))
                .setSmallIcon(R.drawable.ic_alarm_white_36dp)
                .setTicker(context.getString(R.string.AN_dismissContentText,time))
                .addAction(R.drawable.ic_alarm_off_white_36dp,context.getString(R.string.AN_dismissActionText),piDismiss)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.alarm_icon_test))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setWhen(System.currentTimeMillis());
        return builder.build();
    }
    void CancelNotification(Context context, int notificationID) {
        NotificationManager notificationManager = getNotificationManager(context);
        notificationManager.cancel(notificationID);
    }

    private NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

}

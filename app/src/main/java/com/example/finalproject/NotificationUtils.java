package com.example.finalproject;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationUtils extends ContextWrapper{
    private NotificationManager _notificationManager;
    private Context _context;


    public NotificationUtils(Context base)
    {
        super(base);
        _context = base;
        createChannel();
    }

    public NotificationCompat.Builder setNotification(String title, String body)
    {
        return new NotificationCompat.Builder(this, "todo")
                .setSmallIcon(R.drawable.ic_tdt)
                .setContentTitle("Incoming Task: "+title)
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    private void createChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("todo2", "todo name2", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager().createNotificationChannel(channel);
        }
    }

    public NotificationManager getManager()
    {
        if(_notificationManager == null)
        {
            _notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return _notificationManager;
    }

    public void setReminder(long timeInMillis, String title, String description, String projectKey)
    {
        Intent _intent = new Intent("sendNotification");
        _intent.setClass(_context, ReminderBroadcast.class);
        _intent.putExtra("title", title);
        _intent.putExtra("description", description);
        _intent.putExtra("projectKey", projectKey);
        PendingIntent _pendingIntent = PendingIntent.getBroadcast(_context, 0, _intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager _alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        _alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, _pendingIntent);
    }
}

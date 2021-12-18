package com.example.finalproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationUtils _notificationUtils = new NotificationUtils(context);
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        NotificationCompat.Builder _builder = _notificationUtils.setNotification(title, description);
        _notificationUtils.getManager().notify(102, _builder.build());
    }
}

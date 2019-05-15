package com.danieldk.brewuappassignment2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class BrewService extends Service {

    private static final String CHANNEL = "myChannel";
    private static final int NOTIFICATION_ID = 1;

    public BrewService() {}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SendNotification();
        return START_NOT_STICKY;
    }

    public void SendNotification()
    {
        // Inspireret af ServicesDemo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL, "channelName", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification =
                new NotificationCompat.Builder(this, CHANNEL)
                        .setSmallIcon(R.drawable.pint)
                        .setContentTitle(getResources().getString(R.string.notificationTitle))
                        .setContentText(getResources().getString(R.string.notificationText))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setChannelId(CHANNEL)
                        .build();

        startForeground(NOTIFICATION_ID,notification);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

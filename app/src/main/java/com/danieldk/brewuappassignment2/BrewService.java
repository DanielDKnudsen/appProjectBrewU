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
import com.danieldk.brewuappassignment2.Models.Brew;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class BrewService extends Service {

    private static final String CHANNEL = "myChannel";
    private static final int NOTIFICATION_ID = 1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public BrewService() {}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ListenToBrew();
        return START_NOT_STICKY;
    }

    public void SendNotification(Brew brew)
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
                        .setContentText(brew.getTitle()+" " + getResources().getString(R.string.notificationText) +" " + brew.getAvgRating())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setChannelId(CHANNEL)
                        .build();

        startForeground(NOTIFICATION_ID,notification);
    }

    public void ListenToBrew() {
        db.collection("Brews")
                .whereEqualTo("userId", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            System.err.println("Listen failed: " + e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case MODIFIED:
                                    Brew brew = dc.getDocument().toObject(Brew.class);
                                    SendNotification(brew);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

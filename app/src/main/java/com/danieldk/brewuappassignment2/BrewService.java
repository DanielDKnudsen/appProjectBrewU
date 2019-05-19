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

import java.util.Date;
import java.util.concurrent.TimeUnit;

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
        // Inspirered by ServicesDemo
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

    // When a document in our database for "Brews" is edited we want to notify the user who created the brew: This method does that
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
                        // Create new brew to make sure we only notify once even if same document is changed several times
                        Brew brew = new Brew();
                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            if (dc.getDocument().toObject(Brew.class) != brew)
                            {
                                brew = dc.getDocument().toObject(Brew.class);
                                switch (dc.getType()) {
                                    case MODIFIED:
                                        // Check if we already notified the brew. Also not the ideal way
                                        if (brew.getCreationDate() == null) {
                                            SendNotification(brew);
                                        }
                                        // Check if brew is just created in last 3 seconds - if so, dont notify user. This is not the ideal way, but is works
                                        else if (!(TimeUnit.MILLISECONDS.toSeconds(brew.getCreationDate().getTime())+3 > TimeUnit.MILLISECONDS.toSeconds(new Date().getTime())))
                                        {
                                            SendNotification(brew);
                                        }
                                        break;
                                    default:
                                        break;
                                }
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

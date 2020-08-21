package com.intactaengenharia.intacta.Utilities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.intactaengenharia.intacta.R;
import com.intactaengenharia.intacta.Splash;

import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;

public class FCMService extends FirebaseMessagingService {
    public FCMService() {
    }





    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());


        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Intent intent = new Intent(this, Splash.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            System.out.println("notification " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            String CHANNEL_ID = "enginer";
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                AudioAttributes att = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .build();
                CharSequence name = "Intacta Engenharia";
                String description = "Canal de notificações intacta engenharia";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);
                channel.enableVibration(true);
                channel.setSound(alarmSound,att);

                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);

            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_ic_notification)
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                        .setSound(alarmSound)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getTitle()))
                        .setChannelId(CHANNEL_ID)
                        .setAutoCancel(true);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(5, builder.build());


        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

    }

    @Override
    public void onNewToken(String s) {
        if (s != null) {
            getSharedPreferences("userpreferences", Context.MODE_PRIVATE)
                    .edit().putString("token",s).apply();
            Toast.makeText(getBaseContext(),"Dispositivo recebendo notificações",Toast.LENGTH_LONG).show();

            super.onNewToken(s);
        }else{
            //System.out.println(Preferences.ANSI_RED +  "No token provided");
        }

    }
}

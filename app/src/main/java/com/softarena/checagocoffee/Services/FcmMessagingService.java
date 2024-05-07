package com.softarena.checagocoffee.Services;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.softarena.checagocoffee.Acitivity.Home.MainMenuDrawerActivity;
import com.softarena.checagocoffee.Acitivity.Orders.OrderHistoryActivity;
import com.softarena.checagocoffee.R;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class FcmMessagingService extends FirebaseMessagingService {
    Context context = this;
    private static final String ADMIN_CHANNEL_ID ="admin_channel";
    private NotificationManager notificationManager;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        SharedPreferences sp = getSharedPreferences("chichago", MODE_PRIVATE);
        sp.edit().putString("fcm_token",s ).apply();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("body");
        String type = remoteMessage.getData().get("type");


       // Uri sound = Uri.parse("android.resource://com.softarena.checagocoffee/" + R.raw.notification);
        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.notification);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {

            Intent callIntent;
            if (type.equals("Promotional")){
                callIntent = new Intent(getApplicationContext(), MainMenuDrawerActivity.class);
                callIntent.putExtra("from","notiif");
            }else {
                callIntent = new Intent(getApplicationContext(), OrderHistoryActivity.class);


            }
            callIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, callIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder nBuilder= new NotificationCompat.Builder(this);
            nBuilder.addAction( R.drawable.common_google_signin_btn_icon_dark, title,pendingIntent);
            nBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
            nBuilder.setAutoCancel(false);
            nBuilder.setContentTitle(title+" - Gizah App");
            nBuilder.setContentText(message);
            nBuilder.setVibrate(new long[] { 1000, 1000});
//            nBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            nBuilder.setSound(sound);
            nBuilder.setSmallIcon(R.drawable.loginlogo);
            nBuilder.setContentIntent(pendingIntent);
            NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(title,1,nBuilder.build());

        }else{



            notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            //Setting up Notification channels for android O and above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setupChannels(sound);
            }
            int notificationId = new Random().nextInt(60000);
            Intent callIntent;
            if (type.equals("Promotional")){
                callIntent = new Intent(getApplicationContext(), MainMenuDrawerActivity.class);
                callIntent.putExtra("from","notiif");

            }else {
                callIntent = new Intent(getApplicationContext(), OrderHistoryActivity.class);

            }
            callIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, callIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)

                    .setSmallIcon(R.drawable.loginlogo)  //a resource for your custom small icon
                    .setContentTitle(title+" - Gizah App") //the "title" value you sent in your notification
                    .setContentText(message) //ditto
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
//dismisses the notification on click
//                    .setSound(defaultSoundUri).addAction( R.drawable.loginlogo, "Check",pendingIntent);
                    .setSound(sound).addAction( R.drawable.loginlogo, "Check",pendingIntent);

            notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(notificationId, notificationBuilder.build());
        }
        super.onMessageReceived(remoteMessage);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(Uri sound){
        CharSequence adminChannelName = getString(R.string.app_name);
        String adminChannelDescription = getString(R.string.app_name);
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setSound(sound,attributes);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
}
package com.det.skillinvillage;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//import com.android.sripad.leadnew_22_6_2018.R;

//import static java.lang.System.lineSeparator;


/**
 * Created by User on 7/18/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService
{

    private static final String TAG = "MyFirebaseMsgService";
    Bitmap url2bitmap=null;

    String str_titlewhr2go;

    NotificationCompat.Builder notificationBuilders;

    Intent intent;

    String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        // Create and show notification
        //Log.d(TAG, "From: " + remoteMessage.getFrom());
        //Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        notificationBuilders = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        Log.e(TAG, "From: " + remoteMessage.getData().get("title"));


        str_titlewhr2go =  remoteMessage.getData().get("title").trim();
        Log.e("notitle","notitle:"+ str_titlewhr2go);
        if(str_titlewhr2go.equals("empty")||str_titlewhr2go.equals("Empty")||str_titlewhr2go==null ||str_titlewhr2go==""||str_titlewhr2go.isEmpty())
        { str_titlewhr2go="empty";}
        else {    }

        sendNotification(remoteMessage.getData().get("message").trim());

    }

    @SuppressLint("NewApi")
    private void sendNotification(String messageBody)
    {
        Log.e("MessageBoby: ", " " + messageBody);

        if (messageBody == null || messageBody == "" || messageBody.isEmpty()) {
            Log.e("empty:", "Empty");
        } else
         {
            Log.e("else: ", " " + messageBody);

             {

                 intent = new Intent(this, Activity_HomeScreen.class);

//                 intent = new Intent(this, CalenderActivity.class);

                 PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                 int id = (int) System.currentTimeMillis();
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)

//Forth Comming Scheduler for Cluster: SHIGGAON, Institute: TEST, Level: Level-3. For the Session 05:30 PM To 07:30 PM
                /*.setSmallIcon(R.mipmap.ic_launcher_round)*/
            //notificationBuilders
                        .setSmallIcon(R.drawable.appicon)
                        .setContentTitle("Skill In Village")
                      .setStyle(new NotificationCompat.BigTextStyle()
                              .bigText(messageBody))
                        .setPriority(id)
                        //.setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(id /* ID of notification */, notificationBuilder.build());

        }

    }

    }


    public Bitmap getBitmapfromUrl(String imageUrl)
    {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }


}// end of class


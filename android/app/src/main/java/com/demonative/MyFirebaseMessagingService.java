package com.demonative;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.internal.FidListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    /*
   https://firebase.google.com/docs/cloud-messaging/android/client?authuser=0
   The registration token may change when:
   - The app is restored on a new device
   - The user uninstalls/reinstall the app
   - The user clears app data. */

    private static final String TAG = "#MyFirebaseMessagingService";

    public MyFirebaseMessagingService() {
    }

    void register() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.d("loi", task.getException().toString());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d("TOKEN", token);
                    }
                });
    }

    @Override
    public void onNewToken(@NonNull String s) {
        Log.d("Refreshed token: ", s);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            String msgTitle = remoteMessage.getNotification().getTitle();
            String msgBody = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Message Notification Body: " + msgBody);
            sendNotification(msgTitle, msgBody, null);
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void sendNotification(String msgTitle, String messageBody, Map<String, String> fcmData) {
        Intent intent = new Intent(this, MainActivity.class);
//        if (fcmData.size() > 0) {
//            if (fcmData.containsKey("screen")) {
//                String screen = fcmData.get("screen");
//                String props = fcmData.get("props");
//                MrBenModule.dataScreen = screen;
//                MrBenModule.dataProps = props;
//                intent.putExtra("screen", screen);
//                intent.putExtra("props", props);
//               /* WritableMap storedMap = Arguments.createMap();
//                storedMap.putString("screen", screen);
//                storedMap.putString("props", props);
//                sendEvent(MrBenModule.reactContext, "FCM.onMessageReceived", storedMap);*/
//            }
//        }

        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                (new Random()).nextInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String channelId = "demonative";
        String fcm_message = "demonative";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                        .setContentTitle(msgTitle)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}

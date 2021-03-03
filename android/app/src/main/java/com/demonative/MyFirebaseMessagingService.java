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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    /**
     * https://firebase.google.com/docs/cloud-messaging/android/client?authuser=0
     * The registration token may change when:
     * - The app is restored on a new device
     * - The user uninstalls/reinstall the app
     * - The user clears app data.
     */

    private static final String TAG = "#MyFirebaseMessagingService";
    private String token = "";

    // send message
    private String msgTitle = "";
    private String msgBody = "";

    // send notication

    private static MyFirebaseMessagingService instance;

    public MyFirebaseMessagingService() {
    }

    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    public static synchronized MyFirebaseMessagingService getInstance() {
        if (instance == null) {
            instance = new MyFirebaseMessagingService();
        }
        return instance;
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
                        setToken(task.getResult());
                        Log.d("TOKEN", token);
                    }
                });
    }

    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    @Override
    public void onNewToken(@NonNull String newToken) {
        setToken(newToken);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        System.out.print("Đã vào onMessageReceived");

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            String body = remoteMessage.getData().get("body");
            String title = remoteMessage.getData().get("title");
            Log.d("Send message: ", body + " " + title);

            setMsgBody(remoteMessage.getData().get("body"));
            setMsgTitle(remoteMessage.getData().get("title"));
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
            Log.d("Send notification: ", msgTitle + " " + msgBody);
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onSendError(@NonNull String s, @NonNull Exception e) {
        super.onSendError(s, e);
    }
}

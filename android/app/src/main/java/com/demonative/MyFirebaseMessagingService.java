package com.demonative;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

//    https://firebase.google.com/docs/cloud-messaging/android/client?authuser=0
//    The registration token may change when:
//   - The app is restored on a new device
//   - The user uninstalls/reinstall the app
//   - The user clears app data.


    public MyFirebaseMessagingService() {
    }

    void getToken() {
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

//                       // Log and toast
//                       String msg = getString(R.string.msg_token_fmt, token);
//                       Log.d(TAG, msg);
//                       Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onNewToken(@NonNull String s) {
        Log.d("Refreshed token: ", s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("onMessageReceived", "From: " + remoteMessage.getFrom());
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(@NonNull String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(@NonNull String s, @NonNull Exception e) {
        super.onSendError(s, e);
    }

    @Override
    protected Intent getStartCommandIntent(Intent intent) {
        return super.getStartCommandIntent(intent);
    }

    @Override
    public boolean handleIntentOnMainThread(Intent intent) {
        return super.handleIntentOnMainThread(intent);
    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
    }
}

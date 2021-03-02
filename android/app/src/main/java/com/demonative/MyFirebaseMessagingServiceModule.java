package com.demonative;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class MyFirebaseMessagingServiceModule extends ReactContextBaseJavaModule {

    Context context;


    MyFirebaseMessagingServiceModule(ReactApplicationContext mContext) {
        super(mContext);
        context = mContext;
    }

    @NonNull
    @Override
    public String getName() {
        return "MyFirebaseMessagingServiceModule";
    }

    @ReactMethod
    public void onClickEvent() {
        Toast.makeText(context, "click", Toast.LENGTH_LONG).show();
    }


    @ReactMethod
    public void onClickSendParamsEvent(String data, Promise promise) {
        try {
            Integer eventId = 200;
            promise.resolve(eventId);
        } catch (Exception e) {
            promise.reject(e);
        }
    }


    @ReactMethod
    public void getTokenFCM(Promise promise) {
//        MyFirebaseMessagingService myFirebaseMessagingService = new MyFirebaseMessagingService();
//        System.out.println("GGG:"+ myFirebaseMessagingService.getToken());
        try {
            MyFirebaseMessagingService myFirebaseMessagingService = MyFirebaseMessagingService.getInstance();
            System.out.println("GGG:" + myFirebaseMessagingService.getToken());
            promise.resolve(myFirebaseMessagingService.getToken());
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void onMessageReceivedFCM(Promise promise) {
        try {

        } catch (Exception e) {
            promise.reject(e);
        }
    }

}

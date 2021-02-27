package com.demonative;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class CalendarModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

    CalendarModule(ReactApplicationContext context) {
        super(context);
        context.addLifecycleEventListener(this);
    }

    @NonNull
    @Override
    public String getName() {
        return "CalendarModule";
    }

    @ReactMethod
    public void createCalendarEvent(String name, String location) {
        Log.d("CalendarModule", "Create event called with name: " + name + " and location: " + location);
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("DEFAULT_EVENT_NAME", "New Event");
        return constants;
    }

    @ReactMethod
    public void createCalendarCallBackEvent(String name, String location, Callback callBack) {
        Integer eventId = 1000;
        callBack.invoke(eventId);
    }

    @ReactMethod
    public void createCalendarPromiseEvent(String name, String location, Promise promise) {
        try {
            Integer eventId = 2000;
            promise.resolve(eventId);
        } catch (Exception e) {
            promise.reject("Create Event Error", e);
        }
    }

    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    @Override
    public void onHostResume() {
        // Activity `onResume`
        Log.d("VONGDOI", "onResume");
    }

    @Override
    public void onHostPause() {
        // Activity `onPause`
        Log.d("VONGDOI", "onPause");
    }

    @Override
    public void onHostDestroy() {
        // Activity `onDestroy`
        Log.d("VONGDOI", "onDestroy");
    }
}
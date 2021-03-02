package com.demonative;

import android.content.Context;

public class MyFCMSingleton {

    private static MyFCMSingleton instance;
    private static Context ctx;

    private MyFCMSingleton() {

    }

    public static synchronized MyFCMSingleton getInstance() {
        if (instance == null) {
            instance = new MyFCMSingleton();
        }
        return instance;
    }
}

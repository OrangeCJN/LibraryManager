package com.example;

import android.app.Application;

import com.google.firebase.FirebaseApp;

/**
 * global App class，for initialization use
 */
public class App extends Application {

    // App singleton
    private static App sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        FirebaseApp.initializeApp(this); // initialize Firebase
    }

    /**
     * get App singleton
     * @return
     */
    public static App getApp() {
        return sApp;
    }
}

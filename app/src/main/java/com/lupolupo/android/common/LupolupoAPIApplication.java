package com.lupolupo.android.common;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

/**
 * @author Ritesh Shakya
 */
public class LupolupoAPIApplication extends Application {
    private static LupolupoAPIApplication instance;

    public static LupolupoAPIApplication get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
        LupolupoAPIApplication.instance = this;
        // Setup handler for uncaught exceptions.
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                handleUncaughtException(thread, e);
            }
        });
    }

    public boolean isUIThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public void handleUncaughtException(Thread thread, Throwable e) {
        e.printStackTrace(); // not all Android versions will print the stack trace automatically

        if (isUIThread()) {
            invokeLogActivity();
        } else {  //handle non UI thread throw uncaught exception
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    invokeLogActivity();
                }
            });
        }
    }

    private void invokeLogActivity() {
        Intent intent = new Intent();
        intent.setAction("com.lupulupo.SEND_LOG");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
        startActivity(intent);

        System.exit(1); // kill off the crashed app
    }
}

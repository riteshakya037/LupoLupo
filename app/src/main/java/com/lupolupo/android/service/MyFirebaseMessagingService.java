package com.lupolupo.android.service;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.lupolupo.android.BuildConfig;
import com.lupolupo.android.common.NotificationUtils;
import com.lupolupo.android.controllers.retrofit.LupolupoHTTPManager;
import com.lupolupo.android.model.UserInfo;
import com.lupolupo.android.views.activities.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;

/**
 * @author Ritesh Shakya
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        if (NotificationPref.with(LupolupoAPIApplication.get()).getBoolean()) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        notificationResponse();
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
//        }
    }

    private Task<Void> notificationResponse() {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        Tasks.call(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                UserInfo info = new UserInfo();
                if (BuildConfig.FLAVOR.equals("staging")) {
                    tcs.setResult(null);
                } else {
                    info.getInfo().onSuccess(new Continuation<UserInfo, Object>() {
                        @Override
                        public Object then(Task<UserInfo> task) throws Exception {
                            LupolupoHTTPManager.getInstance().notificationResponse(task.getResult()).continueWith(new Continuation<String, Void>() {
                                @Override
                                public Void then(Task<String> task) throws Exception {
                                    tcs.setResult(null);
                                    return null;
                                }
                            });
                            return null;
                        }
                    });
                }
                return null;
            }
        });

        return tcs.getTask();
    }

    private void handleNotification(String message, String title) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.showNotificationMessage(title, message, "", new Intent());
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, new Intent());
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, new Intent(), imageUrl);
                }

            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), SplashActivity.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
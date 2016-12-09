package com.lupolupo.android;

import org.json.JSONObject;
import org.junit.Test;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void FireBase() throws Exception {
        // Method to send Notifications from server to client end.
        String AUTH_KEY_FCM = "AIzaSyByF2Ax3YJq5l6U5yXrkicR-xqR98PBFK0";
        String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
        String userDeviceIdKey = "fLZ3r7p-Qho:APA91bE9fyO4xZpn0jDeqj7cJRw3pv-Wggpp7Xq2DcGGDc7dDkK_FK3wCOy3IMLwfPTcP0x1ZurrNEg2l2N62unDei2o7gP8cnyrAx6frTYA9sG7GpcKSnzdPcFFgqcWCQi0NMLvAdDP";

        URL url = new URL(API_URL_FCM);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        json.put("to", userDeviceIdKey.trim());
        JSONObject info = new JSONObject();
        info.put("title", "Notification Title");
        info.put("body", "Hello Test notification");
        json.put("notification", info);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(json.toString());
        wr.flush();
        conn.getInputStream();
    }
}
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
        String userDeviceIdKey = "fDUGiZAA9gM:APA91bGs7rOQucRaa_Rfo-kJChvB8NJ4xiWYNhXpeo0Pk7SycNZuWFos3Ka4LtetsSbPNF-dS5M2_9CxWved9LPROG1ZMp9WVT_bx0wwzf8c9B-RP4CbcUV3laxJzgaHngBD1MWSmGvO";

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
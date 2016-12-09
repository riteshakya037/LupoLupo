package com.lupolupo.android.model;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

import com.lupolupo.android.common.FCMPref;
import com.lupolupo.android.common.GPSTracker;
import com.lupolupo.android.common.LupolupoAPIApplication;

import static android.content.Context.WIFI_SERVICE;

/**
 * @author Ritesh Shakya
 */
public class UserInfo {
    public String latitude;
    public String longitude;
    public String publicIP;
    public String deviceModel;
    public String deviceID;
    public String carrier;
    public String deviceType;
    private GPSTracker gpsTracker = new GPSTracker(LupolupoAPIApplication.get());

    public void getInfo() {
        if (gpsTracker.canGetLocation()) {
            latitude = String.valueOf(gpsTracker.getLatitude());
            longitude = String.valueOf(gpsTracker.getLongitude());
        }
        publicIP = getIp();
        deviceModel = getDeviceModel();
        deviceID = getDeviceToken();
        carrier = getCarrier();
        deviceType = "android";
    }

    private String getDeviceToken() {
        return FCMPref.newInsance().getToken();
    }

    private String getCarrier() {
        TelephonyManager manager = (TelephonyManager) LupolupoAPIApplication.get().getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getNetworkOperatorName();
    }


    private String getDeviceModel() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model.toUpperCase();
        } else {
            return manufacturer.toUpperCase() + " " + model;
        }
    }

    private String getIp() {
        WifiManager wm = (WifiManager) LupolupoAPIApplication.get().getSystemService(WIFI_SERVICE);
        return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", publicIP='" + publicIP + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", deviceID='" + deviceID + '\'' +
                ", carrier='" + carrier + '\'' +
                ", deviceType='" + deviceType + '\'' +
                '}';
    }
}

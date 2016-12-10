package com.lupolupo.android.model;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.lupolupo.android.common.FCMPref;
import com.lupolupo.android.common.GPSTracker;
import com.lupolupo.android.common.LupolupoAPIApplication;
import com.lupolupo.android.controllers.retrofit.PublicIP;

import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Ritesh Shakya
 */
public class UserInfo {
    public String latitude;
    public String longitude;
    public String publicIP;
    public String deviceModel;
    public String deviceID;
    public String carrier = "Unknown Carrier";
    public String deviceType;
    private GPSTracker gpsTracker = new GPSTracker(LupolupoAPIApplication.get());

    public Task<UserInfo> getInfo() {
        final TaskCompletionSource<UserInfo> source = new TaskCompletionSource<>();
        if (gpsTracker.canGetLocation()) {
            latitude = String.valueOf(gpsTracker.getLatitude());
            longitude = String.valueOf(gpsTracker.getLongitude());
        } else {
            gpsTracker.showSettingsAlert();
        }
        deviceModel = getDeviceModel();
        deviceID = getDeviceToken();
        carrier = getCarrier();
        deviceType = "android";
        getIp().onSuccess(new Continuation<String, Void>() {
            @Override
            public Void then(Task<String> task) throws Exception {
                publicIP = task.getResult();
                source.setResult(UserInfo.this);
                return null;
            }
        });

        return source.getTask();
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

    private Task<String> getIp() {
        final TaskCompletionSource<String> source = new TaskCompletionSource<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://freegeoip.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(PublicIP.class).getIP().enqueue(new Callback<FreegeoipDto>() {
            @Override
            public void onResponse(Call<FreegeoipDto> call, Response<FreegeoipDto> response) {
                source.setResult(response.body().ip);
            }

            @Override
            public void onFailure(Call<FreegeoipDto> call, Throwable t) {
                source.setError(null);
            }
        });
        return source.getTask();
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

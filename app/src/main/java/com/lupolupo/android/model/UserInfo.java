package com.lupolupo.android.model;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.lupolupo.android.common.FCMPref;
import com.lupolupo.android.common.GPSTracker;
import com.lupolupo.android.common.LupolupoAPIApplication;
import com.lupolupo.android.controllers.retrofit.PublicIP;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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
    public String networkSpeed;
    public String adsID;
    public String language;

    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public Task<UserInfo> getInfo() {
        List<Task> tasks = new ArrayList<>();
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
        getAdId().onSuccessTask(new Continuation<Void, Task<String>>() {
            @Override
            public Task<String> then(Task<Void> task) throws Exception {
                return getIp();
            }
        }).onSuccess(new Continuation<String, Void>() {
            @Override
            public Void then(Task<String> task) throws Exception {
                publicIP = task.getResult();
                source.setResult(UserInfo.this);
                return null;
            }
        });
        language = Locale.getDefault().getDisplayLanguage();
        return source.getTask();
    }

    public static boolean validate(final String ip) {
        return PATTERN.matcher(ip).matches();
    }

    private Task<Void> getAdId() {
        final TaskCompletionSource<Void> source = new TaskCompletionSource<>();
        Task.callInBackground(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                AdvertisingIdClient.Info idInfo;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(LupolupoAPIApplication.get());
                    adsID = idInfo.getId();
                    source.setResult(null);
                } catch (Exception e) {
                    source.setError(e);
                    e.printStackTrace();
                }
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

        String carrier = manager.getNetworkOperatorName();
        if (carrier.equalsIgnoreCase("") || carrier.equalsIgnoreCase("android"))
            return "Unknown Carrier";
        else return carrier;
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
                if (PATTERN.matcher(response.body().ip).matches())
                    source.setResult(response.body().ip);
                else {
                    getIp2(source);
                }
            }

            @Override
            public void onFailure(Call<FreegeoipDto> call, Throwable t) {
                getIp2(source);
            }
        });
        return source.getTask();
    }

    private void getIp2(final TaskCompletionSource<String> source) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://icanhazip.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        retrofit.create(PublicIP.class).getIP2().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                source.setResult(response.body().replace("\n", ""));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
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
                ", networkSpeed='" + networkSpeed + '\'' +
                '}';
    }

    public void setDownloadSpeed(double downloadSpeed) {
        this.networkSpeed = String.format(Locale.getDefault(), "%.2f", downloadSpeed);
    }
}

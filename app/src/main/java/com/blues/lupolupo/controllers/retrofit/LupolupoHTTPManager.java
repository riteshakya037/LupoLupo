package com.blues.lupolupo.controllers.retrofit;

import android.util.Log;

import com.blues.lupolupo.model.Comic;
import com.blues.lupolupo.model.GetComicDto;

import java.util.List;

import bolts.Task;
import bolts.TaskCompletionSource;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Ritesh Shakya
 */

public class LupolupoHTTPManager {
    private static LupolupoHTTPManager __instance;
    @SuppressWarnings("FieldCanBeLocal")
    private final String PROD_ENDPOINT = "http://lupolupo.com";

    public static LupolupoHTTPManager getInstance() {
        if (__instance == null) {
            synchronized (LupolupoHTTPManager.class) {
                if (__instance == null) {
                    __instance = new LupolupoHTTPManager();
                }
            }
        }
        return __instance;
    }

    private LupolupoAPI getHttpAdaptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Log.e("End point", PROD_ENDPOINT);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PROD_ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(LupolupoAPI.class);
    }

    public Task<List<Comic>> getComics() {
        final TaskCompletionSource<List<Comic>> source = new TaskCompletionSource<>();
        getHttpAdaptor().getComics().enqueue(new Callback<GetComicDto>() {
            @Override
            public void onResponse(Call<GetComicDto> call, Response<GetComicDto> response) {
                source.setResult(response.body().comics);
            }

            @Override
            public void onFailure(Call<GetComicDto> call, Throwable t) {
                source.setError(null);
            }
        });
        return source.getTask();
    }
}

package com.blues.lupolupo.controllers.retrofit;

import android.util.Log;

import com.blues.lupolupo.BuildConfig;
import com.blues.lupolupo.common.LikePref;
import com.blues.lupolupo.common.LupolupoAPIApplication;
import com.blues.lupolupo.model.Comic;
import com.blues.lupolupo.model.Episode;
import com.blues.lupolupo.model.Panel;
import com.blues.lupolupo.model.UserInfo;
import com.blues.lupolupo.model.dtos.GetComicDto;
import com.blues.lupolupo.model.dtos.GetEpisodeDto;
import com.blues.lupolupo.model.dtos.GetPanelDto;

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
import retrofit2.converter.scalars.ScalarsConverterFactory;

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
        if (BuildConfig.FLAVOR.equals("staging")) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Log.e("End point", PROD_ENDPOINT);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PROD_ENDPOINT)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
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

    public Task<List<Episode>> getEpisodes(String comic_id) {
        final TaskCompletionSource<List<Episode>> source = new TaskCompletionSource<>();
        getHttpAdaptor().getEpisode(comic_id).enqueue(new Callback<GetEpisodeDto>() {
            @Override
            public void onResponse(Call<GetEpisodeDto> call, Response<GetEpisodeDto> response) {
                source.setResult(response.body().episodes);
            }

            @Override
            public void onFailure(Call<GetEpisodeDto> call, Throwable t) {
                source.setError(null);
            }
        });
        return source.getTask();
    }

    public Task<List<Panel>> getPanel(String episode_id) {
        final TaskCompletionSource<List<Panel>> source = new TaskCompletionSource<>();
        getHttpAdaptor().getPanel(episode_id).enqueue(new Callback<GetPanelDto>() {
            @Override
            public void onResponse(Call<GetPanelDto> call, Response<GetPanelDto> response) {
                source.setResult(response.body().panels);
            }

            @Override
            public void onFailure(Call<GetPanelDto> call, Throwable t) {
                source.setError(null);
            }
        });
        return source.getTask();
    }

    public void postLikeUnlike(final String episode_id, final boolean status) {
        getHttpAdaptor().postLikeUnlike(episode_id, status ? "liked" : "unliked").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                LikePref.with(LupolupoAPIApplication.get()).save(episode_id, status);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    public Task<String> saveInfo(UserInfo info) {
        final TaskCompletionSource<String> source = new TaskCompletionSource<>();
        getHttpAdaptor().saveInfo(info.latitude, info.longitude, info.publicIP, info.deviceModel, info.deviceID, info.carrier, info.deviceType).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                source.setResult(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        return source.getTask();
    }
}

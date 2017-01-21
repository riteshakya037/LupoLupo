package com.lupolupo.android.controllers.retrofit;

import com.lupolupo.android.model.FreegeoipDto;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Ritesh Shakya
 */
public interface PublicIP {
    @GET("json")
    Call<FreegeoipDto> getIP();

    @GET("json")
    Call<String> getIP2();
}

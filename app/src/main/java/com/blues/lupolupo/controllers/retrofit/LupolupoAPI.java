package com.blues.lupolupo.controllers.retrofit;

import com.blues.lupolupo.model.GetComicDto;
import com.blues.lupolupo.model.UserInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Ritesh Shakya
 */

interface LupolupoAPI {

    @POST("api/getComics")
    Call<GetComicDto> getComics();

    @POST("api/saveInfo")
    Call<Object> saveInfo(@Body UserInfo user);

}

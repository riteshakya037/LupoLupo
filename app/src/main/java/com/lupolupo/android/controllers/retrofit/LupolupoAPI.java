package com.lupolupo.android.controllers.retrofit;

import com.lupolupo.android.model.dtos.GetComicDto;
import com.lupolupo.android.model.dtos.GetEpisodeDto;
import com.lupolupo.android.model.dtos.GetPanelDto;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author Ritesh Shakya
 */

interface LupolupoAPI {

    @FormUrlEncoded
    @POST("api/getComics")
    Call<GetComicDto> getComics(@Field("app_name") String app_name);

    @FormUrlEncoded
    @POST("api/getEpisodes")
    Call<GetEpisodeDto> getEpisode(@Field("comic_id") String comic_id, @Field("app_name") String app_name);

    @FormUrlEncoded
    @POST("api/getPanels")
    Call<GetPanelDto> getPanel(@Field("episode_id") String episode_id, @Field("deviceType") String deviceType);

    @FormUrlEncoded
    @POST("api/Like_unlike")
    Call<String> postLikeUnlike(@Field("episode_id") String episode_id, @Field("status") String status);

    @FormUrlEncoded
    @POST("api/saveInfo")
    Call<String> saveInfo(@Field("latitude") String latitude, @Field("longitude") String longitude, @Field("publicIP") String publicIP, @Field("deviceModel") String episode_id, @Field("deviceID") String deviceID, @Field("carrier") String carrier, @Field("deviceType") String deviceType, @Field("networkSpeed") String networkSpeed, @Field("adsID") String adsID, @Field("language") String language, @Field("app_name") String app_name);

    @FormUrlEncoded
    @POST("api/subscribe")
    Call<String> subscribe(@Field("comicID") String comicID, @Field("deviceID") String deviceID, @Field("adsID") String adsID, @Field("deviceType") String deviceType, @Field("app_name") String app_name);

    @FormUrlEncoded
    @POST("api/getAllEpisodes")
    Call<GetEpisodeDto> getAllEpisode(@Field("app_name") String app_name);
}

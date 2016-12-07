package com.blues.lupolupo.controllers.retrofit;

import com.blues.lupolupo.model.UserInfo;
import com.blues.lupolupo.model.dtos.GetComicDto;
import com.blues.lupolupo.model.dtos.GetEpisodeDto;
import com.blues.lupolupo.model.dtos.GetPanelDto;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author Ritesh Shakya
 */

interface LupolupoAPI {

    @POST("api/getComics")
    Call<GetComicDto> getComics();

    @POST("api/saveInfo")
    Call<Object> saveInfo(UserInfo user);

    @FormUrlEncoded
    @POST("api/getEpisodes")
    Call<GetEpisodeDto> getEpisode(@Field("comic_id") String comic_id);

    @FormUrlEncoded
    @POST("api/getPanels")
    Call<GetPanelDto> getPanel(@Field("episode_id") String episode_id);
}

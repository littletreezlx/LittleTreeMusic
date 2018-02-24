package com.example.littletreemusic.util;

import com.example.littletreemusic.pojo.QueryLyric;
import com.example.littletreemusic.pojo.QuerySong;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 春风亭lx小树 on 2018/1/1.
 */


    public interface ShowApiRetrofitRequest {
    /**
     * method 表示请求的方法，区分大小写
     * path表示路径
     * hasBody表示是否有请求体
     */

//    @HTTP(method = "GET", path = "blog/{id}", hasBody = false)
//    @POST("blog")
//    Call<ResponseBody> createBlog(@Body Blog blog);
//
//    String MUSIC_BASE_URL = "http://tingapi.ting.baidu.com/";
//    String SUB_URL = "v1/restserver/ting";

//
//    @GET("213-1?showapi_appid=53343&showapi_sign=e6427ae1d0ed48de895cb1f9246f0e9c")
//    Observable<FirstEvent> getSong(@Query("keyword") String keyword, @Query("page") String page);


//    @GET("/Spring_war/firstservlet/search1/retrieve")
//    Observable<ResponseBody> getSimple();


//    请求歌词
    @GET("213-1?showapi_appid=53343&showapi_sign=e6427ae1d0ed48de895cb1f9246f0e9c")
    Observable<Response<QuerySong>> getSong(@Query("keyword") String keyword, @Query("page") String page);

    @GET("213-2?showapi_appid=53343&showapi_sign=e6427ae1d0ed48de895cb1f9246f0e9c")
    Observable<Response<QueryLyric>> getLyric(@Query("musicid") String musicid);
    }


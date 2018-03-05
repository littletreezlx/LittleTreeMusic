package com.example.littletreemusic.util;

import com.example.littletreemusic.pojo.LoginInfo;
import com.example.littletreemusic.pojo.RegisterMsg;
import com.example.littletreemusic.pojo.ServerSongInfo;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by 春风亭lx小树 on 2018/1/1.
 */


    public interface CommunityRetrofitRequest {


    @POST("/register")
    Observable<Response<ResponseBody>> register(@Body RegisterMsg registerMsg);

    @POST("/login")
    Observable<Response<ResponseBody>> login(@Body LoginInfo loginInfo);

    @POST("/alterpd")
    Observable<Response<ResponseBody>> alterpd(@Body LoginInfo loginInfo);



    @Headers({"Content-Type: application/json"})
    @POST("songinfo/push")
    Observable<Response<String>> postJson(@Body RequestBody requestBody);

    @GET("songinfo/pull")
    Observable<Response<ServerSongInfo>> pullJson();

    @Multipart
    @POST("file/upload")
    Observable<Response<String>> uploadFiles(@Part("description") RequestBody description,
                                             @Part MultipartBody.Part file);

    @POST("file/download")
    Observable<okhttp3.Response> downloadFiles();

    @GET("springmvc/hello")
    Observable<Response<String>> getString();



    }


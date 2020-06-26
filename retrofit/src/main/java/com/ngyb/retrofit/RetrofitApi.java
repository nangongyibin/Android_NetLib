package com.ngyb.retrofit;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/6/26 21:40
 */
public interface RetrofitApi {

    @GET("test")
    Call<People> getTest();

    @FormUrlEncoded
    @POST("login")
    Call<User> login(@Field("username") String username, @Field("password") String password);

    @GET("image")
    Call<ResponseBody> download();

    @Multipart
    @POST("uploadMulti")
    Call<ResponseBody> upload(@PartMap Map<String, RequestBody> map);
}

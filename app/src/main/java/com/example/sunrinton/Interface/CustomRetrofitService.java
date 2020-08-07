package com.example.sunrinton.Interface;

import com.example.sunrinton.Util.Quiz;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CustomRetrofitService {

    @POST("auth")
    Call<ResponseBody> UserLogin(
            @Query("id") String id,
            @Query("pw") String pw
    );

    @POST("register")
    Call<ResponseBody> UserRegister(
            @Query("name") String name,
            @Query("id") String id,
            @Query("pw") String pw,
            @Query("birth") long date
    );

    @GET("search")
    Call<ResponseBody> getOneWord(
            @Query("text") String text
    );

    @GET("getOne")
    Call<ResponseBody> getOneWord();

    @GET("quiz")
    Call<ResponseBody> getWords();




}

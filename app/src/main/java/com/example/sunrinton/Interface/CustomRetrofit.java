package com.example.sunrinton.Interface;


import android.content.Context;
import android.icu.text.UnicodeSetIterator;

import com.example.sunrinton.R;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomRetrofit {

    private static CustomRetrofit instance;

    private CustomRetrofitService dropService;

    public static CustomRetrofit getInstance(Context context) {
        if (instance == null) {
            instance = new CustomRetrofit(context);
        }
        return instance;
    }

    private CustomRetrofit(Context context) {
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        Retrofit register = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.retrofit_uri))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dropService = register.create(CustomRetrofitService.class);
    }

    public CustomRetrofitService getCustomService() {
        return dropService;
    }

}

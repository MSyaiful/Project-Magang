package com.warehousenesia.id.Service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String url){
        if (retrofit == null){
            final OkHttpClient okHttpClient = new OkHttpClient.Builder().
                    connectTimeout(20, TimeUnit.SECONDS).
                    writeTimeout(20, TimeUnit.SECONDS).
                    readTimeout(20, TimeUnit.SECONDS).build();

            retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

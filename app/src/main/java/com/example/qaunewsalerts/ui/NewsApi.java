package com.example.qaunewsalerts.ui;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsApi {


    private static Retrofit retrofit;
    private  static final String BASE_URL="http://192.168.0.111/qauApi/";
    public static Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}

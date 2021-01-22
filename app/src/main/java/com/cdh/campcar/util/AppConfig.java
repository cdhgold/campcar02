package com.cdh.campcar.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppConfig {
    private static String BASE_URL = "http://49.50.167.90";
    public static Retrofit getRetrofit() {
        return new Retrofit.Builder().baseUrl(AppConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }
}

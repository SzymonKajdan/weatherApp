package com.SzymonKajdan.weatherapp.RetroFit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Rest {
    private static RestInterface serviceRest;

    private static OkHttpClient okHttpClient;

    private static Gson gson;

    private Rest() {
    }

    public static RestInterface getRest() {
        return serviceRest;
    }

    public static void init() {
        gson = new GsonBuilder().create();
        okHttpClient = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("http://api.apixu.com/v1/forecast.json?key=81fa35fe846b469ebc0132923190901")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        serviceRest = retrofit.create(RestInterface.class);


    }

}



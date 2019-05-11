package com.SzymonKajdan.weatherapp.RetroFit;

import com.SzymonKajdan.weatherapp.Models.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public  interface RestInterface {
    String token="forecast.json?key=81fa35fe846b469ebc0132923190901";
    @GET(token) //i.e https://api.test.com/Search?
    Call<Weather> getCitiesForecast(@Query("q") String one, @Query("days") int days);


}

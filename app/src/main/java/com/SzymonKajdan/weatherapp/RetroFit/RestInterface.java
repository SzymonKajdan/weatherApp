package com.SzymonKajdan.weatherapp.RetroFit;

import com.SzymonKajdan.weatherapp.Models.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface RestInterface {
    @GET() //i.e https://api.test.com/Search?
    Call<Weather> getProducts(@Query("q") String one, @Query("days") int days);
}

package com.SzymonKajdan.weatherapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class Forecast {
    @SerializedName("forecasday")
    List<Forecastday>forecastdayList;
}

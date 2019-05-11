package com.SzymonKajdan.weatherapp.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

 public class Forecast implements Serializable {
    @SerializedName("forecastday")
   private List<Forecastday>forecastdayList;

     public List<Forecastday> getForecastdayList() {
         return forecastdayList;
     }

     public void setForecastdayList(List<Forecastday> forecastdayList) {
         this.forecastdayList = forecastdayList;
     }
 }

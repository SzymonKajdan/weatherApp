package com.SzymonKajdan.weatherapp.Models;

import com.google.gson.annotations.SerializedName;

class Forecastday {
    @SerializedName("date")
    private String date;
    @SerializedName("day")
    private Day day;

    @SerializedName("astro")
    private Astro astro;

    @SerializedName("uv")
    private double uv;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public double getUv() {
        return uv;
    }

    public void setUv(double uv) {
        this.uv = uv;
    }
}

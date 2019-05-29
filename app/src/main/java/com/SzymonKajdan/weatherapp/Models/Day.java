package com.SzymonKajdan.weatherapp.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Day implements Serializable {
    @SerializedName("maxtemp_c")
    private double maxtemp_c;
    @SerializedName("mintemp_c")
    private double mintemp_c;
    @SerializedName("avgtemp_c")
    private double avgtemp_c;
    @SerializedName("avghumidity")
    private double avghumitidty;
    @SerializedName("condition")
    private Condtition condtition;
    @SerializedName("maxwind_mph")
    private double maxWind;


    public Condtition getCondtition() {
        return condtition;
    }

    public void setCondtition(Condtition condtition) {
        this.condtition = condtition;
    }

    public double getAvghumitidty() {
        return avghumitidty;
    }

    public void setAvghumitidty(double avghumitidty) {
        this.avghumitidty = avghumitidty;
    }


    public double getMintemp_c() {
        return mintemp_c;
    }

    public void setMintemp_c(double mintemp_c) {
        this.mintemp_c = mintemp_c;
    }

    public double getMaxtemp_c() {
        return maxtemp_c;
    }

    public void setMaxtemp_c(double maxtemp_c) {
        this.maxtemp_c = maxtemp_c;
    }

    public double getAvgtemp_c() {
        return avgtemp_c;
    }

    public void setAvgtemp_c(double avgtemp_c) {
        this.avgtemp_c = avgtemp_c;
    }

    public double getMaxWind() {
        return maxWind;
    }

    public void setMaxWind(double maxWind) {
        this.maxWind = maxWind;
    }
}

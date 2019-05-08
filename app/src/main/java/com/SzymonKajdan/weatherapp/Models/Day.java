package com.SzymonKajdan.weatherapp.Models;

import com.google.gson.annotations.SerializedName;

class Day {
    @SerializedName("maxtemp_c")
    private double maxtemp_c;
    @SerializedName("mintemp_c")
    private double mintemp_c;
    @SerializedName("avgtemp_c")
    private double avgtemp_cl;
    @SerializedName("avghumidity")
    private double avghumitidty;
    @SerializedName("condition")
    private Condtition condtition;


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

    public double getAvgtemp_cl() {
        return avgtemp_cl;
    }

    public void setAvgtemp_cl(double avgtemp_cl) {
        this.avgtemp_cl = avgtemp_cl;
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
}

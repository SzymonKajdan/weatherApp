package com.SzymonKajdan.weatherapp.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Current  implements Serializable {
    @SerializedName("last_updated")
    private String last_updated;
    @SerializedName("temp_c")
    private double temp_c;
    @SerializedName("wind_kph")
    private  double wind_kph;
    @SerializedName("pressure_mb")
    private  double pressure_mb;
    @SerializedName("humidity")
    private  double humidity;
    @SerializedName("feelslike_c")
    private  double feelslike_c;
    @SerializedName("condition")
    private Condtition condtition;
    @SerializedName("cloud")
    private double cloud;
    @SerializedName("uv")
    private  double uv;

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public double getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(double temp_c) {
        this.temp_c = temp_c;
    }

    public double getWind_kph() {
        return wind_kph;
    }

    public void setWind_kph(double wind_kph) {
        this.wind_kph = wind_kph;
    }

    public double getPressure_mb() {
        return pressure_mb;
    }

    public void setPressure_mb(double pressure_mb) {
        this.pressure_mb = pressure_mb;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getFeelslike_c() {
        return feelslike_c;
    }

    public void setFeelslike_c(double feelslike_c) {
        this.feelslike_c = feelslike_c;
    }

    public Condtition getCondtition() {
        return condtition;
    }

    public void setCondtition(Condtition condtition) {
        this.condtition = condtition;
    }

    public double getCloud() {
        return cloud;
    }

    public void setCloud(double cloud) {
        this.cloud = cloud;
    }

    public double getUv() {
        return uv;
    }

    public void setUv(double uv) {
        this.uv = uv;
    }
}

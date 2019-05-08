package com.SzymonKajdan.weatherapp.Models;

import com.google.gson.annotations.SerializedName;

class Condtition {
    @SerializedName("icon")
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

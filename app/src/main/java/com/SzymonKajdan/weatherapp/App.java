package com.SzymonKajdan.weatherapp;


import android.app.Application;

import com.SzymonKajdan.weatherapp.RetroFit.Rest;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public class App extends Application {
    @Override
    public  void onCreate(){
        super.onCreate();
        Rest.init();
        FlowManager.init(new FlowConfig.Builder(this).build());
    }
}
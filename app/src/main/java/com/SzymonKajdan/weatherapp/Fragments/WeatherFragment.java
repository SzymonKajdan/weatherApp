package com.SzymonKajdan.weatherapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SzymonKajdan.weatherapp.Adapters.WeahterDayAdapter;
import com.SzymonKajdan.weatherapp.BaseFragments.BaseFragment;
import com.SzymonKajdan.weatherapp.Models.City;
import com.SzymonKajdan.weatherapp.Models.Forecast;
import com.SzymonKajdan.weatherapp.Models.Forecastday;
import com.SzymonKajdan.weatherapp.Models.Weather;
import com.SzymonKajdan.weatherapp.R;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

public class WeatherFragment extends BaseFragment {

    private static final String WEATHER = "Weather";
    public ImageView weatherIcon;
    private TextView tempTextView;
    private TextView cityTextView;
    private TextView humitdityTextView;
    private TextView feelsTempTextview;
    private TextView pressureTextView;
    private TextView windTextView;
    private TextView cloudTextView;
    private TextView uvTextview;
    private TextView startDayTextView;
    private TextView endDayTextView;

    private Weather weather;

    private RecyclerView recyclerView;
    private WeahterDayAdapter weahterDayAdapter;

    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;

    public static WeatherFragment newInstance(Weather weather) {

        Bundle args = new Bundle();
        args.putSerializable(WEATHER, weather);
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup conatiner, @Nullable Bundle savedInstanceSate) {

        View rootView = inflater.inflate(R.layout.start_fragment, conatiner, false);

        weather = (Weather) getArguments().getSerializable(WEATHER);



        findViews(rootView);


        setViews(weather);
        setAdapter();
        onClickListener();

        return rootView;
    }
    private void onClickListener(){
        weahterDayAdapter.setOnClickListener(new WeahterDayAdapter.ClickListener() {
            @Override
            public void onClickListener(View view, int position) {
                Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                List<Forecastday>forecasts=  weather.getForecast().getForecastdayList().subList(1,weather.getForecast().getForecastdayList().size());
                getNavigationInteractions().changeFragment(AnotherDayFragment.newInstance(forecasts.get(position)), true);


            }
        });
    }
    private void setAdapter() {
      List<Forecastday>forecasts=  weather.getForecast().getForecastdayList().subList(1,weather.getForecast().getForecastdayList().size());

        weahterDayAdapter = new WeahterDayAdapter(forecasts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(weahterDayAdapter);
    }


    private void setViews(Weather weather) {

        Picasso.get().load("http:" + weather.getCurrent().getCondtition().getIcon()).into(weatherIcon);

        tempTextView.setText(weather.getCurrent().getTemp_c() + " \u00B0" + "C");

        cityTextView.setText(weather.getLocation().getName());

        humitdityTextView.setText("Wilgotność: \n" + weather.getCurrent().getHumidity() + " %");
        pressureTextView.setText("Ciśnienie: \n" + weather.getCurrent().getPressure_mb() + " HPa");
        feelsTempTextview.setText("Odczuwalna: \n" + weather.getCurrent().getFeelslike_c() + " \u00B0" + "C");
        windTextView.setText("Wiatr: \n" + weather.getCurrent().getWind_kph() + " Km/h");
        uvTextview.setText("UV " + weather.getCurrent().getUv());
        cloudTextView.setText("Zachmurzenie " + weather.getCurrent().getCloud() + "%");

    }


    public void findViews(View rootView) {
        recyclerView=rootView.findViewById(R.id.nextDayWeatherRecyclerView);
        weatherIcon = rootView.findViewById(R.id.weather_image);
        tempTextView = rootView.findViewById(R.id.temp_textView);
        cityTextView = rootView.findViewById(R.id.city_TextView);
        humitdityTextView = rootView.findViewById(R.id.humidity_TextView);
        pressureTextView = rootView.findViewById(R.id.pressure_TextView);
        feelsTempTextview = rootView.findViewById(R.id.feelsLikeTemp_TextView);
        windTextView = rootView.findViewById(R.id.windKph_TextView);
        uvTextview = rootView.findViewById(R.id.uv_TextView);
        cloudTextView = rootView.findViewById(R.id.cloud_TextView);
        startDayTextView = rootView.findViewById(R.id.dayStart_TextView);
        endDayTextView = rootView.findViewById(R.id.dayEnd_TextView);
    }


}

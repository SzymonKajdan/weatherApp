package com.SzymonKajdan.weatherapp.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.SzymonKajdan.weatherapp.Adapters.ViewPagerAdapter;
import com.SzymonKajdan.weatherapp.BaseFragments.BaseFragment;
import com.SzymonKajdan.weatherapp.Database.AppDatabase;
import com.SzymonKajdan.weatherapp.Models.City;
import com.SzymonKajdan.weatherapp.Models.Forecastday;
import com.SzymonKajdan.weatherapp.Models.Weather;
import com.SzymonKajdan.weatherapp.R;
import com.SzymonKajdan.weatherapp.RetroFit.Rest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InitFragment extends BaseFragment {

    private ViewPagerAdapter viewPagerAdapter;

    private ViewPager viewPager;


    private List<City> cities = new ArrayList<>();

    private FusedLocationProviderClient fusedLocationProviderClient;
    private List<Weather> weatherList = new ArrayList<>();
    private Geocoder geocoder;

    City city = new City();

    public static InitFragment newInstance() {
        return new InitFragment();
    }


    @Override
    public void onResume() {

        super.onResume();


        initFragments();
       // setLoc();
        geocoder = new Geocoder(getContext());
        findLocation(geocoder);
        System.out.println(viewPagerAdapter.getCount());

    }

    private void setLoc() {
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = getContext().getResources().getConfiguration();
        config.locale = locale;
        getContext().getResources().updateConfiguration(config,
                getContext().getResources().getDisplayMetrics());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.init_fragment, container, false);

        findViews(view);


        initFragments();
        setViewPagerAdapter();


        //    findLocation(geocoder);
//        System.out.println("wielkosc miast "+cities.size());

        return view;
    }

    private void findViews(View view) {
        viewPager = view.findViewById(R.id.viewPager);
    }

    private void initFragments() {
        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());


    }

    private void setViewPagerAdapter() {
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void checkIsCityInApi(City city) {
        boolean flag;
        Rest.getRest().getCitiesForecast(city.getCity(), 7).enqueue(new Callback<Weather>() {

            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Weather weather = response.body();


                if (response.body() != null) {
                    System.out.println("sprawdzam" + checkCity(cities, city) + " " + city.getCity());
                    if (checkCity(cities, city)) {
                        //System.out.println("nie zpaisuje");
                        cities.add(city);
                    } else {
                        //System.out.println("zpaiosuje");

                    }
                } else {


                }


                for (City c : cities) {
                    System.out.println("zpaisuje " + c.getCity());
                    c.save();
                }


            }


            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });

    }


    private void loadCities() {

        cities = new ArrayList<>();

        cities.addAll(SQLite.select().from(City.class).queryList());


        //sprawdzam czy miasto jest w liscie z bazy jesli nie to sprawdxaam cyz w api jest takie maisto
        for (City c : cities) {
            //  System.out.println(c.getCity());
        }

    }

    private void loadWeatherForCities(List<City> cities) {

        setViewPagerAdapter();
        for (City city : cities) {
            Rest.getRest().getCitiesForecast(city.getCity(), 7).enqueue(new Callback<Weather>() {


                @Override
                public void onResponse(Call<Weather> call, Response<Weather> response) {
                    if (response.body() != null) {
                        Weather weather = response.body();
                        changeDate(weather);
                        //  viewPagerAdapter.notifyDataSetChanged();

                        weatherList.add(weather);

                        viewPagerAdapter.addFragment(WeatherFragment.newInstance(weather));
                        viewPagerAdapter.notifyDataSetChanged();


                    } else {


                    }

                }

                @Override
                public void onFailure(Call<Weather> call, Throwable t) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                }
            });

        }


    }

    private void changeDate(Weather weather) {

        List<Forecastday> forecastList = weather.getForecast().getForecastdayList();
        for (Forecastday forecastday : forecastList) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateTime dt = formatter.parseDateTime(forecastday.getDate());
            int day = dt.getDayOfWeek();
            if (day == 1) {
                forecastday.setDate("PON");
            } else if (day == 2) {
                forecastday.setDate("WTO");
            } else if (day == 3) {
                forecastday.setDate("SRO");
            } else if (day == 4) {
                forecastday.setDate("CZW");
            } else if (day == 5) {
                forecastday.setDate("PIO");
            } else if (day == 6) {
                forecastday.setDate("SOB");
            } else {
                forecastday.setDate("NDZ");
            }

        }
    }

    ;

    private boolean checkCity(final List<City> cities, final City city) {

        for (City c : cities) {
            if (c.getCity().equals(city.getCity()))
                return false;
        }
        return true;

    }

    private void findLocation(final Geocoder geocoder) {
        viewPagerAdapter.clear();


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getContext());
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this.getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    try {
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


                        if (addressList.get(0).getLocality().equals("Łódź")) {
                            city.setCity("Lodz");
                        }else {
                            city.setCity(addressList.get(0).getLocality());
                        }
                        System.out.println("porbralem" + city.getCity());

                        loadCities();

                        checkIsCityInApi(city);

                        loadWeatherForCities(cities);
//                        System.out.println("maista wielkosc" + cities.size());
//                        System.out.println("wilkosc pogoód" + weatherList.size());
//                        System.out.println("wielkosc adpatera " + viewPagerAdapter.getCount());

//                        if (checkCity(cities, city)) {
//                            checkIsCityInApi(city);
//                        } else {
//
//                            loadWeatherForCities(cities);
//                        }


                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Lokazacja nie wyszuwalal kortynatow przyjmuje domyslne ", Toast.LENGTH_LONG).show();
                        System.out.println("eroor" + city.getCity());
                        City city = new City();
                        city.setCity("Lodz");
                        loadCities();
                        checkIsCityInApi(city);

                        loadWeatherForCities(cities);
//                        if (checkCity(cities, city)) {
//                            checkIsCityInApi(city);
//                        } else {
//
//                            loadWeatherForCities(cities);
//                        }
                    }
                }
            });


        }


    }

}

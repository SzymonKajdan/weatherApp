package com.SzymonKajdan.weatherapp.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.SzymonKajdan.weatherapp.Adapters.ViewPagerAdapter;
import com.SzymonKajdan.weatherapp.BaseFragments.BaseFragment;
import com.SzymonKajdan.weatherapp.Models.City;
import com.SzymonKajdan.weatherapp.Models.Forecastday;
import com.SzymonKajdan.weatherapp.Models.Weather;
import com.SzymonKajdan.weatherapp.R;
import com.SzymonKajdan.weatherapp.RetroFit.Rest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InitFragment extends BaseFragment {

    private ViewPagerAdapter viewPagerAdapter;

    private ViewPager viewPager;




    private List<City> cities;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private List<Weather> weatherList;

    public static InitFragment newInstance() {
        return new InitFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.init_fragment, container, false);


        cities = new ArrayList<>();
        weatherList = new ArrayList<>();


        Geocoder geocoder = new Geocoder(getContext());


        findViews(view);
        initFragments();
        setViewPagerAdapter();

        findLocation(geocoder);




        return view;
    }

    private void findViews(View view) {
        viewPager = view.findViewById(R.id.viewPager);
    }

    private void initFragments() {
        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());


    }

private void setViewPagerAdapter(){
        viewPager.setAdapter(viewPagerAdapter);
}

    private void checkIsCityInApi(City city) {
        boolean flag;
        Rest.getRest().getCitiesForecast(city.getCity(), 7).enqueue(new Callback<Weather>() {

            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Weather weather = response.body();
                System.out.println(call.request());
                if (response.body() != null) {
                    cities.add(city);
                    System.out.println(response.toString());
                } else {
                    System.out.println(response.body());
                    Toast.makeText(getContext(), "Miasto " + city.getCity() + " w którym sie znajdujesz nie widnieje w lokaziacji wczytuje reszte miast", Toast.LENGTH_LONG).show();

                }
                loadWeatherForCities(cities);


                for (City c : cities) {
                    c.save();
                }

                Toast.makeText(getContext(), "rozmair po " + cities.size(), Toast.LENGTH_LONG).show();
            }


            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });

    }


    private void loadCities(City city) {
        Toast.makeText(getContext(), "Laduje miasta z bazy ", Toast.LENGTH_LONG).show();


        cities.addAll(SQLite.select().from(City.class).queryList());
        Toast.makeText(getContext(), "rozmair przed " + cities.size(), Toast.LENGTH_LONG).show();


        //sprawdzam czy miasto jest w liscie z bazy jesli nie to sprawdxaam cyz w api jest takie maisto
        if (checkCity(cities, city)) {
            System.out.println("miasto " + city.getCity());
            checkIsCityInApi(city);
        } else {

            Toast.makeText(getContext(), "rozmair po " + cities.size(), Toast.LENGTH_LONG).show();
            loadWeatherForCities(cities);
        }
    }

    private void loadWeatherForCities(List<City> cities) {


        for (City city : cities) {
            Rest.getRest().getCitiesForecast(city.getCity(), 7).enqueue(new Callback<Weather>() {


                @Override
                public void onResponse(Call<Weather> call, Response<Weather> response) {
                    if (response.body() != null) {
                        Weather weather = response.body();
                        changeDate(weather);
                        viewPagerAdapter.notifyDataSetChanged();
                        weatherList.add(weather);

                        viewPagerAdapter.addFragment(WeatherFragment.newInstance(weather));
                        viewPagerAdapter.notifyDataSetChanged();


                        System.out.println(weather.getLocation().getName());
                        Toast.makeText(getContext(), "Pobrano   " + city.getCity() + " z api  ", Toast.LENGTH_LONG).show();
                    } else {
                        System.out.println(response.body());
                        Toast.makeText(getContext(), "Error  " + city.getCity() + " przy pobieraniu tego maista z api  ", Toast.LENGTH_LONG).show();

                    }
                    System.out.println(weatherList.size());
                }

                @Override
                public void onFailure(Call<Weather> call, Throwable t) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                }
            });

        }



    }
private  void  changeDate(Weather weather) {
    System.out.println(weather.getLocation().getName());
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
        System.out.println(forecastday.getDate());
    }
};
    private boolean checkCity(final List<City> cities, final City city) {

        for (City c : cities) {
            if (c.getCity().equals(city.getCity()))
                return false;
        }
        return true;

    }

    private void findLocation(final Geocoder geocoder) {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getContext());
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this.getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    try {
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);




                        City city = new City();
                        city.setCity(addressList.get(0).getLocality());

                        if (city.getCity().equals("Łódź")) {
                            city.setCity("Lodz");
                        }
                        loadCities(city);


                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Lokazacja nie wyszuwalal kortynatow przyjmuje domyslne ", Toast.LENGTH_LONG).show();

                        City city = new City();
                        city.setCity("Lodz");
                        loadCities(city);

                    }
                }
            });

        }


    }

}

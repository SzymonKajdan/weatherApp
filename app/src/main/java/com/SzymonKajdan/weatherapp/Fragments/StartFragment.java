package com.SzymonKajdan.weatherapp.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.SzymonKajdan.weatherapp.BaseFragments.BaseFragment;
import com.SzymonKajdan.weatherapp.Models.City;
import com.SzymonKajdan.weatherapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Locale.getDefault;

public class StartFragment extends BaseFragment {
    public TextView textView;


    private double lat, lon;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private Context context;

    public static StartFragment newInstance() {
        return new StartFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup conatiner, @Nullable Bundle savedInstanceSate) {

        View rootView = inflater.inflate(R.layout.start_fragment, conatiner, false);



        findViews(rootView);

        Geocoder geocoder = new Geocoder(getContext());
        findLocation(geocoder);





        return rootView;
    }

    private void loadCities(City city) {
        Toast.makeText(getContext(),"Laduje miasta z bazy ",Toast.LENGTH_LONG).show();


        List<City>cities=new ArrayList<>();
        cities.addAll(SQLite.select().from(City.class).queryList());
        Toast.makeText(getContext(), "rozmair przed " +cities.size(), Toast.LENGTH_LONG).show();;

        System.out.println(city.getCity());
        //sprawdzam czy miasto jest w liscie z bazy jesli nie to sprawdxaam cyz w api jest takie maisto
        if(checkCity(cities,city)){
            System.out.println("");
           if( checkIsCityInApi(city.getCity())){
               cities.add(city);

           }else{
               Toast.makeText(getContext(),"maisto w ktorym sie znajdujesz nie widnieje w api",Toast.LENGTH_LONG).show();

           }
        }


        for(City c:cities){
            c.save();
        }

        textView.setText("po " +cities.size());
    }

    private boolean checkIsCityInApi(String city) {
        return true;
    }

    private boolean checkCity(final List<City> cities, final City city) {

            for(City c:cities){
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

                        textView.setText(addressList.get(0).getLocality());
                        lat = location.getLatitude();
                        lon = location.getLongitude();



                        City city= new City();
                        city.setCity(addressList.get(0).getLocality());

                        if (city.getCity().equals("Łódź")){
                            city.setCity("Lodz");
                        }
                        loadCities(city);


                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Lokazacja nie wyszuwalal kortynatow przyjmuje domyslne ", Toast.LENGTH_LONG).show();

                        City city= new City();
                        city.setCity("Lodz");


                        lat = 0;
                        lon = 0;
                        loadCities(city);

                    }
                }
            });

        }


    }
    public void findViews(View rootView) {
        textView = rootView.findViewById(R.id.ext_view);
    }


}

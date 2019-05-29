package com.SzymonKajdan.weatherapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.SzymonKajdan.weatherapp.BaseFragments.BaseFragment;
import com.SzymonKajdan.weatherapp.Models.Forecastday;
import com.SzymonKajdan.weatherapp.R;
import com.squareup.picasso.Picasso;

public class AnotherDayFragment extends BaseFragment {


    public ImageView weatherIcon;
    private TextView tempTextView;
    private TextView dayTextView;
    private  TextView astroStart;
    private  TextView astroend;
    private  TextView uv;
    private  TextView avgHumidity;
    private TextView clould;
    private  TextView avgWind;

    private Forecastday forecastday;
    private static final String ARG = "FORECASTDAY";
    public  static  AnotherDayFragment newInstance(Forecastday forecastday){
        Bundle args = new Bundle();
        args.putSerializable(ARG, forecastday);
        AnotherDayFragment fragment = new AnotherDayFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

            forecastday= (Forecastday) getArguments().get(ARG);

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.one_day_fragment, container, false);
        findViews(rootView);
        setViews();
        return rootView;
    }

    private void setViews() {

        Picasso.get().load("http:" + forecastday.getDay().getCondtition().getIcon()).into(weatherIcon);
        dayTextView.setText(forecastday.getDate()+"");
        tempTextView.setText(forecastday.getDay().getAvgtemp_c()+"\u00B0" + "C");

        astroStart.setText("Wschód "+forecastday.getAstro().getSunrise());
        astroend.setText("Zachód  "+forecastday.getAstro().getSunset());
        uv.setText("UV "+forecastday.getUv());
        avgHumidity.setText("Wilgotnosc  "+forecastday.getDay().getAvghumitidty()+"%");
        clould.setText("Max temp "+forecastday.getDay().getMaxtemp_c());
        avgWind.setText("Wiatr "+forecastday.getDay().getMaxWind());

    }

    private void findViews(View rootView) {
        weatherIcon=rootView.findViewById(R.id.weatherday_image);
        dayTextView= rootView.findViewById(R.id.day_textview);

        tempTextView=rootView.findViewById(R.id.dayTemp_textview);
         astroStart=rootView.findViewById(R.id.astrostartday_TextView);
           astroend=rootView.findViewById(R.id.astroenday_TextView);
           uv=rootView.findViewById(R.id.uv_TextView);
           avgHumidity=rootView.findViewById(R.id.avghumitidy_TextView);
          clould=rootView.findViewById(R.id.clouday_TextView);
           avgWind=rootView.findViewById(R.id.avgwindKph_TextView);
    }
}

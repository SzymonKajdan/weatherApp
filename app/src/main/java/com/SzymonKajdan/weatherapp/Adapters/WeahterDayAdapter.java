package com.SzymonKajdan.weatherapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.SzymonKajdan.weatherapp.Models.Forecast;
import com.SzymonKajdan.weatherapp.Models.Forecastday;
import com.SzymonKajdan.weatherapp.Models.Weather;
import com.SzymonKajdan.weatherapp.R;

import java.util.List;

public class WeahterDayAdapter extends RecyclerView.Adapter<WeahterDayAdapter.ViewHolder> {
    private List<Forecastday> dataList;
    public   static ClickListener clickListener;
    public interface  ClickListener{
       void  onClickListener(View view,int position);
    }
    public void setOnClickListener(ClickListener clickListener){
        WeahterDayAdapter.clickListener=clickListener;
    }

    public  WeahterDayAdapter(List<Forecastday> dataList){this.dataList=dataList;}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_pager_day,viewGroup,false);
        return new WeahterDayAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Forecastday item=dataList.get(i);


        viewHolder.avgTemp.setText(String.valueOf(item.getDay().getAvgtemp_c()));
        viewHolder.dayName.setText(item.getDate());

    }


    @Override
    public int getItemCount() {
        if (dataList != null) {
            return dataList.size();
        } else {

            return 0;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView dayName, avgTemp;

        ViewHolder(View view) {
            super(view);
            dayName = view.findViewById(R.id.dayName_TextView);
            avgTemp = view.findViewById(R.id.avgTemp_TextView);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            clickListener.onClickListener(v,getAdapterPosition());
        }
    }
}

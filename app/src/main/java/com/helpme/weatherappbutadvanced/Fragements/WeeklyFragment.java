package com.helpme.weatherappbutadvanced.Fragements;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.helpme.weatherappbutadvanced.Adapters.ForecastAdapter;
import com.helpme.weatherappbutadvanced.ApiServices.HttpApiService;
import com.helpme.weatherappbutadvanced.ApiServices.HttpApiServiceClient;
import com.helpme.weatherappbutadvanced.Models.WeeklyForecast.Forecast;
import com.helpme.weatherappbutadvanced.Models.WeeklyForecast.WeeklyForecast;
import com.helpme.weatherappbutadvanced.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class WeeklyFragment extends Fragment
{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    View view;
    HttpApiService weeklyHttpApiService = HttpApiServiceClient.getHttpApiService();
    RecyclerView.LayoutManager fLayoutManager;

    public WeeklyFragment()
    {

    }

    public static WeeklyFragment newInstance(String param1, String param2)
    {
        WeeklyFragment fragment = new WeeklyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static WeeklyFragment newInstance()
    {
        WeeklyFragment fragment = new WeeklyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view  = inflater.inflate(R.layout.fragment_weekly, container, false);

        weeklyHttpApiService.getForecasts(weeklyHttpApiService.key,weeklyHttpApiService.metric)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<WeeklyForecast>() {
                            @SuppressLint("CheckResult")
                            @Override
                            public void accept(WeeklyForecast weeklyForecast) throws Exception {
                                List<Forecast> forecasts = weeklyForecast.getForecasts();
                                Log.i("NUM", forecasts.size()+"");

                                RecyclerView rcForecasts = view.findViewById(R.id.rvForecasts);
                                ForecastAdapter adapter = new ForecastAdapter(forecasts);

                                fLayoutManager = new LinearLayoutManager(getActivity());
                                rcForecasts.setLayoutManager(fLayoutManager);
                                rcForecasts.setAdapter(adapter);

                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.i("ERROR", throwable.getMessage()+"");
                            }
                        }
                );



        return view;
    }
}
package com.helpme.weatherappbutadvanced.Fragements;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.helpme.weatherappbutadvanced.ApiServices.HttpApiService;
import com.helpme.weatherappbutadvanced.ApiServices.HttpApiServiceClient;
import com.helpme.weatherappbutadvanced.Models.CurrentForecast.CurrentForecast;
import com.helpme.weatherappbutadvanced.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class CurrentFragment extends Fragment
{
    HttpApiService httpApiService = HttpApiServiceClient.getHttpApiService();

    View view;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public CurrentFragment()
    {

    }


    public static CurrentFragment newInstance(String param1, String param2)
    {
        CurrentFragment fragment = new CurrentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static CurrentFragment newInstance()
    {
        CurrentFragment fragment = new CurrentFragment();
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

        view = inflater.inflate(R.layout.fragment_current, container, false);
        httpApiService.getConditions(httpApiService.key, httpApiService.metric).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Consumer<List<CurrentForecast>>() {
                    @Override
                    public void accept(List<CurrentForecast> forecasts) {
                        CurrentForecast currentForecast = forecasts.get(0);

                        TextView tv_Date = view.findViewById(R.id.tv_Date);
                        tv_Date.setText("Date: " + currentForecast.getLocalObservationDateTime().substring(0, 10));

                        ImageView iv_Icon = view.findViewById(R.id.iv_Icon);
                        String iconWithLeading0 = String.format("%02d", currentForecast.getWeatherIcon());
                        String url = "https://developer.accuweather.com/sites/default/files/" + iconWithLeading0 + "-s.png";
                        Picasso.get().load(url).into(iv_Icon);
                        Log.i("URL", url);

                        TextView tv_Condition = view.findViewById(R.id.tv_IconPhrase);
                        tv_Condition.setText("Condition: " + currentForecast.getWeatherText());

                        TextView tv_Temp = view.findViewById(R.id.tv_Temp);
                        tv_Temp.setText("Temperature: " + currentForecast.getTemperature().getMetric().getValue() + " °C");

                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("ERROR", ""+throwable.getMessage());
                    }
                }
        );


        return view;
    }
}
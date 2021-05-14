package com.helpme.weatherappbutadvanced.Fragements;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.helpme.weatherappbutadvanced.ApiServices.HttpApiService;
import com.helpme.weatherappbutadvanced.ApiServices.HttpApiServiceClient;
import com.helpme.weatherappbutadvanced.Models.CurrentForecast.CurrentForecast;
import com.helpme.weatherappbutadvanced.Models.Location.Location;
import com.helpme.weatherappbutadvanced.Models.WeeklyForecast.Forecast;
import com.helpme.weatherappbutadvanced.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class SearchFragment extends Fragment
{

    View view;
    HttpApiService searchHttpApiService = HttpApiServiceClient.getHttpApiService();

    TextView tv_localName, tv_date, tv_condition, tv_temp;
    ImageView iv_icon;
    Button btn_search;
    EditText ed_cityName;

    String locationId, searchQuery, localizedName;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public SearchFragment()
    {

    }


    public static SearchFragment newInstance(String param1, String param2)
    {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static SearchFragment newInstance()
    {
        SearchFragment fragment = new SearchFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {


        view = inflater.inflate(R.layout.fragment_search, container, false);

        tv_localName = view.findViewById(R.id.tv_CityName);
        tv_date =  view.findViewById(R.id.tv_Date);
        tv_condition = view.findViewById(R.id.tv_IconPhrase);
        tv_temp =  view.findViewById(R.id.tv_Temp);
        iv_icon =  view.findViewById(R.id.iv_Icon);
        btn_search = view.findViewById(R.id.btn_Search);
        ed_cityName = view.findViewById(R.id.ed_City);



        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_localName.setText("");
                tv_date.setText("");
                tv_condition.setText("");
                tv_temp.setText("");

                searchQuery = ed_cityName.getText().toString().trim();
                Log.i("CITY NAME", searchQuery);

                getCityCode();

            }
        });

        return view;
    }

   @SuppressLint("CheckResult")
   private void getCityCode()
   {

       searchHttpApiService.getCity(searchHttpApiService.key, searchQuery).subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread()).subscribe
               (
                       new Consumer<List<Location>>()
                       {
                           @Override
                           public void accept(List<Location> locations) throws Exception
                           {
                               Location location = locations.get(0);
                               locationId = location.getKey();
                               localizedName = location.getLocalizedName();
                               Log.i("LOCATION", location.getKey().toString()+"");

                               getCurrentConditions();
                           }
                       },
                          new Consumer<Throwable>()
                         {
                           @Override
                           public void accept(Throwable throwable) throws Exception
                           {
                               Log.i("ERROR", ""+throwable.getMessage());
                           }
                         }
               );
   }


   @SuppressLint("CheckResult")
   private void getCurrentConditions()
   {
       searchHttpApiService.getSpecificConditions(locationId, searchHttpApiService.key, searchQuery).subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread()).subscribe
               (
                       new Consumer<List<CurrentForecast>>()
                       {

                           @Override
                           public void accept(List<CurrentForecast> forecasts) throws Exception {
                               CurrentForecast forecast = forecasts.get(0);

                               tv_localName = view.findViewById(R.id.tv_CityName);
                               tv_date =  view.findViewById(R.id.tv_Date);
                               tv_condition = view.findViewById(R.id.tv_IconPhrase);
                               tv_temp =  view.findViewById(R.id.tv_Temp);
                               iv_icon =  view.findViewById(R.id.iv_Icon);

                               tv_localName.setText(localizedName+"");
                               tv_date.setText("Date: " + forecast.getLocalObservationDateTime().substring(0, 10));
                               String iconWithLeading0 = String.format("%02d", forecast.getWeatherIcon());
                               String url = "https://developer.accuweather.com/sites/default/files/" + iconWithLeading0 + "-s.png";
                               Picasso.get().load(url).into(iv_icon);
                               Log.i("URL", url);

                               tv_condition.setText("Condition: " + forecast.getWeatherText());
                               tv_temp.setText("Temperature: " + forecast.getTemperature().getMetric().getValue() + " °C");


                           }
                       },
                       new Consumer<Throwable>()
                       {
                           @Override
                           public void accept(Throwable throwable) throws Exception
                           {
                               Log.i("ERROR", ""+throwable.getMessage());
                           }
                       }

               );

   }

   






}
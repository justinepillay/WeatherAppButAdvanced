package com.helpme.weatherappbutadvanced.ApiServices;

import com.helpme.weatherappbutadvanced.Models.CurrentForecast.CurrentForecast;
import com.helpme.weatherappbutadvanced.Models.Location.Location;
import com.helpme.weatherappbutadvanced.Models.WeeklyForecast.WeeklyForecast;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HttpApiService
{
    String key = "BSyztRhtSdnfTgoUA37X6OFazua1eDRE";
    String metric = "true";

    @GET("/currentconditions/v1/305605")
    Flowable<List<CurrentForecast>> getConditions(
            @Query("apikey") String key,
            @Query("metric") String metric);


    @GET("/forecasts/v1/daily/5day/305605")
    Flowable<WeeklyForecast> getForecasts(
            @Query("apikey") String key,
            @Query("metric") String metric);


    @GET("/locations/v1/cities/search")
    Flowable<List<Location>>getCity(
            @Query("apikey") String key,
            @Query("q") String searchQuery);

    @GET("/currentconditions/v1/{cityCode}")
    Flowable<List<CurrentForecast>>getSpecificConditions(
            @Path("cityCode") String cityCode,
            @Query("apikey") String key,
            @Query("metric") String metric);



}

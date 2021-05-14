package com.helpme.weatherappbutadvanced.Models.WeeklyForecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeeklyForecast
{

    @SerializedName("DailyForecasts")
    @Expose
    private List<Forecast> dailyForecasts = null;


    public List<Forecast> getForecasts() {
        return dailyForecasts;
    }

    public void setDailyForecasts(List<Forecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }

}

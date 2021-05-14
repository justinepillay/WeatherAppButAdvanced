package com.helpme.weatherappbutadvanced.Models.CurrentForecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentTemperature
{
    @SerializedName("Metric")
    @Expose
    private Metric metric;


    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

}

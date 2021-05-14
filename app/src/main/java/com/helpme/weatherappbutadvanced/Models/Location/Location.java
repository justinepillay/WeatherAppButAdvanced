package com.helpme.weatherappbutadvanced.Models.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location
{

    @SerializedName("Key")
    @Expose
    private String key;

    @SerializedName("LocalizedName")
    @Expose
    private String localizedName;

    public String getKey() {return key;}

    public String getLocalizedName() {return localizedName;}

}

package com.helpme.weatherappbutadvanced.Models;

public class ForecastItem
{
    private String fImageSource;
    private String fMaxTemp;
    private String fMinTemp;
    private String fStatus;
    private String fDate;
    private String fCity;

    public ForecastItem(String  fImageSource, String fMaxTemp, String fMinTemp, String fStatus, String fDate ,String fCity)
    {
        this.fImageSource = fImageSource;
        this.fMaxTemp = fMaxTemp;
        this.fMinTemp = fMinTemp;
        this.fStatus = fStatus;
        this.fDate = fDate;
        this.fCity = fCity;
    }

    public String getfImageSource()
    {
        return fImageSource;
    }

    public String getfMaxTemp()
    {
        return fMaxTemp;
    }

    public String getfMinTemp()
    {
        return fMinTemp;
    }

    public String getfStatus()
    {
        return fStatus;
    }

    public String getfDate()
    {
        return fDate;
    }

    public String getfCity()
    {
        return fCity;
    }




}
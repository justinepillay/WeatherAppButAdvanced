package com.helpme.weatherappbutadvanced.ApiServices;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpApiServiceClient
{
    static Retrofit retrofit;
    static HttpApiService httpApiService;

    public static HttpApiService getHttpApiService()
    {

        final String API_BASE_URL = "https://dataservice.accuweather.com/";

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        if(httpApiService == null )
        {
            retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create()).client(client).build();

            httpApiService = retrofit.create(HttpApiService.class);
        }

        return httpApiService;
    }


}

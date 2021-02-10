package com.weatherapp.service;

import com.weatherapp.pojo.CurrentWithName;
import com.weatherapp.pojo.WeatherStats;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {

    @GET("onecall")
    Call<WeatherStats> oneCallByLatLon(@Query("lat") String lat, @Query("lon") String lon,
                                       @Query("appid") String appId, @Query("units") String units);

    @GET("weather")
    Call<CurrentWithName> currentByQuery(@Query("q") String city, @Query("appid") String appId,
            @Query("units") String units);

    @GET("weather")
    Call<CurrentWithName> currentByLatLon(@Query("lat") String lat, @Query("lon") String lon,
                                          @Query("appid") String appId, @Query("units") String units);
}

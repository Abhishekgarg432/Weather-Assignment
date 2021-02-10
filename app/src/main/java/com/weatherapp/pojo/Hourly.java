
package com.weatherapp.pojo;

import java.util.List;


public class Hourly {



    private Integer dt;


    private Double temp;


    private Double feelsLike;


    private Integer pressure;


    private Integer humidity;


    private Double dewPoint;


    private String uvi;


    private Integer clouds;


    private Integer visibility;


    private Double windSpeed;


    private Integer windDeg;


    private List<Weather> weather = null;


    private Integer pop;

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Double dewPoint) {
        this.dewPoint = dewPoint;
    }

    public String getUvi() {
        return uvi;
    }

    public void setUvi(String uvi) {
        this.uvi = uvi;
    }

    public Integer getClouds() {
        return clouds;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(Integer windDeg) {
        this.windDeg = windDeg;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Integer getPop() {
        return pop;
    }

    public void setPop(Integer pop) {
        this.pop = pop;
    }

}

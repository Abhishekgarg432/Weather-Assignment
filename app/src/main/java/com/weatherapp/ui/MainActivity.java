package com.weatherapp.ui;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.weatherapp.pojo.CurrentWithName;
import com.weatherapp.pojo.WeatherStats;
import com.weatherapp.service.OpenWeatherService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String METRIC = "metric";
    final String APP_ID = "051815f7f39ad0c50af0779d4b554ba8";
    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;
    private final String Location_Provider = LocationManager.GPS_PROVIDER;

    LocationManager mLocationManager;
    LocationListener mLocationListner = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            String Latitude = String.valueOf(location.getLatitude());
            String Longitude = String.valueOf(location.getLongitude());
            service.currentByLatLon(Latitude, Longitude, APP_ID, METRIC).
                    enqueue(new Callback<CurrentWithName>() {
                        @Override
                        public void onResponse(Call<CurrentWithName> call, Response<CurrentWithName> response) {
                            updateUI(response.body());
                        }

                        @Override
                        public void onFailure(Call<CurrentWithName> call, Throwable t) {

                        }
                    });
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            //not able to get location
        }
    };
    private OpenWeatherService service;
    private DailyWeatherAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new DailyWeatherAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …
// add logging as last interceptor
        httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        service = retrofit.create(OpenWeatherService.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent mIntent = getIntent();
        String city = mIntent.getStringExtra("City");
        if (city != null) {
            service.currentByQuery(city, APP_ID, METRIC).enqueue(new Callback<CurrentWithName>() {
                @Override
                public void onResponse(Call<CurrentWithName> call, Response<CurrentWithName> response) {
                    updateUI(response.body());
                }

                @Override
                public void onFailure(Call<CurrentWithName> call, Throwable t) {

                }
            });
        } else {
            getWeatherForCurrentLocation();
        }
    }

    private void getWeatherForCurrentLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mLocationListner);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Location Get Succesffully", Toast.LENGTH_SHORT).show();
                getWeatherForCurrentLocation();
            } else {
                //user denied the permission
                Toast.makeText(getApplicationContext(), "No worries! you can search for your location.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void updateUI(CurrentWithName weather) {
        if (weather != null) {
            adapter.setData(weather);
            //trying to fetch hourly and daily data
            service.oneCallByLatLon(String.valueOf(weather.getCoord().getLat()),
                    String.valueOf(weather.getCoord().getLon()), APP_ID, METRIC).enqueue(new Callback<WeatherStats>() {
                @Override
                public void onResponse(Call<WeatherStats> call, Response<WeatherStats> response) {
                    if(response.isSuccessful()){
                        adapter.setHourlyDailyData(response.body());
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "Failed to get hourly and daily data", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<WeatherStats> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Failed to get data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListner);
        }
    }
}
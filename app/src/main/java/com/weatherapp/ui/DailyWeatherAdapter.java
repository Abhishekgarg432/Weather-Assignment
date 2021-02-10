package com.weatherapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weatherapp.pojo.CurrentWithName;
import com.weatherapp.pojo.Daily;
import com.weatherapp.pojo.Weather;
import com.weatherapp.pojo.WeatherStats;

import java.util.Calendar;

public class DailyWeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    int HEADER = 0;
    int DAY_ROW = 1;
    private final Activity context;
    private CurrentWithName weather;
    private WeatherStats aggregatedData;

    public DailyWeatherAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_daily, parent, false);
        return new DailyWeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder dailyWeatherViewHolder = (HeaderViewHolder) holder;
            dailyWeatherViewHolder.bind(weather);
        } else {
            DailyWeatherViewHolder dailyWeatherViewHolder = (DailyWeatherViewHolder) holder;
            dailyWeatherViewHolder.bind();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        }
        return DAY_ROW;
    }

    @Override
    public int getItemCount() {
        return 1 + (aggregatedData == null ? 0 : aggregatedData.getDaily().size());
    }

    public void setData(CurrentWithName weather) {
        this.weather = weather;
        notifyDataSetChanged();
    }

    public void setHourlyDailyData(WeatherStats aggregatedData) {
        this.aggregatedData = aggregatedData;
        notifyItemRangeInserted(1, aggregatedData.getDaily().size());
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView cityTextView, weatherStateTextVire, tempTextView;
        private TextView airpressuetv, humiditytv, maxtemptv, mintemptv;
        ImageView weatherIconImageView;
        RelativeLayout cirtFinderRl;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            weatherStateTextVire = itemView.findViewById(R.id.weatherCondition);
            tempTextView = itemView.findViewById(R.id.temp);
            weatherIconImageView = itemView.findViewById(R.id.weatherIcon);
            humiditytv = itemView.findViewById(R.id.humiditytv);
            maxtemptv = itemView.findViewById(R.id.maxtemptv);
            mintemptv = itemView.findViewById(R.id.mintemptv);
            airpressuetv = itemView.findViewById(R.id.airpressuetv);
            cirtFinderRl = itemView.findViewById(R.id.cityFinder);
            cityTextView = itemView.findViewById(R.id.cityName);
            cirtFinderRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CityFinder.class);
                    context.startActivity(intent);
                }
            });
        }

        public void bind(CurrentWithName weather) {
            if (weather != null) {
                tempTextView.setText(weather.getMain().getTemp() + "°C");
                cityTextView.setText(weather.getName());
                Weather weatherMain = weather.getWeather().get(0);
                weatherStateTextVire.setText(weatherMain.getMain());
                int resourceID = context.getResources().getIdentifier(
                        weatherMain.getIconDrawable(), "drawable", context.getPackageName());
                weatherIconImageView.setImageResource(resourceID);
                humiditytv.setText(String.valueOf(weather.getMain().getHumidity()));
                airpressuetv.setText(String.valueOf(weather.getMain().getPressure()));
                mintemptv.setText(weather.getMain().getTempMin() + "°C");
                maxtemptv.setText(weather.getMain().getTempMax() + "°C");
            }
        }
    }

    class DailyWeatherViewHolder extends RecyclerView.ViewHolder {

        private final TextView daytv, mintemptv;
        private final ImageView statetv;

        public DailyWeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            daytv = itemView.findViewById(R.id.daytv);
            statetv = itemView.findViewById(R.id.statetv);
            mintemptv = itemView.findViewById(R.id.minmaxtv);
        }

        public void bind() {
            Daily daily = aggregatedData.getDaily().get(getAdapterPosition() - 1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(daily.getDt() * 1000);
            daytv.setText("Date - " +( String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))));
            mintemptv.setText(daily.getTemp().getMin() + "°C/ " + daily.getTemp().getMax() + "°C");
            int resourceID = context.getResources().getIdentifier(
                    daily.getWeather().get(0).getIconDrawable(), "drawable", context.getPackageName());
            statetv.setImageResource(resourceID);
        }
    }
}

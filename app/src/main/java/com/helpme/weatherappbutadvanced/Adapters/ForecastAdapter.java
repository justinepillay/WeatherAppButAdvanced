package com.helpme.weatherappbutadvanced.Adapters;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.helpme.weatherappbutadvanced.MainActivity;
import com.helpme.weatherappbutadvanced.Models.ForecastItem;
import com.helpme.weatherappbutadvanced.Models.WeeklyForecast.Forecast;
import com.helpme.weatherappbutadvanced.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>
{
    Context ctx = new MainActivity();
    private List<Forecast> forecasts;

    private OnItemClickListener fListener;


    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemCLickListener(OnItemClickListener listener)
    {

        fListener = listener;
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder
    {

        ImageView ivIcon;
        TextView tvMaxTemp;
        TextView tvMinTemp;
        TextView tvStatus;
        TextView tvDate;
        Context ctx;

        public ForecastViewHolder(@NonNull View itemView, final OnItemClickListener listener)
        {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvMaxTemp = itemView.findViewById(R.id.tvMaxTemp);
            tvMinTemp = itemView.findViewById(R.id.tvMinTemp);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDate = itemView.findViewById(R.id.tvDate);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });


        }
    }

    public ForecastAdapter(List<Forecast> inForecasts)
    {
        forecasts = inForecasts;
    }


    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_item, parent, false);
        ForecastViewHolder fvh = new ForecastViewHolder(v, fListener);

        return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position)
    {

        Forecast currentItem = forecasts.get(position);



        String url = String.format("https://developer.accuweather.com/sites/default/files/%02d-s.png",currentItem.getDay().getIcon());


        Picasso.get().load(url).into(holder.ivIcon);

        holder.tvMaxTemp.setText(currentItem.getTemperature().getMaximum().getValue()+"");
        holder.tvMinTemp.setText(currentItem.getTemperature().getMinimum().getValue()+"");
        holder.tvStatus.setText(currentItem.getDay().getIconPhrase()+"");
        holder.tvDate.setText(currentItem.getDate().substring(0,10));
    }

    @Override
    public int getItemCount()
    {
        return forecasts.size();
    }


}

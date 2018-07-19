package com.linwei.elderlycare.elderlycaringsystemclient.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linwei.elderlycare.elderlycaringsystemclient.R;
import com.linwei.elderlycare.elderlycaringsystemclient.entities.Sensor;

import java.util.List;

public class CardRecyAdapter extends RecyclerView.Adapter<CardRecyAdapter.ViewHolder> {
    private List<Sensor> sensors;

    public CardRecyAdapter(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sensor s = sensors.get(position);
        //holder.sensorname.setText(s.getSensorDescription());
    }

    @Override
    public int getItemCount() {
        return sensors == null ? 0 : sensors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView sensorname;

        public ViewHolder(View v) {
            super(v);
            //sensorname=v.findViewById(R.id.tv_sensorname);
        }
    }
}


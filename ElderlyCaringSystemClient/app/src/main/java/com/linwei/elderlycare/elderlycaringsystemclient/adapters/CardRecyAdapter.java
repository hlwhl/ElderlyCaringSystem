package com.linwei.elderlycare.elderlycaringsystemclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linwei.elderlycare.elderlycaringsystemclient.R;
import com.linwei.elderlycare.elderlycaringsystemclient.activities.SensorHistoryActivity;
import com.linwei.elderlycare.elderlycaringsystemclient.entities.Sensor;

import java.util.List;

public class CardRecyAdapter extends RecyclerView.Adapter<CardRecyAdapter.ViewHolder> {
    private static List<Sensor> sensors;
    private static Context context;

    public CardRecyAdapter(Context context, List<Sensor> sensors) {
        CardRecyAdapter.sensors = sensors;
        CardRecyAdapter.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        //TODO:根据传感器类型加载不同card layout

        return super.getItemViewType(position);
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
        if (s.isOn()) {
            holder.status.setText("Sensor Status: Power On and Normal");
        } else {
            holder.status.setText("Sensor Status: Sensor Power Off");
        }
        holder.title.setText(s.getTitle());
        holder.description.setText(s.getSensorDescription());
        holder.address.setText("Sensor Bluetooth Address: " + s.getSensorBTAddress());
    }

    @Override
    public int getItemCount() {
        return sensors == null ? 0 : sensors.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, status, description, address;

        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.card_title);
            status = v.findViewById(R.id.card_sensorstatus);
            description = v.findViewById(R.id.card_description);
            address = v.findViewById(R.id.card_btAddress);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SensorHistoryActivity.class);
                    intent.putExtra("sensor", sensors.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}


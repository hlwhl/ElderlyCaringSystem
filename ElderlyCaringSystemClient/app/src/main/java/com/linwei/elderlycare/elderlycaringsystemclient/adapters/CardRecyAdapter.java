package com.linwei.elderlycare.elderlycaringsystemclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linwei.elderlycare.elderlycaringsystemclient.R;
import com.linwei.elderlycare.elderlycaringsystemclient.activities.SensorHistoryActivity;
import com.linwei.elderlycare.elderlycaringsystemclient.entities.Sensor;
import com.linwei.elderlycare.elderlycaringsystemclient.entities.SensorDataHistory;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CardRecyAdapter extends RecyclerView.Adapter<CardRecyAdapter.ViewHolder> {
    private static List<Sensor> sensors;
    private static Context context;

    public CardRecyAdapter(Context context, List<Sensor> sensors) {
        CardRecyAdapter.sensors = sensors;
        CardRecyAdapter.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        //根据传感器类型加载不同card layout
        if (sensors.get(position).getTitle().equals("Temperature")) {
            return 1;
        }
        return super.getItemViewType(position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //根据ViewType加载布局文件
        View view = null;
        if (viewType == 0) {
            //DefaultView
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        } else if (viewType == 1) {
            //Temperature View
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_temp, parent, false);
        }
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        Sensor s = sensors.get(position);
        if (s.getTitle().equals("Temperature")) {
            BmobQuery<SensorDataHistory> query = new BmobQuery<>();
            query.addWhereEqualTo("sensor", s.getObjectId());
            query.setLimit(1);
            query.order("-createdAt");
            query.findObjects(new FindListener<SensorDataHistory>() {
                @Override
                public void done(List<SensorDataHistory> list, BmobException e) {
                    holder.temp.setText(list.get(0).getContent());
                    if (Integer.parseInt(list.get(0).getContent()) > 24) {
                        holder.tempHint.setText("The temperature is a little hot.");
                        holder.cardView.setCardBackgroundColor(Color.RED);
                        holder.title.setTextColor(Color.WHITE);
                        holder.temp.setTextColor(Color.WHITE);
                        holder.tempHint.setTextColor(Color.WHITE);
                    } else if (Integer.parseInt(list.get(0).getContent()) < 21) {
                        holder.tempHint.setText("The temperature is a little cold.");
                        holder.cardView.setCardBackgroundColor(Color.BLUE);
                        holder.title.setTextColor(Color.WHITE);
                        holder.temp.setTextColor(Color.WHITE);
                        holder.tempHint.setTextColor(Color.WHITE);
                    } else {
                        holder.cardView.setCardBackgroundColor(Color.GREEN);
                        holder.tempHint.setText("The Temperature is perfect for living.");
                        holder.title.setTextColor(Color.BLACK);
                        holder.temp.setTextColor(Color.BLACK);
                        holder.tempHint.setTextColor(Color.BLACK);
                    }
                }
            });
            return;
        }

        if (s.isOn()) {
            holder.status.setText("Sensor Status: Power On");
        } else {
            holder.status.setText("Sensor Status: Power Off");
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
        public TextView title, status, description, address, temp, tempHint;
        public CardView cardView;

        public ViewHolder(View v, int viewType) {
            super(v);
            title = v.findViewById(R.id.card_title);

            //默认View处理
            if (viewType == 0) {
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
            //温度View处理
            if (viewType == 1) {
                cardView = v.findViewById(R.id.cardview_temp);
                temp = v.findViewById(R.id.tv_temp);
                tempHint = v.findViewById(R.id.tv_hint_temp);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context,SensorHistoryActivity.class);
                        intent.putExtra("sensor", sensors.get(getAdapterPosition()));
                        context.startActivity(intent);
                    }
                });
            }
        }
    }
}


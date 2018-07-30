package com.linwei.elderlycare.elderlycaringsystemclient.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linwei.elderlycare.elderlycaringsystemclient.R;
import com.linwei.elderlycare.elderlycaringsystemclient.entities.SensorDataHistory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class HistoryDataAdapter extends RecyclerView.Adapter<HistoryDataAdapter.ViewHolder> {
    private List<SensorDataHistory> hislist;
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL = 0x0001;
    private LayoutInflater inflater;

    public HistoryDataAdapter(Context context, List<SensorDataHistory> hislist) {
        this.hislist = hislist;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_his, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder itemHolder = holder;
        if (position == hislist.size() - 1) {
            // 第一行头的竖线不显示
            itemHolder.tvTopLine.setVisibility(View.INVISIBLE);
            // 字体颜色加深
            itemHolder.time.setTextColor(0xff555555);
            itemHolder.content.setTextColor(0xff555555);
            itemHolder.tvDot.setBackgroundResource(R.drawable.timelline_dot_first);
        } else {
            itemHolder.tvTopLine.setVisibility(View.VISIBLE);
            itemHolder.time.setTextColor(0xff999999);
            itemHolder.content.setTextColor(0xff999999);
            itemHolder.tvDot.setBackgroundResource(R.drawable.timelline_dot_normal);
        }

        itemHolder.bindHolder(hislist.get(position));
    }

    @Override
    public int getItemCount() {
        return hislist.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView time, content;
        private TextView tvTopLine, tvDot;

        public ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tvTime);
            content = itemView.findViewById(R.id.tvContent);
            tvTopLine = itemView.findViewById(R.id.tvTopLine);
            tvDot = itemView.findViewById(R.id.tvDot);
        }

        public void bindHolder(SensorDataHistory his) {
            time.setText(transformTime(his.getCreatedAt()));
            content.setText(his.getContent());
        }
    }

    public String transformTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(time);
            TimeZone oldZone = TimeZone.getTimeZone("GMT+8");
            TimeZone newZone = TimeZone.getDefault();
            if (date != null) {
                int timeOffset = oldZone.getRawOffset() - newZone.getRawOffset();
                Date newDate = new Date(date.getTime() - timeOffset);
                return newDate.toLocaleString();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

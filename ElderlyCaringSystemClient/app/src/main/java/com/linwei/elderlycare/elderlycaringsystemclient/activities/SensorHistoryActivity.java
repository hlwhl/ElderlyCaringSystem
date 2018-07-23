package com.linwei.elderlycare.elderlycaringsystemclient.activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.linwei.elderlycare.elderlycaringsystemclient.R;
import com.linwei.elderlycare.elderlycaringsystemclient.adapters.HistoryDataAdapter;
import com.linwei.elderlycare.elderlycaringsystemclient.entities.Sensor;
import com.linwei.elderlycare.elderlycaringsystemclient.entities.SensorDataHistory;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SensorHistoryActivity extends AppCompatActivity {
    private RecyclerView recy_his;
    private HistoryDataAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_history);
        recy_his = findViewById(R.id.his_list);
        swipeRefreshLayout = findViewById(R.id.refresh_his);

        Intent intent = getIntent();
        sensor = (Sensor) intent.getSerializableExtra("sensor");
        getSupportActionBar().setTitle(sensor.getTitle() + " History Data");


        BmobQuery<SensorDataHistory> query = new BmobQuery<SensorDataHistory>();
        query.addWhereEqualTo("sensor", sensor.getObjectId());
        query.findObjects(new FindListener<SensorDataHistory>() {
            @Override
            public void done(List<SensorDataHistory> list, BmobException e) {
                if (e == null) {
                    adapter = new HistoryDataAdapter(getApplicationContext(), list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    linearLayoutManager.setStackFromEnd(true);
                    linearLayoutManager.setReverseLayout(true);
                    recy_his.setLayoutManager(linearLayoutManager);
                    recy_his.setItemAnimator(new DefaultItemAnimator());
                    recy_his.setAdapter(adapter);
                } else {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BmobQuery<SensorDataHistory> query = new BmobQuery<SensorDataHistory>();
                query.addWhereEqualTo("sensor", sensor.getObjectId());
                query.findObjects(new FindListener<SensorDataHistory>() {
                    @Override
                    public void done(List<SensorDataHistory> list, BmobException e) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (e == null) {
                            HistoryDataAdapter newadapter = new HistoryDataAdapter(getApplicationContext(), list);
                            recy_his.swapAdapter(newadapter, true);
                            Toast.makeText(getApplicationContext(), "Refresh Succeed", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }


}

package com.linwei.elderlycare.elderlycaringsystemclient.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.linwei.elderlycare.elderlycaringsystemclient.R;
import com.linwei.elderlycare.elderlycaringsystemclient.adapters.HistoryDataAdapter;
import com.linwei.elderlycare.elderlycaringsystemclient.entities.Sensor;
import com.linwei.elderlycare.elderlycaringsystemclient.entities.SensorDataHistory;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class SensorHistoryActivity extends AppCompatActivity {
    private RecyclerView recy_his;
    private HistoryDataAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Sensor sensor;
    private LineChartView chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_history);


        Intent intent = getIntent();
        sensor = (Sensor) intent.getSerializableExtra("sensor");
        if (sensor.getTitle().equals("Temperature")) {
            chart = findViewById(R.id.linechart_temp);
            chart.setVisibility(View.VISIBLE);
            chart.setInteractive(false);
        }

        recy_his = findViewById(R.id.his_list);
        swipeRefreshLayout = findViewById(R.id.refresh_his);
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


                    //Set temp chart
                    if (sensor.getTitle().equals("Temperature")) {
                        List<PointValue> values = new ArrayList<PointValue>();
                        for (int i = 0; i < list.size(); i++) {
                            values.add(new PointValue(i, Integer.parseInt(list.get(i).getContent())));
                        }
                        Line line = new Line(values).setColor(Color.BLUE).setCubic(true);
                        List<Line> lines = new ArrayList<Line>();
                        lines.add(line);
                        LineChartData data = new LineChartData();
                        data.setLines(lines);
                        //set axis
                        setChartAxis(data);
                        chart.setLineChartData(data);
                    }
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

    public void setChartAxis(LineChartData data) {
        //Axis x
        Axis x = new Axis();
        //设置斜体
        x.setHasTiltedLabels(true);
        x.setTextColor(Color.BLACK);
        data.setAxisXBottom(x);

        //Axis y
        Axis y = new Axis();
        y.setName("Temp ℃");
        y.setTextColor(Color.BLACK);
        data.setAxisYLeft(y);
    }
}

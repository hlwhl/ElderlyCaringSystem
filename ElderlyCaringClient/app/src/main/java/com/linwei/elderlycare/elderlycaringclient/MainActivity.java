/*
 * Copyright (c) 2018. Created by Linwei Hao. All rights reserved.
 * Project Name : ElderlyCaringClient
 * File Name : MainActivity.java
 * Date : 09/07/18 11:40
 * Author : Linwei Hao
 */

package com.linwei.elderlycare.elderlycaringclient;

import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler=new Handler();
    private TextView tv;
    private String result;
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            try{
                URL url=new URL("http://ec2-35-178-62-54.eu-west-2.compute.amazonaws.com:8000");
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(5*1000);
                if (urlConnection.getResponseCode() == 200){
                    InputStream is=urlConnection.getInputStream();
                    result=HttpUtils.readMyInputStream(is);
                    updateUI(result);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            mHandler.postDelayed(runnable,1000);
        }
    };

    public void start() {
        stop();
        mHandler.post(runnable);
    }

    private void stop() {
        mHandler.removeCallbacks(runnable);//移除回调
    }

    public void updateUI(String result){
        tv.setText(result);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        tv=findViewById(R.id.maintext);
        start();
    }


}

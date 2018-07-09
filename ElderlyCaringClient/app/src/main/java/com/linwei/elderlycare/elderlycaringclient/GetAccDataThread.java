package com.linwei.elderlycare.elderlycaringclient;

import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetAccDataThread extends Thread {

    private Handler mHandler;

    public GetAccDataThread(Handler mHandler){
        this.mHandler=mHandler;
    }

    @Override
    public void run() {
        try{
            URL url=new URL("http://ec2-35-178-62-54.eu-west-2.compute.amazonaws.com:8000");
            HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5*1000);
            if (urlConnection.getResponseCode() == 200){
                InputStream is=urlConnection.getInputStream();
                String result=HttpUtils.readMyInputStream(is);
                Message msg=new Message();
                msg.obj=result;
                msg.what=1;
                mHandler.sendMessage(msg);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

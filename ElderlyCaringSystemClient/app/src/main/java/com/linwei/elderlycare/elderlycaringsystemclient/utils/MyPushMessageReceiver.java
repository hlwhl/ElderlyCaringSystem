package com.linwei.elderlycare.elderlycaringsystemclient.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.linwei.elderlycare.elderlycaringsystemclient.R;
import com.linwei.elderlycare.elderlycaringsystemclient.activities.SplashActivity;
import com.linwei.elderlycare.elderlycaringsystemclient.activities.WarningActivity;

import cn.bmob.push.PushConstants;
import cn.bmob.v3.util.BmobNotificationManager;

public class MyPushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            //Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra("msg"));

            Intent pendingIntent=new Intent(context, WarningActivity.class);
            pendingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(pendingIntent);
            Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            BmobNotificationManager.getInstance(context).showNotification(largeIcon,"666",intent.getStringExtra("msg"),"666",pendingIntent);
        }
    }
}

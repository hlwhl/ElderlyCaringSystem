package com.linwei.elderlycare.elderlycaringsystemclient.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class TestArrayAdapter extends ArrayAdapter {
    public TestArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    //getView方法，在每一次item从屏幕外滑进屏幕内或程序刚开始的时候创建第一屏item时调用
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}

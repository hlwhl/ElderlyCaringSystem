package com.linwei.elderlycare.elderlycaringsystemclient.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linwei.elderlycare.elderlycaringsystemclient.R;

import butterknife.BindView;

public class WarningActivity extends AppCompatActivity {
    @BindView(R.id.call_ward)
    Button callWard;
    @BindView(R.id.call_emergency)
    Button callEmergency;
    String phonenum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
        getSupportActionBar().setTitle("Warning");

        phonenum = "666666";

        callWard.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenum));
                startActivity(intent);
            }
        });
    }
}

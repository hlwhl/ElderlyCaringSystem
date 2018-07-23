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

public class WarningActivity extends AppCompatActivity implements View.OnClickListener{
    Button callWard;
    Button callEmergency;
    String wardNum;
    String emgNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
        getSupportActionBar().setTitle("Warning");
        callWard = findViewById(R.id.call_ward);
        callEmergency = findViewById(R.id.call_emergency);

        wardNum = "666666";
        emgNum = "112";

        callWard.setOnClickListener(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.call_ward:
                Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + wardNum));
                startActivity(intent1);
                break;
            case R.id.call_emergency:
                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + emgNum));
                startActivity(intent2);
                break;
        }
    }
}

package com.linwei.elderlycare.elderlycaringsystemclient.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.linwei.elderlycare.elderlycaringsystemclient.R;

import cn.bmob.v3.BmobUser;

public class WarningActivity extends AppCompatActivity implements View.OnClickListener{
    Button callWard;
    Button callEmergency;
    String wardNum;
    String emgNum;
    TextView title,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
        getSupportActionBar().setTitle("Warning");

        title=findViewById(R.id.tv_emgTitle);
        description=findViewById(R.id.tv_description);

        Intent intent=getIntent();
        String msg=intent.getStringExtra("msg");
        if(msg.contains("Panic")){
            title.setText("Panic Button Pressed!");
            description.setText("The panic button has been pressed! The ward may in emergency situation!");
        }

        callWard = findViewById(R.id.call_ward);
        callEmergency = findViewById(R.id.call_emergency);

        wardNum = BmobUser.getCurrentUser().getMobilePhoneNumber();
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

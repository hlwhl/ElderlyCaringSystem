package com.linwei.elderlycare.elderlycaringsystemclient.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.linwei.elderlycare.elderlycaringsystemclient.R;

public class AddSensorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_sensor);
        LinearLayout linearLayout = new LinearLayout(this);
        final EditText sensorAddress = new EditText(this);
        sensorAddress.setHint("Please input your sensor's Bluetooth address");
        Button btn_submit = new Button(this);
        btn_submit.setText("Submit");
        linearLayout.addView(sensorAddress);
        linearLayout.addView(btn_submit);
        setContentView(linearLayout);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), sensorAddress.getText().toString(), Toast.LENGTH_LONG);
            }
        });
    }
}

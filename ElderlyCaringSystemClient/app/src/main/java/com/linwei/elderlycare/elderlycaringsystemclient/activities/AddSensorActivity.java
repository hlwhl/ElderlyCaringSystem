package com.linwei.elderlycare.elderlycaringsystemclient.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.linwei.elderlycare.elderlycaringsystemclient.R;

public class AddSensorActivity extends AppCompatActivity {
    EditText sensorAddress, sensorDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsensor);

//        LinearLayout linearLayout = new LinearLayout(this);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        sensorAddress = new EditText(this);
//        sensorAddress.setHint("Please input your sensor's Bluetooth address");
//        sensorDescription = new EditText(this);
//        sensorDescription.setHint("Please describe the sensor here");
//        Button btn_submit = new Button(this);
//        btn_submit.setText("Submit");
//        linearLayout.addView(sensorAddress);
//        linearLayout.addView(sensorDescription);
//        linearLayout.addView(btn_submit);
//        setContentView(linearLayout);
//
//        btn_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), sensorAddress.getText().toString(), Toast.LENGTH_LONG);
//                Sensor sensor = new Sensor();
//                sensor.setOwner(BmobUser.getCurrentUser());
//                sensor.setSensorBTAddress(sensorAddress.getText().toString());
//                sensor.setSensorDescription(sensorDescription.getText().toString());
//                sensor.save(new SaveListener<String>() {
//                    @Override
//                    public void done(String s, BmobException e) {
//                        if (e == null) {
//                            Toast.makeText(getApplicationContext(), "Sensor information saved", Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(AddSensorActivity.this, HomeActivity.class);
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//            }
//        });
    }
}

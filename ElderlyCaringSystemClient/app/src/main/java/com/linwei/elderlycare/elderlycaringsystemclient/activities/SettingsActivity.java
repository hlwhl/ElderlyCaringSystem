package com.linwei.elderlycare.elderlycaringsystemclient.activities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.linwei.elderlycare.elderlycaringsystemclient.R;

public class SettingsActivity extends AppCompatActivity {
    Switch bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setTitle("Settings");

        bio = findViewById(R.id.switch_Bio);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean isBioOn;
        isBioOn = sharedPref.getBoolean("bio", false);
        if (isBioOn) {
            bio.setChecked(true);
        }


        bio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    verifyBio(b);
                } else {
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("bio", b);
                    if (editor.commit()) {
                        Toast.makeText(getApplicationContext(), "Settings Changed", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void verifyBio(final boolean b) {
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            //API28 create biometricPrompt
            BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(getApplicationContext())
                    .setDescription("Need Verify To Turn On Biometric Verification")
                    .setTitle("Biometric Verify")
                    .setNegativeButton("Cancel", getMainExecutor(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), "Canceled.Exiting...", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    })
                    .build();
            CancellationSignal cancellationSignal = new CancellationSignal();
            biometricPrompt.authenticate(cancellationSignal, getMainExecutor(), new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    Toast.makeText(getApplicationContext(), errString.toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                }

                @Override
                public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    // 写入设置
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("bio", b);
                    if (editor.commit()) {
                        Toast.makeText(getApplicationContext(), "Settings Changed", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}

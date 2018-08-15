package com.linwei.elderlycare.elderlycaringsystemclient.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.linwei.elderlycare.elderlycaringsystemclient.R;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.exception.BmobException;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //权限检查
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }


        //TODO 集成：1.4、初始化数据服务SDK、初始化设备信息并启动推送服务
        // 初始化BmobSDK
        Bmob.initialize(this, "718cb7645ebfcd11e7af7fc89230d1ce");
        // 使用推送服务时的初始化操作
        BmobInstallationManager.getInstance().initialize(new InstallationListener<BmobInstallation>() {
            @Override
            public void done(BmobInstallation bmobInstallation, BmobException e) {
                if (e == null) {
                    Log.i("install",bmobInstallation.getObjectId() + "-" + bmobInstallation.getInstallationId());
                } else {
                    Log.e("install",e.getMessage());
                }
            }
        });
        // 启动推送服务
        BmobPush.startWork(this);

        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                //判断用户是否已经登陆
                BmobUser bmobUser = BmobUser.getCurrentUser();
                if (bmobUser != null) {

                    if (android.os.Build.VERSION.SDK_INT >= 28) {
                        //API28 create biometricPrompt
                        BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(getApplicationContext())
                                .setDescription("Need Verification before entered in")
                                .setTitle("Biometric Verify")
                                .setSubtitle("Subtitle")
                                .setNegativeButton("Cancel", getMainExecutor(), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .build();
                        CancellationSignal cancellationSignal = new CancellationSignal();
                        biometricPrompt.authenticate(cancellationSignal, getMainExecutor(), new BiometricPrompt.AuthenticationCallback() {
                            @Override
                            public void onAuthenticationError(int errorCode, CharSequence errString) {
                                super.onAuthenticationError(errorCode, errString);
                            }

                            @Override
                            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                                super.onAuthenticationSucceeded(result);
                                // 允许用户使用应用
                                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                    }


                } else {
                    //缓存用户对象为空时， 可打开用户注册界面…
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        }).sendEmptyMessageDelayed(0,2000);
    }
}

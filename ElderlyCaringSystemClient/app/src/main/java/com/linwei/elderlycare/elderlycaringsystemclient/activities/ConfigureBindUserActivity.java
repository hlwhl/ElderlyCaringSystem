package com.linwei.elderlycare.elderlycaringsystemclient.activities;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.linwei.elderlycare.elderlycaringsystemclient.R;
import com.linwei.elderlycare.elderlycaringsystemclient.entities.User;
import com.r0adkll.slidr.Slidr;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class ConfigureBindUserActivity extends AppCompatActivity {
    Button bind, unbind;
    TextView bindUser;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_bind_user);
        bindUser = findViewById(R.id.tvBindUserNameConfigure);

        getSupportActionBar().setTitle("Configure Bind User");

        final User currentUser = BmobUser.getCurrentUser(User.class);
        if (currentUser.getBindUser() != null) {
            if (currentUser.getBindUser().getObjectId() != "") {
                BmobQuery<User> q = new BmobQuery<User>();
                q.getObject(currentUser.getBindUser().getObjectId(), new QueryListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        bindUser.setText(user.getUsername());
                    }
                });
            }
        }

        Slidr.attach(this);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter the User's ID you want to bind");
        builder.setMessage("You can find the user ID in User's About page");
        final EditText editText = new EditText(getApplicationContext());
        builder.setView(editText);
        //增加确定和取消按钮
        builder.setPositiveButton("Bind", null);
        builder.setNegativeButton("Cancel", null);
        final AlertDialog dialog = builder.create();

        bind = findViewById(R.id.btn_bind);
        bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //绑定按钮

                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //点击确认绑定后的逻辑
                        BmobQuery<User> query = new BmobQuery<User>();
                        query.addWhereEqualTo("objectId", editText.getText());
                        query.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> list, BmobException e) {
                                if (e == null) {
                                    if (list.isEmpty()) {
                                        Toast.makeText(getApplicationContext(), "Can not find the user ID you entered, please try again.", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    } else {
                                        currentUser.setBindUser(new User(editText.getText().toString()));
                                        currentUser.update(currentUser.getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(getApplicationContext(), "Update succeed", Toast.LENGTH_LONG).show();
                                                    setBindUserName(editText.getText().toString());
                                                    dialog.dismiss();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                                    dialog.dismiss();
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                });

            }
        });

        unbind = findViewById(R.id.btn_unbind);
        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //解绑按钮
                currentUser.setBindUser(new User(""));
                currentUser.update(currentUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "Update succeed", Toast.LENGTH_LONG).show();
                            bindUser.setText("Not Set");
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                });

            }
        });
    }

    public void setBindUserName(String name) {
        bindUser.setText(name);
    }
}

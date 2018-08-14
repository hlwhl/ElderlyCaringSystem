package com.linwei.elderlycare.elderlycaringsystemclient.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.linwei.elderlycare.elderlycaringsystemclient.R;
import com.linwei.elderlycare.elderlycaringsystemclient.adapters.CardRecyAdapter;
import com.linwei.elderlycare.elderlycaringsystemclient.entities.Sensor;
import com.linwei.elderlycare.elderlycaringsystemclient.entities.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    User currentUser;
    private RecyclerView recyclerView;
    private CardRecyAdapter myAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvBindUser, tvCUser;
    private Button callBindUser, sendmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");

        swipeRefreshLayout = findViewById(R.id.refresh_home);

        currentUser = BmobUser.getCurrentUser(User.class);



        //Load Bind User info
        if (currentUser.getBindUser() != null) {
            setBindUserInfo();
        }

        //recycler view
        recyclerView = findViewById(R.id.recview_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //querry sensor information
        BmobQuery<Sensor> query = new BmobQuery<Sensor>();
        query.addWhereEqualTo("owner", currentUser);
        query.findObjects(new FindListener<Sensor>() {
            @Override
            public void done(List<Sensor> list, BmobException e) {
                if (e == null) {
                    //bind data to adapter
                    myAdapter = new CardRecyAdapter(getApplicationContext(), list);
                    recyclerView.setAdapter(myAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        //refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //querry sensor information
                BmobQuery<Sensor> query = new BmobQuery<Sensor>();
                query.addWhereEqualTo("owner", currentUser);
                query.findObjects(new FindListener<Sensor>() {
                    @Override
                    public void done(List<Sensor> list, BmobException e) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (e == null) {
                            //bind data to adapter
                            myAdapter = new CardRecyAdapter(getApplicationContext(), list);
                            recyclerView.swapAdapter(myAdapter, true);
                            Toast.makeText(getApplicationContext(), "Refresh Succeed", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        //悬浮按钮
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddSensorActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    //加载绑定的用户信息
    public void setBindUserInfo() {
        if (!currentUser.getBindUser().getObjectId().equals("")) {
            callBindUser = findViewById(R.id.btn_callbinduser);
            sendmessage = findViewById(R.id.btn_sendmessage);
            tvBindUser = findViewById(R.id.tvBindUserName);
            sendmessage.setVisibility(View.VISIBLE);
            callBindUser.setVisibility(View.VISIBLE);
            BmobQuery<User> q = new BmobQuery<User>();
            q.getObject(currentUser.getBindUser().getObjectId(), new QueryListener<User>() {
                @Override
                public void done(final User user, BmobException e) {
                    tvBindUser.setText(user.getUsername());
                    callBindUser.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void onClick(View view) {
                            if (!user.getPhonenum().equals("")) {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + user.getPhonenum()));
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "User did not set phone number", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    sendmessage.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void onClick(View view) {
                            if (!user.getPhonenum().equals("")) {
                                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + user.getPhonenum()));
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "User did not set phone number", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        tvCUser = findViewById(R.id.tv_cuser);
        tvCUser.setText("Logged User: " + currentUser.getUsername());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_about) {
            //跳转关于页面
            Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_setBindUser) {
            //绑定用户设置
            Intent intent = new Intent(HomeActivity.this, ConfigureBindUserActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_logout) {
            //登出
            BmobUser.logOut();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

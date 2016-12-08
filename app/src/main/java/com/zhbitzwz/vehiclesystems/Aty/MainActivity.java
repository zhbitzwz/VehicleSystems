package com.zhbitzwz.vehiclesystems.Aty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhbitzwz.vehiclesystems.Fragment.FragmentCar;
import com.zhbitzwz.vehiclesystems.Fragment.FragmentDevices;
import com.zhbitzwz.vehiclesystems.Fragment.FragmentHome;
import com.zhbitzwz.vehiclesystems.R;
/**
 *  主界面
 * Author: ZWZ
 */
public class MainActivity extends AppCompatActivity {

    //DrawerLayout布局
    DrawerLayout drawerLayout;
    //左抽屉视图
    private Toolbar toolbar;
    private ActionBar actionBar;

    //Login or not
    private boolean isLogin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        //设置菜单Toolbar和actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //设置Navigation抽屉
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        setupNavigationDrawerContent(navigationView);

        //设置头像和用户名
        setupUserInfo(navigationView);
        //First fragment
        setFragment(0);
    }

    private void setupUserInfo(NavigationView navigationView) {
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header,null);
        navigationView.addHeaderView(header);

        TextView tv_username = (TextView) header.findViewById(R.id.tv_username);
        //TODO Preference判断登录状态
        SharedPreferences preferences = getSharedPreferences("userInfo",
                MODE_PRIVATE);
        isLogin = preferences.getBoolean("isLogin", true);
        if (isLogin) {
            tv_username.setText(" 未登录");
        } else {
            tv_username.setText(preferences.getString("userEmail",""));
        }
        //点击头像跳转到用户设置Activity
        ImageView im_UserSettings = (ImageView) header.findViewById(R.id.account_png);
        im_UserSettings.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isLogin) {
                            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                            startActivity(intent);
                        }

                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.home:
                                item.setChecked(true);
                                setFragment(0);
                                drawerLayout.closeDrawer(GravityCompat.START);
//                                Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.car:
                                item.setChecked(true);
                                setFragment(1);
                                drawerLayout.closeDrawer(GravityCompat.START);
//                                Toast.makeText(getApplicationContext(),"car",Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.devices:
                                item.setChecked(true);
                                setFragment(2);
                                drawerLayout.closeDrawer(GravityCompat.START);
//                                Toast.makeText(MainActivity.this, "Launching " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.exit:
                                item.setChecked(true);
//                                Toast.makeText(getApplicationContext(),"exit",Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(0);
                                return true;
                        }
                        return true;
                    }
                }
        );
    }

    public void setFragment(int position) {

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                FragmentHome fragmentHome = new FragmentHome();
                fragmentTransaction.replace(R.id.fragment,fragmentHome);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                FragmentCar fragmentCar = new FragmentCar();
                fragmentTransaction.replace(R.id.fragment,fragmentCar);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                FragmentDevices fragmentDevices = new FragmentDevices();
                fragmentTransaction.replace(R.id.fragment,fragmentDevices);
                fragmentTransaction.commit();
                break;
        }
    }
}

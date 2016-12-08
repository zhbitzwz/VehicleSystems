package com.zhbitzwz.vehiclesystems.Aty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wilddog.wilddogauth.WilddogAuth;
import com.zhbitzwz.vehiclesystems.R;
/**
 *  用户设置界面
 * Author: ZWZ
 */
public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    //WilddogAuth
    private WilddogAuth mWilddogAuth;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        SettingsActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }
        /* Create the Wilddog ref that is used for all authentication with Wilddog */
        mWilddogAuth = WilddogAuth.getInstance();

        preferences = getSharedPreferences(
                "userInfo", MODE_PRIVATE);

        Button btnLoggingUp = (Button) findViewById(R.id.email_action_loggingup);
        TextView tv_SettingUsername = (TextView) findViewById(R.id.tv_username);

        tv_SettingUsername.setText(preferences.getString("userEmail",""));
        btnLoggingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLogin",true);
                editor.putString("userEmail","null");
                editor.putString("deviceId","null");
//            editor.putString("providerId",providerId);
                editor.commit();
                mWilddogAuth.signOut();
                Toast.makeText(SettingsActivity.this, "email logging up suceessful ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SettingsActivity.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
}

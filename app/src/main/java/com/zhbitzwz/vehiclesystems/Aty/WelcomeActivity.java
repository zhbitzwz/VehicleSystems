package com.zhbitzwz.vehiclesystems.Aty;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.zhbitzwz.vehiclesystems.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                finish();
                startActivity(intent);
                return true;
            }
        });
        handler.sendEmptyMessageDelayed(0,800);
    }
}

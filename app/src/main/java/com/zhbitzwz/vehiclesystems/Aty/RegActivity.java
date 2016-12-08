package com.zhbitzwz.vehiclesystems.Aty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wilddog.wilddogauth.WilddogAuth;
import com.wilddog.wilddogauth.core.Task;
import com.wilddog.wilddogauth.core.listener.OnCompleteListener;
import com.wilddog.wilddogauth.core.result.AuthResult;
import com.wilddog.wilddogauth.model.WilddogUser;
import com.zhbitzwz.vehiclesystems.R;
import com.zhbitzwz.vehiclesystems.Util.DataHelper;
import com.zhbitzwz.vehiclesystems.Util.ToastUtil;

/**
 * A login screen that offers login via email/password.
 */
public class RegActivity extends AppCompatActivity {

    private Toolbar toolbar;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private String loginEmail;
    private String loginPasseord;
    //WilddogAuth
    private WilddogAuth mWilddogAuth;
    // Data from the authenticated user
    private WilddogUser mUser;
    //Listener for Wilddog session changes
    private WilddogAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        RegActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

        /* Create the Wilddog ref that is used for all authentication with Wilddog */
        mWilddogAuth = WilddogAuth.getInstance();
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_action_toRegActivity);

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUserWithEmailAndPwForWilddog();
            }
        });
    }

    private void createUserWithEmailAndPwForWilddog() {
        loginEmail = DataHelper.getInputString(mEmailView);
        loginPasseord = DataHelper.getInputString(mPasswordView);
        if (DataHelper.isStringNull(loginEmail) || DataHelper.isStringNull(loginPasseord)) {
            ToastUtil.show(this,"输入不能为空");
//            Toast.makeText(this,"输入不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        mWilddogAuth.createUserWithEmailAndPassword(loginEmail,loginPasseord)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("result","Create user success");
                            SharedPreferences preferences = getSharedPreferences(
                                    "userInfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("isLogin",false);
                            editor.putString("userEmail",loginEmail);
                            editor.putString("deviceId",loginPasseord);
//                            editor.putString("providerId",providerId);
                            editor.commit();
                            Toast.makeText(RegActivity.this, "email register suceessful ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegActivity.this,MainActivity.class);
                            finish();
                            startActivity(intent);
                        } else {
                            Log.d("result","reason:" + task.getException().toString());
                        }
                    }
                });

    }

}


package com.zhbitzwz.vehiclesystems.Aty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
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
public class LoginActivity extends ActionBarActivity {

    // UI references.
    private Toolbar toolbar;
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

    private String userEmail;
    private String providerId;
    //TODO 实现硬件对接
    private String deviceId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        LoginActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }
        /* Create the Wilddog ref that is used for all authentication with Wilddog */
        mWilddogAuth = WilddogAuth.getInstance();
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        Button mEmailSignUpButton = (Button) findViewById(R.id.email_action_toRegActivity);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //野狗邮箱验证方式
                loginWithPassword();
            }
        });
        mEmailSignUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }

    private void loginWithPassword() {
        loginEmail = DataHelper.getInputString(mEmailView);
        loginPasseord = DataHelper.getInputString(mPasswordView);
//        loginEmail = mEmailView.getText().toString();
//        loginPasseord = mPasswordView.getText().toString();
        if (DataHelper.isStringNull(loginEmail) || DataHelper.isStringNull(loginPasseord)) {
            ToastUtil.show(this,"输入不能为空");
//            Toast.makeText(this,"输入不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        mWilddogAuth.signInWithEmailAndPassword(loginEmail,loginPasseord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                processTask(task);
            }
        });
    }

    /*
    * 处理授权登录成功后的结果
    * */
    private void processTask(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            Log.i(LoginActivity.class.getSimpleName(), task.getResult().getWilddogUser().getProviderId() + " auth successful");
//            setAuthenticatedUser(task.getResult().getWilddogUser());
            //TODO 这里只是邮箱登录方式，第三方登录需要重写另外一个方法
            mUser = task.getResult().getWilddogUser();
            userEmail = mUser.getEmail();
            providerId = mUser.getProviderId();
//            SharedPreferences preferences = getSharedPreferences("sp", FLAG);
            SharedPreferences preferences = getSharedPreferences(
                    "userInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isLogin",false);
            editor.putString("userEmail",userEmail);
            editor.putString("deviceId",deviceId);
//            editor.putString("providerId",providerId);
            editor.commit();
            Toast.makeText(LoginActivity.this, "email login suceessful ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//            finish();
            startActivity(intent);
        } else {
            showErrorDialog(task.getException().toString());
        }
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("ERROR")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}


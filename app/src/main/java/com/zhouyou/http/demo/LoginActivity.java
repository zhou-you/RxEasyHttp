/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhouyou.http.demo;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.demo.constant.AppConstant;
import com.zhouyou.http.demo.constant.ComParamContact;
import com.zhouyou.http.demo.model.AuthModel;
import com.zhouyou.http.demo.model.LoginCache;
import com.zhouyou.http.demo.model.LoginInfo;
import com.zhouyou.http.demo.token.TokenManager;
import com.zhouyou.http.demo.utils.MD5;
import com.zhouyou.http.demo.utils.Validator;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * <p>描述：登录测试</p>
 * 作者： zhouyou<br>
 * 日期： 2017/7/6 16:26 <br>
 * 版本： v1.0<br>
 */
public class LoginActivity extends AppCompatActivity {
    private EditText mEmailView;
    private EditText mPasswordView;
    RxPermissions rxPermissions;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mEmailView.setText("18688994275");
        mPasswordView.setText("123456");
        rxPermissions = new RxPermissions(this);
        autoLogin();
    }

    private void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            getPermissions(email, password);
        }
    }

    private boolean isEmailValid(String email) {
        return Validator.isMobile(email) || Validator.isEmail(email);
    }

    private boolean isPasswordValid(String password) {
        return Validator.isPassword(password);
    }

    /**
     * 用登录举例
     */
    public void onLogin(final String name, final String pass) {
        IProgressDialog mProgressDialog = new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
                dialog.setMessage("登录中...");
                return dialog;
            }
        };
        EasyHttp.post(ComParamContact.Login.PATH)
                .params(ComParamContact.Login.ACCOUNT, name)
                .params(ComParamContact.Login.PASSWORD, MD5.encrypt4login(pass, AppConstant.APP_SECRET))
                .sign(true)
                .timeStamp(true)
                .execute(new ProgressDialogCallBack<AuthModel>(mProgressDialog, true, true) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        showToast("登录失败！" + e.getMessage());
                    }

                    @Override
                    public void onSuccess(AuthModel authModel) {
                        showToast("登录成功！");
                        //授权类信息存入缓存
                        TokenManager.getInstance().setAuthModel(authModel);
                        //将用户和密码存入缓存
                        LoginCache.getInstance().save(name, pass);
                        //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //startActivity(intent);
                        finish();
                    }
                });
    }
    
    private void autoLogin() {
        LoginInfo loginCache = LoginCache.getInstance().get();
        if (loginCache != null) {
            final String user = loginCache.getUsername();
            final String pass = loginCache.getPassword();
            if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)) {
                mEmailView.setText(user);
                mPasswordView.setText(pass);
                final TextView et = new TextView(this);
                et.setPadding(50, 50, 50, 50);
                et.setText("用户名:" + user + "\r\n密    码:" + pass);
                new AlertDialog.Builder(this).setTitle("发现缓存用户").setView(et)
                        .setPositiveButton("自动登录", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showToast("自动登录中...");
                                getPermissions(user, pass);
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        }
    }

    public void getPermissions(final String name, final String pass) {
       rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
               .subscribe(new Consumer<Boolean>() {
                   @Override
                   public void accept(@NonNull Boolean aBoolean) throws Exception {
                       if(aBoolean){
                           //Toast.makeText(LoginActivity.this, "权限获取成功", Toast.LENGTH_SHORT).show();
                           onLogin(name, pass);
                       }else {
                           showMissingPermissionDialog();
                       }
                   }
               });
    }

    // 显示缺失权限提示
    public void showMissingPermissionDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.basic_help);
        builder.setMessage(R.string.basic_string_help_text);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.basic_quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton(R.string.basic_settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });

        builder.setCancelable(false);

        builder.show();
    }

    // 启动应用的设置
    public void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    private void showToast(String msg) {
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}


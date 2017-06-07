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

package com.zhouyou.http.demo.permissions;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.zhouyou.http.demo.R;
import com.zhouyou.http.utils.HttpLog;


@TargetApi(Build.VERSION_CODES.M)
public class ShadowActivity extends AppCompatActivity {

    private static final String PACKAGE_URL_SCHEME = "package:"; // 方案
    private static final int REQUEST_PERSSIONS=100;
    private static final int SETTING_PERSSIONS=101;

    private String[] lastPermissions;
    private int[] lastResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            handleIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String[] permissions = intent.getStringArrayExtra("permissions");
        requestPermissions(permissions, REQUEST_PERSSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERSSIONS:
                dealPermissions(permissions,grantResults);
                break;

            default:
                RxPermissions.getInstance(ShadowActivity.this).onRequestPermissionsResult(REQUEST_PERSSIONS, permissions, grantResults);
                finish();

        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case SETTING_PERSSIONS:
                dealSetPermissions();
                break;
            default:
                dealSetPermissions();

        }
    }

    /**
     * 判断是否在设置界面打开对应权限
     */
    private void dealSetPermissions(){
        int len=lastPermissions.length;
        for (int i=0;i<len;i++){
            if (lacksPermission(lastPermissions[i])){
                lastResults[i]=-1;
            }else {
                lastResults[i]=0;
            }
        }
        RxPermissions.getInstance(ShadowActivity.this).onRequestPermissionsResult(REQUEST_PERSSIONS, lastPermissions, lastResults);
        finish();
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {

        return ContextCompat.checkSelfPermission(this, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    /**
     * 判断请求到的权限
     * @param permissions
     * @param grantResults
     */
    private void dealPermissions(String[] permissions, int[] grantResults){
        int len = grantResults.length;
        String message = "";
        boolean refuseFlag=false;
        for (int ret = 0; ret < len; ret++) {
            int result = grantResults[ret];
            if (result == -1) {
                refuseFlag=true;
                String permission=permissions[ret];
                HttpLog.e("拒绝权限"+permission);
                String msg=getPermissionMess(permission);
                showMissingPermissionDialog(msg,permissions,grantResults);
                break;
            }
        }
        if (!refuseFlag){
            RxPermissions.getInstance(ShadowActivity.this).onRequestPermissionsResult(REQUEST_PERSSIONS, permissions, grantResults);
            finish();
        }


    }

    // 显示缺失权限提示
    private void showMissingPermissionDialog(String message, String[] permissions, int[] grantResults) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShadowActivity.this);
        builder.setTitle(R.string.get_permission);
        builder.setMessage(message);
        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RxPermissions.getInstance(ShadowActivity.this).onRequestPermissionsResult(REQUEST_PERSSIONS, permissions, grantResults);
                finish();
            }
        });

        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                lastPermissions=permissions;
                lastResults=grantResults;
                startAppSettings();
                //checkDrawOverlayPermission();
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    // 启动应用的设置
    public void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivityForResult(intent,SETTING_PERSSIONS);

    }

    public String getPermissionMess(String permission ){
        String message="";
        if (permission.equals(Manifest.permission.READ_PHONE_STATE)) {
            message = "我们需要获取设备的IMEI用于设备信息认证，保证账号登录的安全性。" + getString(R.string.app_name) + "不会拨打其他号码或终止通话。" +
                    "请在设置-应用-" + getString(R.string.app_name) + "-权限中开启电话权限，以正常登录" + getString(R.string.app_name) + "功能";

        } else if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            message = "请在设置-应用-" + getString(R.string.app_name) + "-权限中开启存储空间权限" +
                    ",以正常使用" + getString(R.string.app_name) + "功能";

        } else if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
            message = "因WiFi和蓝牙扫描需要获取模糊定位权限。" +
                    "请在设置-应用-" + getString(R.string.app_name) + "-权限中开启位置信息权限，以正常使用WiFi或蓝牙的添加设备功能";

        } else if (permission.equals(Manifest.permission.CAMERA)) {
            message = "请在设置-应用-" + getString(R.string.app_name) + "-权限中开启相机权限，以正常使用相机功能";
        } else {
            message = "请在设置-应用-" + getString(R.string.app_name) + "-权限中开启对应权限，以正常使用相关功能";
        }
        return message;
    }






    private boolean canDrawOverlays(String[] permissions) {
        if (permissions == null || permissions.length == 0)
            return true;
        for (String per : permissions) {
            if (per.equalsIgnoreCase(Manifest.permission.SYSTEM_ALERT_WINDOW)) {
                return checkDrawOverlayPermission();
            }
        }
        return true;
    }

    public boolean checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent,SETTING_PERSSIONS);
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}

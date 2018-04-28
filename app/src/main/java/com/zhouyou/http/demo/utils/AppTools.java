package com.zhouyou.http.demo.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.File;
import java.net.InetAddress;
import java.util.List;


/**
 * 类名: AppTools <br>
 * 描述: 应用工具类<br>
 *
 * @author zhouyou<br>
 * @version V2.0
 * @since JDK 1.6
 */
@SuppressLint("NewApi")
public class AppTools {
    private AppTools() {
    } // don't instantiate

    /**
     * <p><B>方法:</B><br/> startForwardActivity </p><br/>
     * <p><B>描述:</B><br/> 界面的跳转</p>
     *
     * @param context
     * @param forwardActivity 设定文件
     */
    public static void startForwardActivity(Activity context, Class<?> forwardActivity) {
        startForwardActivity(context, forwardActivity, false);
    }

    public static void startForwardActivity(Activity context, Class<?> forwardActivity, Boolean isFinish) {
        Intent intent = new Intent(context, forwardActivity);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        if (isFinish) {
            context.finish();
        }
    }

    public static void startForwardActivity(Activity context, Class<?> forwardActivity, Bundle bundle, Boolean isFinish, int animin, int animout) {
        Intent intent = new Intent(context, forwardActivity);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivity(intent);
        if (isFinish) {
            context.finish();
        }
        try {
            context.overridePendingTransition(animin, animout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startForwardActivity(Activity context, Class<?> forwardActivity, Bundle bundle, Boolean isFinish) {
        Intent intent = new Intent(context, forwardActivity);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivity(intent);
        if (isFinish) {
            context.finish();
        }
    }

    public static void startForResultActivity(Activity context, Class<?> forwardActivity, int requestCode, Bundle bundle, Boolean isFinish) {
        Intent intent = new Intent(context, forwardActivity);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
        if (isFinish) {
            context.finish();
        }
    }

    public static void startForResultActivity(Activity context, Class<?> forwardActivity, int requestCode, Bundle bundle, Boolean isFinish, int animin, int animout) {
        Intent intent = new Intent(context, forwardActivity);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
        if (isFinish) {
            context.finish();
        }
        try {
            context.overridePendingTransition(animin, animout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startForResultActivity2(Activity context, Class<?> forwardActivity, int requestCode, Bundle bundle, Boolean isFinish) {
        Intent intent = new Intent(context, forwardActivity);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
        if (isFinish) {
            context.finish();
        }
    }

    /**
     * @return 当前程序的版本名称
     */
    public static String getVersionName(Context context) {
        String version;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            version = "";
        }
        return version;
    }

    /**
     * 方法: getVersionCode
     * 描述: 获取客户端版本号
     *
     * @return int    版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            versionCode = 999;
        }
        return versionCode;
    }

    /**
     * 获取Mac地址
     *
     * @param activity
     * @return
     */
    public static String getLocalMacAddress(Activity activity) {
        WifiManager wifi = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String mac = info.getMacAddress();
        return mac;
    }

    /**
     * 获取本地Ip地址
     *
     * @param activity
     * @return
     */
    public static String getLocalIpAddress(Activity activity) {
        try {
            WifiManager wifi;
            wifi = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            int ipAddress = info.getIpAddress();
            String Ipv4Address = InetAddress
                    .getByName(
                            String.format("%d.%d.%d.%d", (ipAddress & 0xff),
                                    (ipAddress >> 8 & 0xff),
                                    (ipAddress >> 16 & 0xff),
                                    (ipAddress >> 24 & 0xff))).getHostAddress();
            return Ipv4Address;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param @param  context
     * @param @return 设定文件
     * @return String    返回类名
     * @Title: getTopActivity
     * @Description: 获取栈顶activity
     */
    @SuppressWarnings("deprecation")
    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null)
            return (runningTaskInfos.get(0).topActivity.getClassName());
        else
            return "";
    }

    /**
     * 判断某一Activity是否在当前栈顶
     *
     * @return true 当前Activity在栈顶，即在最前端显示
     * false 当前Activity不在栈顶，即在后台运行
     */
    public static boolean isTopActivity(Context context, String className) {
        final String topactivity = getTopActivity(context);
        return className.equals(topactivity);
    }

    /**
     * </br><b>title : </b>		设置Activity全屏显示
     * </br><b>description :</b>设置Activity全屏显示。
     *
     * @param activity Activity引用
     * @param isFull   true为全屏，false为非全屏
     */
    public static void setFullScreen(Activity activity, boolean isFull) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        if (isFull) {
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(params);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setAttributes(params);
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * </br><b>title : </b>		隐藏系统标题栏
     * </br><b>description :</b>隐藏Activity的系统默认标题栏
     *
     * @param activity Activity对象
     */
    public static void hideTitleBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * </br><b>title : </b>		设置Activity的显示方向为垂直方向
     * </br><b>description :</b>强制设置Actiity的显示方向为垂直方向。
     *
     * @param activity Activity对象
     */
    public static void setScreenVertical(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * </br><b>title : </b>		设置Activity的显示方向为横向
     * </br><b>description :</b>强制设置Actiity的显示方向为横向。
     *
     * @param activity Activity对象
     */
    public static void setScreenHorizontal(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * </br><b>title : </b>		使UI适配输入法
     * </br><b>description :</b>使UI适配输入法
     *
     * @param activity
     */
    public static void adjustSoftInput(Activity activity) {
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    /**
     * 安装指定APK文件
     *
     * @param activity Activity
     * @param apkFile  APK文件对象
     */
    public static void install(Activity activity, File apkFile) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }

    /**
     * 默认隐藏软键盘
     *
     * @param activity
     */
    public static void hideSoftInput(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    public static void hideSoftInput(Activity activity, IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点  
        return false;
    }

    /**
     * 判断APK包是否已经安装
     *
     * @param context     上下文，一般为Activity
     * @param packageName 包名
     * @return 包存在则返回true，否则返回false
     */
    public static boolean isPackageExists(Context context, String packageName) {
        if (null == packageName || "".equals(packageName)) {
            throw new IllegalArgumentException("Package name cannot be null or empty !");
        }
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return null != info;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 在Activity中获取MetaData
     *
     * @param context
     * @param key
     * @return Object
     */
    public static Object getMetaDataAsObject(Activity context, String key) {
        ActivityInfo info = getActivityInfo(context);
        return info == null ? null : info.metaData.get(key);
    }

    /**
     * 在Activity中获取MetaData
     *
     * @param context
     * @param key
     * @return String
     */
    public static String getMetaDataAsString(Activity context, String key) {
        ActivityInfo info = getActivityInfo(context);
        return info == null ? null : info.metaData.getString(key);
    }

    /**
     * 在Activity中获取MetaData
     *
     * @param context
     * @param key
     * @return int
     */
    public static int getMetaDataAsInt(Activity context, String key) {
        ActivityInfo info = getActivityInfo(context);
        return info == null ? null : info.metaData.getInt(key);
    }

    /**
     * 在Activity中获取MetaData
     *
     * @param context
     * @param key
     * @return boolean
     */
    public static boolean getMetaDataAsBoolean(Activity context, String key) {
        ActivityInfo info = getActivityInfo(context);
        return info == null ? null : info.metaData.getBoolean(key);
    }


    private static ActivityInfo getActivityInfo(Activity context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getActivityInfo(context.getComponentName(),
                    PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}

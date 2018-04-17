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

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.demo.utils.FileUtils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.utils.HttpLog;

/**
 * <p>描述：文件下载</p>
 * 作者： zhouyou<br>
 * 日期： 2017/7/6 16:25 <br>
 * 版本： v1.0<br>
 */
public class DownloadActivity extends AppCompatActivity {
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置进度条的形式为圆形转动的进度条
        dialog.setMessage("正在下载...");
        // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
        dialog.setTitle("下载文件");
        dialog.setMax(100);
    }

    public void onDownloadFile1(View view) {//下载回调是在异步里处理的
        EasyHttp.downLoad("http://apk.hiapk.com/web/api.do?qt=8051&id=723")
        //EasyHttp.downLoad("http://crfiles2.he1ju.com/0/925096f8-f720-4aa5-86ae-ef30548d2fdc.txt")
                .savePath(Environment.getExternalStorageDirectory().getPath()+"/test/")//默认在：/storage/emulated/0/Android/data/包名/files/1494647767055
                .saveName("custom_name")//默认名字是时间戳生成的
                .execute(new DownloadProgressCallBack<String>() {
                    @Override
                    public void update(long bytesRead, long contentLength, boolean done) {
                        int progress = (int) (bytesRead * 100 / contentLength);
                        HttpLog.e(progress + "% ");
                        dialog.setProgress(progress);
                        if (done) {
                            dialog.setMessage("下载完成");
                        }
                    }

                    @Override
                    public void onStart() {
                        HttpLog.i("======"+Thread.currentThread().getName());
                        dialog.show();
                    }

                    @Override
                    public void onComplete(String path) {
                        showToast("文件保存路径：" + path);
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(final ApiException e) {
                        HttpLog.i("======"+Thread.currentThread().getName());
                        showToast(e.getMessage());
                        dialog.dismiss();
                    }
                });
    }

    public void onDownloadFile2(View view) {
        //String url = "http://61.144.207.146:8081/b8154d3d-4166-4561-ad8d-7188a96eb195/2005/07/6c/076ce42f-3a78-4b5b-9aae-3c2959b7b1ba/kfid/2475751/qqlite_3.5.0.660_android_r108360_GuanWang_537047121_release_10000484.apk";
        //String url = "http://crfiles2.he1ju.com/0/925096f8-f720-4aa5-86ae-ef30548d2fdc.txt";
        String url = "http://txt.99dushuzu.com/download-txt/3/21068.txt";
        EasyHttp.downLoad(url)
                .savePath(Environment.getExternalStorageDirectory().getPath()+"/test/QQ")
                .saveName(FileUtils.getFileName(url))
                .execute(new DownloadProgressCallBack<String>() {
                    @Override
                    public void update(long bytesRead, long contentLength, boolean done) {
                        int progress = (int) (bytesRead * 100 / contentLength);
                        HttpLog.e(progress + "% ");
                        dialog.setProgress(progress);
                        if (done) {
                            dialog.setMessage("下载完成");
                        }
                    }

                    @Override
                    public void onStart() {
                        dialog.show();
                    }

                    @Override
                    public void onComplete(String path) {
                        showToast("文件保存路径：" + path);
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                        dialog.dismiss();
                    }
                });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}

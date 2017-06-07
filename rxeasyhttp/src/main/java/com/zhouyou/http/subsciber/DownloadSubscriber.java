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

package com.zhouyou.http.subsciber;

import android.content.Context;
import android.text.TextUtils;

import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.utils.HttpLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * <p>描述：定义一个下载的订阅者</p>
 * 作者： zhouyou<br>
 * 日期： 2016/12/19 16:35<br>
 * 版本： v2.0<br>
 */
public class DownloadSubscriber<ResponseBody extends okhttp3.ResponseBody> extends BaseSubscriber<ResponseBody> {
    private CallBack callBack;
    private Context context;
    private String path;
    private String name;

    private static String APK_CONTENTTYPE = "application/vnd.android.package-archive";
    private static String PNG_CONTENTTYPE = "image/png";
    private static String JPG_CONTENTTYPE = "image/jpg";
    private static String TEXT_CONTENTTYPE = "text/html; charset=utf-8";
    private static String fileSuffix = "";
    private long lastRefreshUiTime;

    public DownloadSubscriber(String path, String name, CallBack callBack, Context context) {
        super(context);
        this.path = path;
        this.name = name;
        this.callBack = callBack;
        this.context = context;
        this.lastRefreshUiTime = System.currentTimeMillis();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (callBack != null) {
            callBack.onStart();
        }
    }

    @Override
    public void onCompleted() {
        if (callBack != null) {
            //callBack.onComplete();
        }
    }

    @Override
    public void onError(final ApiException e) {
        HttpLog.d("DownSubscriber:>>>> onError:" + e.getMessage());
        callBack.onError(e);
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        HttpLog.d("DownSubscriber:>>>> onNext");
        writeResponseBodyToDisk(path, name, context, responseBody);

    }

    private boolean writeResponseBodyToDisk(String path, String name, Context context, okhttp3.ResponseBody body) {
        HttpLog.d("contentType:>>>>" + body.contentType().toString());
        if (!TextUtils.isEmpty(name)) {//text/html; charset=utf-8
            String type = "";
            if (!name.contains(".")) {
                type = body.contentType().toString();
                if (type.equals(APK_CONTENTTYPE)) {
                    fileSuffix = ".apk";
                } else if (type.equals(PNG_CONTENTTYPE)) {
                    fileSuffix = ".png";
                } else if (type.equals(JPG_CONTENTTYPE)) {
                    fileSuffix = ".jpg";
                } else {
                    fileSuffix = "." + body.contentType().subtype();
                }
                name = name + fileSuffix;
            }
        } else {
            name = System.currentTimeMillis() + fileSuffix;
        }

        if (path == null) {
            path = context.getExternalFilesDir(null) + File.separator + name;
        } else {
            File file = new File(path);
            if (file != null && !file.exists()) {
                file.mkdirs();
            }
            path = path + File.separator + name;
            path = path.replaceAll("//", "/");
        }

        HttpLog.i("path:-->" + path);
        try {
            File futureStudioIconFile = new File(path);
           /* if (!futureStudioIconFile.exists()) {
                futureStudioIconFile.createNewFile();
            }*/
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];

                final long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                HttpLog.d("file length: " + fileSize);
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    HttpLog.i("file download: " + fileSizeDownloaded + " of " + fileSize);
                    //下载进度
                    float progress = fileSizeDownloaded * 1.0f / fileSize;
                    long curTime = System.currentTimeMillis();
                    //每200毫秒刷新一次数据,防止频繁更新进度
                    if (curTime - lastRefreshUiTime >= 200 || progress == 1.0f) {
                        if (callBack != null) {
                            if (callBack != null) {
                                final long finalFileSizeDownloaded = fileSizeDownloaded;
                                Observable.just(finalFileSizeDownloaded).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
                                    @Override
                                    public void call(Long aLong) {
                                        if (callBack instanceof DownloadProgressCallBack) {
                                            ((DownloadProgressCallBack) callBack).update(finalFileSizeDownloaded, fileSize, finalFileSizeDownloaded == fileSize);
                                        }
                                    }
                                });
                            }
                        }
                        lastRefreshUiTime = System.currentTimeMillis();
                    }
                }

                outputStream.flush();
                HttpLog.i("file downloaded: " + fileSizeDownloaded + " of " + fileSize);

                if (callBack != null) {
                    final String finalName = name;
                    final String finalPath = path;
                    Observable.just(finalPath).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
                        @Override
                        public void call(String aLong) {
                            if (callBack instanceof DownloadProgressCallBack) {
                                ((DownloadProgressCallBack) callBack).onComplete(finalPath);
                            }
                        }
                    });
                    HttpLog.i("file downloaded: " + fileSizeDownloaded + " of " + fileSize);
                    HttpLog.i("file downloaded: is sucess");
                }

                return true;
            } catch (IOException e) {
                finalonError(e);
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            finalonError(e);
            return false;
        }
    }

    private void finalonError(final Exception e) {

        if (callBack == null) {
            return;
        }
        //if (Utils.checkMain()) {
        Observable.just(new ApiException(e, 100)).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ApiException>() {
            @Override
            public void call(ApiException aLong) {
                callBack.onError(aLong);
            }
        });
    }
}

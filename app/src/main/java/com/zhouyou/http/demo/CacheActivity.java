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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.SerializableDiskConverter;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.cache.model.CacheResult;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.demo.model.SkinTestResult;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;
import com.zhouyou.http.utils.HttpLog;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * <p>描述：缓存使用介绍</p>
 * 作者： zhouyou<br>
 * 日期： 2017/7/6 16:25 <br>
 * 版本： v1.0<br>
 */
public class CacheActivity extends AppCompatActivity implements View.OnClickListener {
    TextView cache_content_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        cache_content_txt = (TextView) findViewById(R.id.cache_content);
        findViewById(R.id.default_cache).setOnClickListener(this);
        findViewById(R.id.first_remote).setOnClickListener(this);
        findViewById(R.id.first_cache).setOnClickListener(this);
        findViewById(R.id.only_remote).setOnClickListener(this);
        findViewById(R.id.only_cache).setOnClickListener(this);
        findViewById(R.id.cache_remote).setOnClickListener(this);
        findViewById(R.id.cache_remote_distinct).setOnClickListener(this);
    }

    CacheMode cacheMode;

    @Override
    public void onClick(View v) {
        cacheMode = CacheMode.FIRSTREMOTE;
        switch (v.getId()) {
            case R.id.default_cache: /** 默认缓存，走的是okhttp cache*/
                cacheMode = CacheMode.DEFAULT;
                break;
            case R.id.first_remote: /** 先请求网络，请求网络失败后再加载缓存 （自定义缓存Rxcache）*/
                cacheMode = CacheMode.FIRSTREMOTE;
                break;
            case R.id.first_cache:/** 先加载缓存，缓存没有再去请求网络 （自定义缓存Rxcache）*/
                cacheMode = CacheMode.FIRSTCACHE;
                break;
            case R.id.only_remote: /** 仅加载网络，但数据依然会被缓存 （自定义缓存Rxcache）*/
                cacheMode = CacheMode.ONLYREMOTE;
                break;
            case R.id.only_cache: /** 只读取缓存 （自定义缓存Rxcache）*/
                cacheMode = CacheMode.ONLYCACHE;
                break;
            case R.id.cache_remote:/** 先使用缓存，不管是否存在，仍然请求网络，会回调两次（自定义缓存Rxcache）*/
                cacheMode = CacheMode.CACHEANDREMOTE;
                break;
            case R.id.cache_remote_distinct:
                /** 先使用缓存，不管是否存在，仍然请求网络，
                 有缓存先显示缓存，等网络请求数据回来后发现和缓存是一样的就不会再返回，否则数据不一样会继续返回。
                 （目的是为了防止数据是一致的也会刷新两次界面）（自定义缓存Rxcache）*/
                cacheMode = CacheMode.CACHEANDREMOTEDISTINCT;
                break;
        }
        requestCahce();
    }

    /**
     * 回调支持：<br>
     * new SimpleCallBack<CacheResult<SkinTestResult>>()<br>
     * new SimpleCallBack<CacheResult<String>>()<br>
     * new SimpleCallBack<SkinTestResult>()//不返回CacheResult 直接返回内容<br>
     * new SimpleCallBack<String>()//返回字符串<br>
     */
    private void requestCahce() {
        EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .readTimeOut(30 * 1000)//测试局部读超时30s
                .cacheMode(cacheMode)
                .cacheKey(this.getClass().getSimpleName())//缓存key
                .retryCount(5)//重试次数
                .cacheTime(5 * 60)//缓存时间300s，默认-1永久缓存  okhttp和自定义缓存都起作用
                //.okCache(new Cache());//okhttp缓存，模式为默认模式（CacheMode.DEFAULT）才生效
                //.cacheDiskConverter(new GsonDiskConverter())//默认使用的是 new SerializableDiskConverter();
                .cacheDiskConverter(new SerializableDiskConverter())//默认使用的是 new SerializableDiskConverter();
                .timeStamp(true)
                .execute(new SimpleCallBack<CacheResult<SkinTestResult>>() {

                    @Override
                    public void onError(ApiException e) {
                        showToast("请求失败：" + e.getMessage());
                    }

                    @Override
                    public void onSuccess(CacheResult<SkinTestResult> cacheResult) {
                        HttpLog.i(cacheResult.toString());
                        String from;
                        if (cacheResult.isFromCache) {
                            from = "我来自缓存";
                        } else {
                            from = "我来自远程网络";
                        }
                        if (cacheResult.data != null) {
                            cache_content_txt.setText(Html.fromHtml(from + "\n" + cacheResult.data.toString()));
                        } else {
                            cache_content_txt.setText(Html.fromHtml(from + "\n" + cacheResult.data));
                        }
                        showToast(cache_content_txt.getText().toString());
                    }
                });
    }

    /**
     * 可以使用但是目前不能标记缓存来源<br>
     * 支持：<br>
     * execute(SkinTestResult.class)<br>
     * execute(String.class)<br>
     * 不支持：<br>
     * execute(CacheResult<SkinTestResult> clazz)<br>
     */
    private void requestCahce2() {
        EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .readTimeOut(30 * 1000)//测试局部读超时30s
                .cacheMode(cacheMode)
                .cacheKey(this.getClass().getSimpleName())//缓存key
                .retryCount(5)//重试次数
                .cacheTime(5 * 60)//缓存时间300s，默认-1永久缓存
                //.cacheDiskConverter(new GsonDiskConverter())//默认使用的是 new SerializableDiskConverter();
                .cacheDiskConverter(new SerializableDiskConverter())//默认使用的是 new SerializableDiskConverter();
                .timeStamp(true)
                .execute(SkinTestResult.class)
                .subscribe(new Observer<SkinTestResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull SkinTestResult skinTestResult) {
                        showToast(skinTestResult.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 移除缓存
     */
    public void onRemoveCache(View view) {
        EasyHttp.removeCache(this.getClass().getSimpleName());
    }

    /**
     * 清空缓存
     */
    public void onClearCache(View view) {
        EasyHttp.clearCache();
    }

    /**
     * 根据key获取缓存
     */
    public void onLoadCache(View view) {
        Observable<SkinTestResult> observable = EasyHttp.getRxCacheBuilder()
                //获取缓存需要指定下转换器，默认就是SerializableDiskConverter 这里可以不用写
                //就是你网络请求用哪个转换器存储的缓存，那么读取时也要采用对应的转换器读取
                .diskConverter(new SerializableDiskConverter()).build()
                 //这个表示读取缓存根据时间,读取指定时间内的缓存，例如读取:5*60s之内的缓存
                //.load(new TypeToken<SkinTestResult>() {}.getType(), this.getClass().getSimpleName(), 5 * 60)
                 //这个表示读取缓存不根据时间只要有缓存就读取
                .load(new TypeToken<SkinTestResult>() {}.getType(), this.getClass().getSimpleName());
        observable.subscribe(new BaseSubscriber<SkinTestResult>() {
            @Override
            public void onError(ApiException e) {
                showToast("获取缓存失败:" + e.getMessage());
            }

            @Override
            public void onNext(SkinTestResult s) {
                showToast("获取缓存成功:" + s.toString());
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

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

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.demo.Api.LoginService;
import com.zhouyou.http.demo.constant.AppConstant;
import com.zhouyou.http.demo.constant.ComParamContact;
import com.zhouyou.http.demo.customapi.CustomApiResult;
import com.zhouyou.http.demo.customapi.ResultBean;
import com.zhouyou.http.demo.model.AuthModel;
import com.zhouyou.http.demo.model.SectionItem;
import com.zhouyou.http.demo.model.SkinTestResult;
import com.zhouyou.http.demo.utils.FileUtils;
import com.zhouyou.http.demo.utils.MD5;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.ApiResult;
import com.zhouyou.http.request.CustomRequest;
import com.zhouyou.http.subsciber.BaseSubscriber;
import com.zhouyou.http.subsciber.IProgressDialog;
import com.zhouyou.http.subsciber.ProgressSubscriber;

import java.util.List;

import retrofit2.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                FileUtils.getFileFromAsset(MainActivity.this, "1.jpg");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
    }


    public void onGet(View view) {
        EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .readTimeOut(30 * 1000)//局部定义读超时
                .writeTimeOut(30 * 1000)
                .connectTimeout(30 * 1000)
                //.headers("","")//设置头参数
                //.params("name","张三")//设置参数
                //.addInterceptor()
                //.addConverterFactory()
                //.addCookie()
                .timeStamp(true)
                .execute(new SimpleCallBack<SkinTestResult>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {
                        if (response != null) showToast(response.toString());
                    }
                });
               /* .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        if (response != null) showToast(response.toString());
                    }
                });*/
    }

    /**
     * post请求
     */
    public void onPost(View view) {
        EasyHttp.post("v1/app/chairdressing/news/favorite")
                .params("newsId", "552")
                .accessToken(true)
                .timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        showToast(response);
                    }
                });
    }

    /**
     * 基础回调
     */
    public void onCallBack(View view) {
        //支持CallBack<SkinTestResult>、CallBack<String>回调
        EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .timeStamp(true)
                .execute(new CallBack<SkinTestResult>() {
                    @Override
                    public void onStart() {
                        showToast("开始请求");
                    }

                    @Override
                    public void onCompleted() {
                        showToast("请求完成");
                    }

                    @Override
                    public void onError(ApiException e) {
                        showToast("请求失败：" + e.getMessage());
                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {
                        showToast("请求成功：" + response.toString());
                    }
                });
    }

    /**
     * 简单回调
     *
     * @param view
     */
    public void onSimpleCallBack(View view) {
        //支持SimpleCallBack<SkinTestResult>、SimpleCallBack<String>回调
        EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .timeStamp(true)
                .execute(new SimpleCallBack<SkinTestResult>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {
                        showToast(response.toString());
                    }
                });
    }

    private IProgressDialog mProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            ProgressDialog dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("请稍候...");
            return dialog;
        }
    };

    /**
     * 带有加载进度框的回调，支持是否可以取消对话框，取消对话框时可以自动取消网络请求，不需要再手动取消。
     */
    public void onProgressDialogCallBack(View view) {
        EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .timeStamp(true)
                .execute(new ProgressDialogCallBack<SkinTestResult>(mProgressDialog, true, true) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);//super.onError(e)必须写不能删掉或者忘记了
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {
                        showToast(response.toString());
                    }
                });
    }

    /**
     * 请求网络接口最终获取Subscription对象，通过该对象手动取消网络请求
     * 在需要取消网络请求的地方调用,一般在onDestroy()中
     */
    public void onSubscription(View view) {
        Subscription subscription = EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .timeStamp(true)
                .execute(new SimpleCallBack<SkinTestResult>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {
                        showToast(response.toString());
                    }
                });

        //在需要取消网络请求的地方调用,一般在onDestroy()中
        //EasyHttp.cancelSubscription(subscription);
    }

    /**
     * 返回Observable对象，Observable是Rxjava，有了Observable可以与其它业务逻辑很好的结合
     * 本例不讲怎么结合简单订阅下输出结果，结合需要看具体场景
     */
    public void onObservable(View view) {
        Observable<SkinTestResult> observable = EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .timeStamp(true)
                .execute(SkinTestResult.class);

        observable.subscribe(new BaseSubscriber<SkinTestResult>() {
            @Override
            public void onError(ApiException e) {
                showToast(e.getMessage());
            }

            @Override
            public void onNext(SkinTestResult skinTestResult) {
                showToast(skinTestResult.toString());
            }
        });
    }

    /**
     * 带有进度框的订阅者，对话框消失，可以自动取消掉网络请求
     */
    public void onProgressSubscriber(View view) {
        Observable<SkinTestResult> observable = EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .timeStamp(true)
                .execute(SkinTestResult.class);

        observable.subscribe(new ProgressSubscriber<SkinTestResult>(this, mProgressDialog) {
            @Override
            public void onError(ApiException e) {
                super.onError(e);
                showToast(e.getMessage());
            }

            @Override
            public void onNext(SkinTestResult skinTestResult) {
                showToast(skinTestResult.toString());
            }
        });
    }

    /**
     * 上传文件
     */
    public void onUploadFile(View view) {
        Intent intent = new Intent(this, UploadActivity.class);
        startActivity(intent);
    }

    /**
     * 上传文件
     */
    public void onDownloadFile(View view) {
        Intent intent = new Intent(this, DownloadActivity.class);
        startActivity(intent);
    }

    /**
     * 网络缓存
     */
    public void onCache(View view) {
        Intent intent = new Intent(this, CacheActivity.class);
        startActivity(intent);
    }

    /**
     * 使用EasyHttp调用自定义api  注意：如果有签名的注意路径有"/"的情况如下
     * https://www.xxx.com/v1/account/login （正确）
     * https://www.xxx.com//v1/account/login (错误 可能会导致签名失败)
     */
    public void onCustomCall(View view) {
        final String name = "18688994275";
        final String pass = "123456";
        final CustomRequest request = EasyHttp.custom().addConverterFactory(GsonConverterFactory.create(new Gson()))
                .sign(true)
                .timeStamp(true)
                .params(ComParamContact.Login.ACCOUNT, name)
                .params(ComParamContact.Login.PASSWORD, MD5.encrypt4login(pass, AppConstant.APP_SECRET))
                .build();

        LoginService mLoginService = request.create(LoginService.class);
        Observable<ApiResult<AuthModel>> observable = request.call(mLoginService.login("v1/account/login", request.getParams().urlParamsMap));
        Subscription subscription = observable.subscribe(new Action1<ApiResult<AuthModel>>() {
            @Override
            public void call(ApiResult<AuthModel> result) {
                showToast(result.toString());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                showToast(throwable.getMessage());
            }
        });
        //EasyHttp.cancelSubscription(subscription);取消订阅
    }

    public void onCustomApiCall(View view) {
        final String name = "18688994275";
        final String pass = "123456";
        final CustomRequest request = EasyHttp.custom()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .sign(true)
                .timeStamp(true)
                .params(ComParamContact.Login.ACCOUNT, name)
                .params(ComParamContact.Login.PASSWORD, MD5.encrypt4login(pass, AppConstant.APP_SECRET))
                .build();

        LoginService mLoginService = request.create(LoginService.class);
        Observable<AuthModel> observable = request.apiCall(mLoginService.login("v1/account/login", request.getParams().urlParamsMap));
        Subscription subscription = observable.subscribe(new Action1<AuthModel>() {
            @Override
            public void call(AuthModel result) {
                showToast(result.toString());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                showToast(throwable.getMessage());
            }
        });
        //EasyHttp.cancelSubscription(subscription);取消订阅
    }

    public void onCustomApiResult(View view) {
        EasyHttp.get("/mobile/get")
                .readTimeOut(30 * 1000)//局部定义读超时
                .writeTimeOut(30 * 1000)
                .connectTimeout(30 * 1000)
                //.cacheKey(this.getClass().getSimpleName()+"11")
                //.cacheMode(CacheMode.CACHEANDREMOTE)
                //.cacheMode(CacheMode.ONLYREMOTE)
                //.headers("","")//设置头参数
                //.params("name","张三")//设置参数
                //.addInterceptor()
                //.addConverterFactory()
                //.addCookie()
                //.timeStamp(true)
                .baseUrl("http://apis.juhe.cn")
                .params("phone", "18688994275")
                .params("dtype", "json")
                .params("key", "5682c1f44a7f486e40f9720d6c97ffe4")
                .execute(new CallBackProxy<CustomApiResult<ResultBean>, ResultBean>(new SimpleCallBack<ResultBean>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(ResultBean response) {
                        if (response != null) showToast(response.toString());
                    }
                }) {
                });

    }

    public void onListResult(View view) {
        //方式一：
        /*EasyHttp.get("http://news-at.zhihu.com/api/3/sections")
                .execute(new SimpleCallBack<List<SectionItem>>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(List<SectionItem> response) {
                        if (response != null) showToast(response.toString());
                    }
                });*/
        //方式二：
        /*Observable<List<SectionItem>> observable = EasyHttp.get("http://news-at.zhihu.com/api/3/sections")
                .execute(new TypeToken<List<SectionItem>>() {
                }.getType());
        observable.subscribe(new Action1<List<SectionItem>>() {
            @Override
            public void call(List<SectionItem> sectionItems) {
                showToast(sectionItems.toString());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                showToast(throwable.getMessage());
            }
        });*/
        //方式三：
        Observable<List<SectionItem>> observable = EasyHttp.get("http://news-at.zhihu.com/api/3/sections")
                .execute(new TypeToken<List<SectionItem>>() {
                }.getType());
        observable.subscribe(new ProgressSubscriber<List<SectionItem>>(MainActivity.this, mProgressDialog) {
            @Override
            public void onError(ApiException e) {
                super.onError(e);
                showToast(e.getMessage());
            }

            @Override
            public void onNext(List<SectionItem> sectionItems) {
                showToast(sectionItems.toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}

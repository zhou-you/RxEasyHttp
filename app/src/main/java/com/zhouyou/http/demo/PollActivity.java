package com.zhouyou.http.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallClazzProxy;
import com.zhouyou.http.demo.customapi.test6.Content;
import com.zhouyou.http.demo.customapi.test6.TestApiResult6;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * <p>描述：轮询请求</p>
 * 作者： zhouyou<br>
 * 日期： 2017/11/1 20:32 <br>
 * 版本： v1.0<br>
 */
public class PollActivity extends AppCompatActivity {
    Disposable polldisposable, countdisposable, ifdisposable, filterdisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
    }

    //无限轮询
    public void onPollRequest(View view) {
        //http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hello%20world
        //第一个参数表示初始化延时多久开始请求，这里用0表示不延时直接请求
        //第二个参数表示间隔多久轮询一次，这里表示间隔5s
        //interval(0,5, TimeUnit.SECONDS)
        //interval(5, TimeUnit.SECONDS)两个参数的这个5就表示，初始延时5秒开始执行请求，轮询也是5s
        //自己根据需要选择合适的interval方法
        polldisposable = Observable.interval(0, 5, TimeUnit.SECONDS).flatMap(new Function<Long, ObservableSource<Content>>() {
            @Override
            public ObservableSource<Content> apply(@NonNull Long aLong) throws Exception {
                return EasyHttp.get("/ajax.php")
                        .baseUrl("http://fy.iciba.com")
                        .params("a", "fy")
                        .params("f", "auto")
                        .params("t", "auto")
                        .params("w", "hello world")
                        //采用代理
                        .execute(new CallClazzProxy<TestApiResult6<Content>, Content>(Content.class) {
                        });
            }
        }).subscribeWith(new BaseSubscriber<Content>() {
            @Override
            public void onError(ApiException e) {
                showToast(e.getMessage());
            }

            @Override
            public void onNext(@NonNull Content content) {
                showToast(content.toString());
            }
        });

    }

    //限制轮询次数
    public void onPollRequestCount(View view) {
        //http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hello%20world
        int count = 3;//轮询3次
        //方式一：采用intervalRange
        //Observable.intervalRange(0,count,0,5, TimeUnit.SECONDS).flatMap(new Function<Long, ObservableSource<Content>>() {
        //方式一：采用take
        countdisposable = Observable.interval(0, 5, TimeUnit.SECONDS).take(count).flatMap(new Function<Long, ObservableSource<Content>>() {
            @Override
            public ObservableSource<Content> apply(@NonNull Long aLong) throws Exception {
                return EasyHttp.get("/ajax.php")
                        .baseUrl("http://fy.iciba.com")
                        .params("a", "fy")
                        .params("f", "auto")
                        .params("t", "auto")
                        .params("w", "hello world")
                        //采用代理
                        .execute(new CallClazzProxy<TestApiResult6<Content>, Content>(Content.class) {
                        });
            }
        }).subscribeWith(new BaseSubscriber<Content>() {

            @Override
            public void onError(ApiException e) {
                showToast(e.getMessage());
            }

            @Override
            public void onNext(@NonNull Content content) {
                showToast(content.toString());
            }
        });

    }

    //指定轮询结束条件->一旦满足条件就停止发送，这里用于设定轮询结束的条件
    public void onPollif(View view) {
        //http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hello%20world
        //第一个参数表示初始化延时多久开始请求，这里用0表示不延时直接请求
        //第二个参数表示间隔多久轮询一次，这里表示间隔5s
        //interval(0,5, TimeUnit.SECONDS)
        //interval(5, TimeUnit.SECONDS)两个参数表示那个这个5就表示，初始延时5秒开始执行请求
        //自己根据需要选择合适的interval方法
        ifdisposable = Observable.interval(0, 5, TimeUnit.SECONDS).flatMap(new Function<Long, ObservableSource<Content>>() {
            @Override
            public ObservableSource<Content> apply(@NonNull Long aLong) throws Exception {
                return EasyHttp.get("/ajax.php")
                        .baseUrl("http://fy.iciba.com")
                        .params("a", "fy")
                        .params("f", "auto")
                        .params("t", "auto")
                        .params("w", "hello world")
                        //采用代理
                        .execute(new CallClazzProxy<TestApiResult6<Content>, Content>(Content.class) {
                        });
            }
        }).takeUntil(new Predicate<Content>() {
            @Override
            public boolean test(@NonNull Content content) throws Exception {
                //如果条件满足，就会终止轮询，这里逻辑可以自己写
                //结果为true，说明满足条件了，就不在轮询了
                return content.getOut().contains("示");
            }
        }).subscribeWith(new BaseSubscriber<Content>() {
            @Override
            public void onError(ApiException e) {
                showToast(e.getMessage());
            }

            @Override
            public void onNext(@NonNull Content content) {
                showToast(content.toString());
            }
        });

    }

    //过滤轮询->就是在轮询的过程中，如果发现不满足条件的数据就不返回给订阅者刷新界面等操作
    //此种操作可以配合任意类型的轮询
    public void onPollFilter(View view) {
        filterdisposable = Observable.interval(0, 5, TimeUnit.SECONDS).flatMap(new Function<Long, ObservableSource<Content>>() {
            @Override
            public ObservableSource<Content> apply(@NonNull Long aLong) throws Exception {
                return EasyHttp.get("/ajax.php")
                        .baseUrl("http://fy.iciba.com")
                        .params("a", "fy")
                        .params("f", "auto")
                        .params("t", "auto")
                        .params("w", "hello world")
                        //采用代理
                        .execute(new CallClazzProxy<TestApiResult6<Content>, Content>(Content.class) {
                        });
            }
        }).filter(new Predicate<Content>() {
            @Override
            public boolean test(@NonNull Content content) throws Exception {
                //如果不满足条件就过滤该条轮询数据，但是轮询还是一直执行
                //ErrNo==0表示成功，如果不等于0就认为失败，content不会返回给订阅者
                return content.getErrNo() != 0;
            }
        }).subscribeWith(new BaseSubscriber<Content>() {
            @Override
            public void onError(ApiException e) {
                showToast(e.getMessage());
            }

            @Override
            public void onNext(@NonNull Content content) {
                showToast(content.toString());
            }
        });

    }

    @Override
    protected void onDestroy() {
        EasyHttp.cancelSubscription(polldisposable);
        EasyHttp.cancelSubscription(countdisposable);
        EasyHttp.cancelSubscription(ifdisposable);
        EasyHttp.cancelSubscription(filterdisposable);
        super.onDestroy();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}

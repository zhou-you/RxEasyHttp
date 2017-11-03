package com.zhouyou.http.demo;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallClazzProxy;
import com.zhouyou.http.demo.customapi.test6.Content2;
import com.zhouyou.http.demo.customapi.test6.TestApiResult6;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * <p>描述：减少频繁的网络请求,用搜索举列</p>
 * 作者： zhouyou<br>
 * 日期： 2017/11/2 15:38 <br>
 * 版本： v1.0<br>
 */
public class SearchActivity extends AppCompatActivity {
    EditText mEditText;
    TextView mTextView;
    Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mEditText = findView(R.id.edit_text);
        mTextView = findView(R.id.content_text);
        search();
    }

    protected <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }

    private void search() {
        //500毫秒才让它去请求一次网络，这样可以初步的避免数据混乱，也优了app性能
        //可以使用debounce减少频繁的网络请求。避免每输入（删除）一个字就做一次请求
        mDisposable = RxTextView.textChangeEvents(mEditText)
                .debounce(500, TimeUnit.MILLISECONDS).filter(new Predicate<TextViewTextChangeEvent>() {
                    @Override
                    public boolean test(@NonNull TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
                        // 过滤，把输入字符串长度为0时过滤掉
                        String key = textViewTextChangeEvent.text().toString();
                        //这里可以对key进行过滤的判断逻辑
                        return key.trim().length() > 0;
                    }
                }).flatMap(new Function<TextViewTextChangeEvent, ObservableSource<Content2>>() {
                    @Override
                    public ObservableSource<Content2> apply(@NonNull TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
                        String key = textViewTextChangeEvent.text().toString();
                        Log.d("test", String.format("Searching for: %s", textViewTextChangeEvent.text().toString()));
                        return EasyHttp.get("/ajax.php")
                                .baseUrl("http://fy.iciba.com")
                                .params("a", "fy")
                                .params("f", "auto")
                                .params("t", "auto")
                                .params("w", key)
                                //采用代理
                                .execute(new CallClazzProxy<TestApiResult6<Content2>, Content2>(Content2.class) {
                                });
                    }
                }).subscribeWith(new BaseSubscriber<Content2>() {
                    @Override
                    protected void onStart() {
                    }

                    @Override
                    public void onError(ApiException e) {
                        mTextView.setText(e.getMessage());
                    }

                    @Override
                    public void onNext(@NonNull Content2 content) {
                        mTextView.setText(content.toString());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        EasyHttp.cancelSubscription(mDisposable);
        super.onDestroy();
    }
}

package com.zhouyou.http.demo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.view.RxView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallClazzProxy;
import com.zhouyou.http.demo.constant.AppConstant;
import com.zhouyou.http.demo.constant.ComParamContact;
import com.zhouyou.http.demo.customapi.test1.ResultBean;
import com.zhouyou.http.demo.customapi.test1.TestApiResult1;
import com.zhouyou.http.demo.customapi.test5.TestApiResult5;
import com.zhouyou.http.demo.customapi.test6.Content;
import com.zhouyou.http.demo.customapi.test6.TestApiResult6;
import com.zhouyou.http.demo.model.AuthModel;
import com.zhouyou.http.demo.model.SectionItem;
import com.zhouyou.http.demo.model.SkinTestResult;
import com.zhouyou.http.demo.utils.MD5;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;
import com.zhouyou.http.subsciber.IProgressDialog;
import com.zhouyou.http.subsciber.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;

/**
 * <p>描述：RxEasyHttp使用场景举列</p>
 * 作者： zhouyou<br>
 * 日期： 2017/10/31 17:45 <br>
 * 版本： v1.0<br>
 */
public class SceneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);
    }

    private IProgressDialog mProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            ProgressDialog dialog = new ProgressDialog(SceneActivity.this);
            dialog.setMessage("请稍候...");
            return dialog;
        }
    };

    //延迟请求
    public void onDeferredRequest(View view) {
        showToast("开启定时");
        //延迟5s请求
        Observable.timer(5, TimeUnit.SECONDS).flatMap(new Function<Long, ObservableSource<SkinTestResult>>() {
            @Override
            public ObservableSource<SkinTestResult> apply(@NonNull Long aLong) throws Exception {
                Log.i("test", "=====" + aLong);
                return EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                        .timeStamp(true)
                        .execute(SkinTestResult.class);
            }
        }).subscribe(new BaseSubscriber<SkinTestResult>() {
            @Override
            protected void onStart() {
                showToast("定时结束->开始请求");
            }

            @Override
            public void onError(ApiException e) {
                showToast(e.getMessage());
            }

            @Override
            public void onNext(@NonNull SkinTestResult skinTestResult) {
                showToast(skinTestResult.toString());
            }
        });
    }

    //轮询示例
    public void onPoll(View view) {
        Intent intent = new Intent(SceneActivity.this, PollActivity.class);
        startActivity(intent);
    }

    //嵌套请求->一个接口的请求依赖另一个API请求返回的数据
    public void onNestedRequest(View view) {
        //例如：先登录获取token后，再去请求另一个接口
        Observable<AuthModel> login = EasyHttp.post(ComParamContact.Login.PATH)
                .params(ComParamContact.Login.ACCOUNT, "18688994275")
                .params(ComParamContact.Login.PASSWORD, MD5.encrypt4login("123456", AppConstant.APP_SECRET))
                .sign(true)
                .timeStamp(true).execute(AuthModel.class);
        login.flatMap(new Function<AuthModel, ObservableSource<SkinTestResult>>() {
            @Override
            public ObservableSource<SkinTestResult> apply(@NonNull AuthModel authModel) throws Exception {
                return EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                        .params("accessToken", authModel.getAccessToken())//这个地方只是举例，并不一定是需要accessToken
                        .timeStamp(true)
                        .execute(SkinTestResult.class);
            }
        }).subscribe(new ProgressSubscriber<SkinTestResult>(this, mProgressDialog) {
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

    //采用zip合并请求
    public void onZipRequest(View view) {
        //使用zip操作符合并等待多个网络请求完成后，再刷新界面
        //例如下面：数据来自3个不同的接口
        Observable<ResultBean> mobileObservable = EasyHttp.get("http://apis.juhe.cn/mobile/get")
                .params("phone", "18688994275")
                .params("dtype", "json")
                .params("key", "5682c1f44a7f486e40f9720d6c97ffe4")
                .execute(new CallClazzProxy<TestApiResult1<ResultBean>, ResultBean>(ResultBean.class) {
                });

        Observable<Content> searchObservable = EasyHttp.get("/ajax.php")
                .baseUrl("http://fy.iciba.com")
                .params("a", "fy")
                .params("f", "auto")
                .params("t", "auto")
                .params("w", "hello world")
                //采用代理
                .execute(new CallClazzProxy<TestApiResult6<Content>, Content>(Content.class) {
                });

        Observable<List<SectionItem>> listObservable = EasyHttp.get("http://news-at.zhihu.com/api/3/sections")
                .execute(new CallClazzProxy<TestApiResult5<List<SectionItem>>, List<SectionItem>>(new TypeToken<List<SectionItem>>() {
                }.getType()) {
                });
        //new Function3最后一个参数这里用的是List<Object>，表示将3个返回的结果，放在同一个集合最终一次性返回，你也可以指定返回其它你需要的数据类型并不一定是List<Object>
        //假如这三个接口返回的都是TestBean,那么就可以直接用具体的List<TestBean>,不需要用List<Object>
        Observable.zip(mobileObservable, searchObservable, listObservable, new Function3<ResultBean, Content, List<SectionItem>, List<Object>>() {
            @Override
            public List<Object> apply(@NonNull ResultBean resultbean, @NonNull Content content, @NonNull List<SectionItem> sectionItems) throws Exception {
                //将接收到的3个数据先暂存起来，一次性发给订阅者
                List list = new ArrayList();
                list.add(resultbean);
                list.add(content);
                list.add(sectionItems);
                return list;
            }
        }).subscribe(new BaseSubscriber<List<Object>>() {
            @Override
            public void onError(ApiException e) {
                showToast(e.getMessage());
            }

            @Override
            public void onNext(@NonNull List<Object> objects) {
                showToast(objects.toString());
            }
        });
    }

    //采用merge合并请求
    public void onMergeRequest(View view) {
        //一定要注意Merge和zip的区别，虽然都是合并多个请求，但是是有区别的，请注意使用场景
        //Merge其实只是将多个Obsevables的输出序列变为一个，方便订阅者统一处理，对于订阅者来说就仿佛只订阅了一个观察者一样。
        //拼接两个/多个Observable的输出，不保证顺序，按照事件产生的顺序发送给订阅者

        //这个请求故意延时5秒再发送-最后测试结果发现，并不是searchObservable等待mobileObservable5秒后再发送
        Observable<ResultBean> mobileObservable = Observable.timer(5, TimeUnit.SECONDS).flatMap(new Function<Long, ObservableSource<ResultBean>>() {
            @Override
            public ObservableSource<ResultBean> apply(@NonNull Long aLong) throws Exception {
                return EasyHttp.get("http://apis.juhe.cn/mobile/get")
                        .params("phone", "18688994275")
                        .params("dtype", "json")
                        .params("key", "5682c1f44a7f486e40f9720d6c97ffe4")
                        .execute(new CallClazzProxy<TestApiResult1<ResultBean>, ResultBean>(ResultBean.class) {
                        });
            }
        });

        Observable<Content> searchObservable = EasyHttp.get("/ajax.php")
                .baseUrl("http://fy.iciba.com")
                .params("a", "fy")
                .params("f", "auto")
                .params("t", "auto")
                .params("w", "hello world")
                //采用代理
                .execute(new CallClazzProxy<TestApiResult6<Content>, Content>(Content.class) {
                });
        //merge和mergeDelayError都是合并，但是需要注意二者区别。
        //merge：合并的请求，如果有一个接口报错了，就立马报错，会终止整个流，另外的接口也不会请求。
        //mergeDelayError：合并的请求，如果有一个接口报错了，会延迟错误处理，后面的接口会继续执行没有被中断。
        //使用时根据需要选用merge/mergeDelayError
        //Observable.merge(searchObservable,mobileObservable).subscribe(new BaseSubscriber<Object>() {
        Observable.mergeDelayError(searchObservable, mobileObservable).subscribe(new BaseSubscriber<Object>() {
            @Override
            public void onError(ApiException e) {
                showToast(e.getMessage());
            }

            @Override
            public void onNext(@NonNull Object object) {
                //为什么用Object接收，因为两个接口请求返回的数据类型不是一样的，如果是一样的就用具体的对象接收就可以了，
                // 不再需要instanceof麻烦的判断
                if (object instanceof ResultBean) {//mobileObservable 返回的结果
                    //处理  ResultBean逻辑
                } else if (object instanceof Content) {
                    //处理  Content逻辑
                }
                showToast(object.toString());
            }
        });

    }

    //与view结合防止按钮重复点击-点击防抖
    public void onRepeatRquest(View view) {
        //与rxbinding库结合-从而避免重复请求，1s中内不再重复请求
        //RxView.clicks(view).debounce(1,TimeUnit.SECONDS)
        RxView.clicks(view).throttleFirst(1, TimeUnit.SECONDS).flatMap(new Function<Object, ObservableSource<ResultBean>>() {
            @Override
            public ObservableSource<ResultBean> apply(@NonNull Object o) throws Exception {
                return EasyHttp.get("http://apis.juhe.cn/mobile/get")
                        .params("phone", "18688994275")
                        .params("dtype", "json")
                        .params("key", "5682c1f44a7f486e40f9720d6c97ffe4")
                        .execute(new CallClazzProxy<TestApiResult1<ResultBean>, ResultBean>(ResultBean.class) {
                        });
            }
        }).subscribe(new BaseSubscriber<ResultBean>() {
            @Override
            public void onError(ApiException e) {
                showToast(e.getMessage());
            }

            @Override
            public void onNext(@NonNull ResultBean resultBean) {
                showToast(resultBean.toString());
            }
        });
    }

    public void onCacheRequest(View view) {
        showToast("请看库中缓存部分源码！");
    }
    
    //减少频繁的网络请求
    public void onFrequentlyRquest(View view) {
        Intent intent = new Intent(SceneActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}

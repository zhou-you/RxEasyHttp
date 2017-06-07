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

package com.zhouyou.http.request;

import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.cache.model.CacheResult;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.func.ApiResultFunc;
import com.zhouyou.http.func.CacheResultFunc;
import com.zhouyou.http.func.RetryExceptionFunc;
import com.zhouyou.http.model.ApiResult;
import com.zhouyou.http.subsciber.CallBackSubsciber;
import com.zhouyou.http.utils.RxSchedulers;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscription;

/**
 * <p>描述：Put请求</p>
 * 作者： zhouyou<br>
 * 日期： 2017/5/22 16:30 <br>
 * 版本： v1.0<br>
 */
public class PutRequest extends BaseRequest<PutRequest> {
    public PutRequest(String url) {
        super(url);
    }

    public <T> Observable<T> execute(Class<T> clazz) {
        return build().apiManager.put(url, params.urlParamsMap)
                .map(new ApiResultFunc(clazz))
                .compose(isSyncRequest ? RxSchedulers._main() : RxSchedulers._io_main())
                .compose(rxCache.transformer(cacheMode, clazz))
                .retryWhen(new RetryExceptionFunc(retryCount, retryDelay, retryIncreaseDelay))
                .compose(new Observable.Transformer<CacheResult<T>, T>() {
                    @Override
                    public Observable<T> call(Observable<CacheResult<T>> cacheResultObservable) {
                        return cacheResultObservable.map(new CacheResultFunc<T>());
                    }
                });
    }

    public <T> Subscription execute(CallBack<T> callBack) {
        return execute(new CallBackProxy<ApiResult<T>, T>(callBack) {
        });
    }

    public <T> Subscription execute(CallBackProxy<? extends ApiResult<T>, T> proxy) {
        Observable<CacheResult<T>> observable = build().toObservable(apiManager.put(url, params.urlParamsMap), proxy);
        if (CacheResult.class != proxy.getCallBack().getRawType()) {
            return observable.compose(new Observable.Transformer<CacheResult<T>, T>() {
                @Override
                public Observable<T> call(Observable<CacheResult<T>> observable) {
                    return observable.map(new CacheResultFunc<T>());
                }
            }).subscribe(new CallBackSubsciber(context, proxy.getCallBack()));
        }
        return observable.subscribe(new CallBackSubsciber<CacheResult<T>>(context, proxy.getCallBack()));
    }

    private <T> Observable<CacheResult<T>> toObservable(Observable observable, CallBackProxy<? extends ApiResult<T>, T> proxy) {
        return observable.map(new ApiResultFunc(proxy != null ? proxy.getType() : new TypeToken<ResponseBody>() {
        }.getType()))
                .compose(isSyncRequest ? RxSchedulers._main() : RxSchedulers._io_main())
                .compose(rxCache.transformer(cacheMode, proxy.getCallBack().getType()))
                .retryWhen(new RetryExceptionFunc(retryCount, retryDelay, retryIncreaseDelay));
    }
}

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
import com.zhouyou.http.utils.RxUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * <p>描述：删除请求</p>
 * 作者： zhouyou<br>
 * 日期： 2017/4/28 15:02 <br>
 * 版本： v1.0<br>
 */
@SuppressWarnings(value = {"unchecked", "deprecation"})
public class DeleteRequest extends BaseBodyRequest<DeleteRequest> {
    public DeleteRequest(String url) {
        super(url);
    }

    public <T> Disposable execute(CallBack<T> callBack) {
        return execute(new CallBackProxy<ApiResult<T>, T>(callBack) {
        });
    }

    public <T> Disposable execute(CallBackProxy<? extends ApiResult<T>, T> proxy) {
        Observable<CacheResult<T>> observable = build().toObservable(generateRequest(), proxy);
        if (CacheResult.class != proxy.getCallBack().getRawType()) {
            return observable.compose(new ObservableTransformer<CacheResult<T>, T>() {
                @Override
                public ObservableSource<T> apply(@NonNull Observable<CacheResult<T>> upstream) {
                    return upstream.map(new CacheResultFunc<T>());
                }
            }).subscribeWith(new CallBackSubsciber<T>(context, proxy.getCallBack()));
        } else {
            return observable.subscribeWith(new CallBackSubsciber<CacheResult<T>>(context, proxy.getCallBack()));
        }
    }

    private <T> Observable<CacheResult<T>> toObservable(Observable observable, CallBackProxy<? extends ApiResult<T>, T> proxy) {
        return observable.map(new ApiResultFunc(proxy != null ? proxy.getType() : new TypeToken<ResponseBody>() {
        }.getType()))
                .compose(isSyncRequest ? RxUtil._main() : RxUtil._io_main())
                .compose(rxCache.transformer(cacheMode, proxy.getCallBack().getType()))
                .retryWhen(new RetryExceptionFunc(retryCount, retryDelay, retryIncreaseDelay));
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        if (this.requestBody != null) { //自定义的请求体
            return apiManager.deleteBody(url, this.requestBody);
        } else if (this.json != null) {//Json
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), this.json);
            return apiManager.deleteJson(url, body);
        }  else if (this.object != null) {//自定义的请求object
            return apiManager.deleteBody(url, object);
        } else if (this.string != null) {//文本内容
            RequestBody body = RequestBody.create(mediaType, this.string);
            return apiManager.deleteBody(url, body);
        } else {
            return apiManager.delete(url, params.urlParamsMap);
        }
    }
}

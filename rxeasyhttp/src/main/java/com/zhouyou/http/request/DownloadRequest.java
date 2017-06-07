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


import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.func.RetryExceptionFunc;
import com.zhouyou.http.subsciber.DownloadSubscriber;
import com.zhouyou.http.transformer.HandleErrTransformer;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>描述：下载请求</p>
 * 作者： zhouyou<br>
 * 日期： 2017/4/28 17:20 <br>
 * 版本： v1.0<br>
 */
public class DownloadRequest extends BaseRequest<DownloadRequest> {
    public DownloadRequest(String url) {
        super(url);
    }

    private String savePath;
    private String saveName;

    /**
     * 下载文件路径<br>
     * 默认在：/storage/emulated/0/Android/data/包名/files/1494647767055<br>
     */
    public DownloadRequest savePath(String savePath) {
        this.savePath = savePath;
        return this;
    }

    /**
     * 下载文件名称<br>
     * 默认名字是时间戳生成的<br>
     */
    public DownloadRequest saveName(String saveName) {
        this.saveName = saveName;
        return this;
    }

    public <T> Subscription execute(CallBack<T> callBack) {
        return build().apiManager.downloadFile(url).compose(new Observable.Transformer<ResponseBody, Object>() {
            @Override
            public Observable<Object> call(Observable<ResponseBody> responseBodyObservable) {
                if (isSyncRequest)
                    return ((Observable) responseBodyObservable)
                            .observeOn(AndroidSchedulers.mainThread());
                else
                    return ((Observable) responseBodyObservable).subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io());
            }
        }).compose(new HandleErrTransformer()).retryWhen(new RetryExceptionFunc(retryCount, retryDelay, retryIncreaseDelay))
                .subscribe(new DownloadSubscriber(savePath, saveName, callBack, context));
    }
}

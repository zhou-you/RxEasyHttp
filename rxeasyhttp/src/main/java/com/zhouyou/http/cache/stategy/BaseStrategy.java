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

package com.zhouyou.http.cache.stategy;

import com.zhouyou.http.cache.RxCache;
import com.zhouyou.http.cache.model.CacheResult;
import com.zhouyou.http.utils.HttpLog;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * <p>描述：实现缓存策略的基类</p>
 * 作者： zhouyou<br>
 * 日期： 2016/12/24 10:35<br>
 * 版本： v2.0<br>
 */
public abstract class BaseStrategy implements IStrategy {
    <T> Observable<CacheResult<T>> loadCache(final RxCache rxCache, Type type, final String key, final long time,final boolean needEmpty) {
        return rxCache
                .<T>load(type,key,time)
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(@NonNull Throwable throwable) throws Exception {
                        if (needEmpty) {
                            return Observable.empty();
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                })
                .map(new Function<T, CacheResult<T>>() {
                    @Override
                    public CacheResult<T> apply(@NonNull T t) throws Exception {
                        HttpLog.i("loadCache result=" + t);
                        return new CacheResult<T>(true, t);
                    }
                });
    }

     <T> Observable<CacheResult<T>> loadRemote(final RxCache rxCache, final String key, Observable<T> source,final boolean needEmpty) {
        return source
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(@NonNull Throwable throwable) throws Exception {
                        if (needEmpty) {
                            return Observable.empty();
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                })
                .map(new Function<T, CacheResult<T>>() {
                    @Override
                    public CacheResult<T> apply(@NonNull T t) throws Exception {
                        HttpLog.i("loadRemote result=" + t);
                        rxCache.save(key, t).subscribeOn(Schedulers.io())
                                .subscribe(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(@NonNull Boolean status) throws Exception {
                                        HttpLog.i("save status => " + status);
                                    }
                                });
                        return new CacheResult<T>(false, t);
                    }
                });
    }
}

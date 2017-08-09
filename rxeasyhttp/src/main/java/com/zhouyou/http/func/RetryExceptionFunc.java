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

package com.zhouyou.http.func;


import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.utils.HttpLog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * <p>描述：网络请求错误重试条件</p>
 * 作者： zhouyou<br>
 * 日期： 2017/4/12 17:52 <br>
 * 版本： v1.0<br>
 */
public class RetryExceptionFunc implements Function<Observable<? extends Throwable>, Observable<?>> {
    /* retry次数*/
    private int count = 0;
    /*延迟*/
    private long delay = 500;
    /*叠加延迟*/
    private long increaseDelay = 3000;

    public RetryExceptionFunc() {

    }

    public RetryExceptionFunc(int count, long delay) {
        this.count = count;
        this.delay = delay;
    }

    public RetryExceptionFunc(int count, long delay, long increaseDelay) {
        this.count = count;
        this.delay = delay;
        this.increaseDelay = increaseDelay;
    }

    @Override
    public Observable<?> apply(@NonNull Observable<? extends Throwable> observable) throws Exception {
        return observable.zipWith(Observable.range(1, count + 1), new BiFunction<Throwable, Integer, Wrapper>() {
            @Override
            public Wrapper apply(@NonNull Throwable throwable, @NonNull Integer integer) throws Exception {
                return new Wrapper(throwable, integer);
            }
        }).flatMap(new Function<Wrapper, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Wrapper wrapper) throws Exception {
                if (wrapper.index > 1)
                    HttpLog.i("重试次数：" + (wrapper.index));
                int errCode = 0;
                if (wrapper.throwable instanceof ApiException) {
                    ApiException exception = (ApiException) wrapper.throwable;
                    errCode = exception.getCode();
                }
                if ((wrapper.throwable instanceof ConnectException
                        || wrapper.throwable instanceof SocketTimeoutException
                        || errCode == ApiException.ERROR.NETWORD_ERROR
                        || errCode == ApiException.ERROR.TIMEOUT_ERROR
                        || wrapper.throwable instanceof SocketTimeoutException
                        || wrapper.throwable instanceof TimeoutException)
                        && wrapper.index < count + 1) { //如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                    return Observable.timer(delay + (wrapper.index - 1) * increaseDelay, TimeUnit.MILLISECONDS);

                }
                return Observable.error(wrapper.throwable);
            }
        });
    }

    private class Wrapper {
        private int index;
        private Throwable throwable;

        public Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }

}

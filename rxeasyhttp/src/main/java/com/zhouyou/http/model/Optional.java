package com.zhouyou.http.model;

import io.reactivex.Observable;

/**
 * <p>描述：为了使Rxjava2 onNext 返回null,使用了此包装类，进行过渡</p>
 * 作者： zhouyou<br>
 * 日期： 2018/7/6 14:33 <br>
 * 版本： v1.0<br>
 */
public class Optional<T> {
    Observable<T> obs;
    public Optional(Observable<T> obs) {
        this.obs = obs;
    }
    public static <T> Optional<T> of(T value) {
        if (value == null) {
            throw new NullPointerException();
        } else {
            return new Optional<T>(Observable.just(value));
        }
    }

    public static <T> Optional<T> ofNullable(T value) {
        if (value == null) {
            return new Optional<T>(Observable.<T>empty());
        } else {
            return new Optional<T>(Observable.just(value));
        }
    }

    public T get() {
        return obs.blockingSingle();
    }

    public T orElse(T defaultValue) {
        return obs.defaultIfEmpty(defaultValue).blockingSingle();
    }
}

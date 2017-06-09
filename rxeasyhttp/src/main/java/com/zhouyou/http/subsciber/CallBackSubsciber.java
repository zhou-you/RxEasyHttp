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

package com.zhouyou.http.subsciber;

import android.content.Context;

import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.utils.Utils;


/**
 * <p>描述：带有callBack的回调</p>
 * 主要作用是不需要用户订阅，只要实现callback回调<br>
 * 作者： zhouyou<br>
 * 日期： 2016/12/28 17:10<br>
 * 版本： v2.0<br>
 */

public class CallBackSubsciber<T> extends BaseSubscriber<T> {
    private CallBack<T> callBack;

    public CallBackSubsciber(Context context, CallBack<T> callBack) {
        super(context);
        this.callBack = callBack;
        if (callBack instanceof ProgressDialogCallBack) {
            ((ProgressDialogCallBack) callBack).subscription(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (callBack != null) {
            callBack.onStart();
        }
    }

    @Override
    public void onError(ApiException e) {
        if (callBack != null) {
            callBack.onError(e);
        }
    }

    @Override
    public void onNext(T t) {
        Utils.checkNotNull(t, "CallBackSubsciber onNext t==null");
        if (callBack != null) {
            callBack.onSuccess(t);
        }
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
        if (callBack != null) {
            callBack.onCompleted();
        }
    }
}

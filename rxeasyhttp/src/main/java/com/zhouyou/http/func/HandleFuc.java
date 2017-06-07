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
import com.zhouyou.http.exception.ServerException;
import com.zhouyou.http.model.ApiResult;

import rx.functions.Func1;

/**
 * <p>描述：ApiResult<T>转换T</p>
 * 作者： zhouyou<br>
 * 日期： 2017/5/15 16:54 <br>
 * 版本： v1.0<br>
 */
public class HandleFuc<T> implements Func1<ApiResult<T>, T> {
    @Override
    public T call(ApiResult<T> response) {
        if (ApiException.isOk(response)) {
            return response.getData();
        } else {
            throw new ServerException(response.getCode(), response.getMsg());
        }
    }
}

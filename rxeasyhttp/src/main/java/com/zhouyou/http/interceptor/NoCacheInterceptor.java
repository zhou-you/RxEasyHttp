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

package com.zhouyou.http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * <p>描述：不加载缓存</p>
 * 1.不适用Okhttp自带的缓存<br>
 * 作者： zhouyou<br>
 * 日期： 2016/12/20 10:35<br>
 * 版本： v2.0<br>
 */
public class NoCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder().header("Cache-Control", "no-cache").build();
        Response originalResponse = chain.proceed(request);
        originalResponse = originalResponse.newBuilder().header("Cache-Control", "no-cache").build();
        return originalResponse;
    }
}




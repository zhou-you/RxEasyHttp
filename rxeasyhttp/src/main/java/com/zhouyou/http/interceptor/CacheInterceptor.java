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

import android.content.Context;
import android.text.TextUtils;

import com.zhouyou.http.utils.HttpLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * <p>描述：设置缓存功能</p>
 * 作者： zhouyou<br>
 * 日期： 2016/12/19 16:35<br>
 * 版本： v2.0<br>
 */
public class CacheInterceptor implements Interceptor {

    protected Context context;
    protected String cacheControlValue_Offline;
    protected String cacheControlValue_Online;
    //set cahe times is 3 days
    protected static final int maxStale = 60 * 60 * 24 * 3;
    // read from cache for 60 s
    protected static final int maxStaleOnline = 60;

    public CacheInterceptor(Context context) {
        this(context, String.format("max-age=%d", maxStaleOnline));
    }

    public CacheInterceptor(Context context, String cacheControlValue) {
        this(context, cacheControlValue, String.format("max-age=%d", maxStale));
    }

    public CacheInterceptor(Context context, String cacheControlValueOffline, String cacheControlValueOnline) {
        this.context = context;
        this.cacheControlValue_Offline = cacheControlValueOffline;
        this.cacheControlValue_Online = cacheControlValueOnline;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //Request request = chain.request();
        okhttp3.Response originalResponse = chain.proceed(chain.request());
        String cacheControl = originalResponse.header("Cache-Control");
        //String cacheControl = request.cacheControl().toString();
       HttpLog.e( maxStaleOnline + "s load cache:" + cacheControl);
        if (TextUtils.isEmpty(cacheControl) || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age") || cacheControl.contains("max-stale")) {
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxStale)
                    .build();

        } else {
            return originalResponse;
        }
    }
}

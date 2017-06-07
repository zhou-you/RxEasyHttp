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

import com.zhouyou.http.utils.HttpLog;
import com.zhouyou.http.utils.Utils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>描述：支持离线缓存,使用OKhttp自带的缓存功能</p>
 * 作者： zhouyou<br>
 *     
 * 配置Okhttp的Cache<br>
 * 配置请求头中的cache-control或者统一处理所有请求的请求头<br>
 * 云端配合设置响应头或者自己写拦截器修改响应头中cache-control<br>
 * 列：<br>
 *     <p>
 * 在Retrofit中，我们可以通过@Headers来配置，如：
 *
 * @Headers("Cache-Control: public, max-age=3600)
 * @GET("merchants/{shopId}/icon")
 * Observable<ShopIconEntity> getShopIcon(@Path("shopId") long shopId);
 * 
 * 如果你不想加入公共缓存，想单独对某个api进行缓存，可用Headers来实现<br/>
 * 
 * 请参考网址：http://www.jianshu.com/p/9c3b4ea108a7<br>
 *</p>
 * 日期： 2016/12/19 16:35<br>
 * 版本： v2.0<br>
 */
public class CacheInterceptorOffline extends CacheInterceptor {
    public CacheInterceptorOffline(Context context) {
        super(context);
    }

    public CacheInterceptorOffline(Context context, String cacheControlValue) {
        super(context, cacheControlValue);
    }

    public CacheInterceptorOffline(Context context, String cacheControlValue, String cacheOnlineControlValue) {
        super(context, cacheControlValue, cacheOnlineControlValue);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!Utils.isNetworkAvailable(context)) {
            HttpLog.i(" no network load cache:"+ request.cacheControl().toString());
           /* request = request.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "only-if-cached, " + cacheControlValue_Offline)
                    .build();*/

            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    //.cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, " + cacheControlValue_Offline)
                    .build();
        }
        return chain.proceed(request);
    }
}

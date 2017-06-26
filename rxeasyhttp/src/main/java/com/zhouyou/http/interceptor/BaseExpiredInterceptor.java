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

import com.zhouyou.http.utils.HttpLog;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static com.zhouyou.http.utils.HttpUtil.UTF8;

/**
 * <p>描述：判断响应是否有效的处理</p>
 * 继承后扩展各种无效响应处理：包括token过期、账号异地登录、时间戳过期、签名sign错误等<br>
 * 作者： zhouyou<br>
 * 日期： 2017/5/4 19:11 <br>
 * 版本： v1.0<br>
 */
public abstract class BaseExpiredInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        String bodyString = buffer.clone().readString(charset);
        HttpLog.i("网络拦截器:" + bodyString + " host:" + request.url().toString());
        boolean isText = isText(contentType);
        if (!isText) {
            return response;
        }
        //判断响应是否过期（无效）
        if (isResponseExpired(response, bodyString)) {
            return responseExpired(chain, bodyString);
        }
        return response;
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType == null)
            return false;
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 处理响应是否有效
     */
    public abstract boolean isResponseExpired(Response response, String bodyString);

    /**
     * 无效响应处理
     */
    public abstract Response responseExpired(Chain chain, String bodyString);
}

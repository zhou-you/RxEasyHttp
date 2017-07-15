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

package com.zhouyou.http.demo.interceptor;

import com.zhouyou.http.demo.constant.AppConstant;
import com.zhouyou.http.demo.constant.ComParamContact;
import com.zhouyou.http.demo.token.TokenManager;
import com.zhouyou.http.demo.utils.MD5;
import com.zhouyou.http.interceptor.BaseDynamicInterceptor;
import com.zhouyou.http.utils.HttpLog;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.TreeMap;

import static com.zhouyou.http.utils.HttpUtil.UTF8;

/**
 * <p>描述：对参数进行签名、添加token、时间戳处理的拦截器</p>
 * 主要功能说明：<br>
 * 因为参数签名没办法统一，签名的规则不一样，签名加密的方式也不同有MD5、BASE64等等，只提供自己能够扩展的能力。<br>
 * 作者： zhouyou<br>
 * 日期： 2017/5/4 15:21 <br>
 * 版本： v1.0<br>
 */
public class CustomSignInterceptor extends BaseDynamicInterceptor<CustomSignInterceptor> {
    @Override
    public TreeMap<String, String> dynamic(TreeMap<String, String> dynamicMap) {
        //dynamicMap:是原有的全局参数+局部参数
        if (isTimeStamp()) {//是否添加时间戳，因为你的字段key可能不是timestamp,这种动态的自己处理
            dynamicMap.put(ComParamContact.Common.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        }
        if (isAccessToken()) {//是否添加token
            String acccess = TokenManager.getInstance().getAuthModel().getAccessToken();
            dynamicMap.put(ComParamContact.Common.ACCESSTOKEN, acccess);
        }
        if (isSign()) {//是否签名,因为你的字段key可能不是sign，这种动态的自己处理
            dynamicMap.put(ComParamContact.Common.SIGN, sign(dynamicMap));
        }
        //HttpLog.i("dynamicMap:" + dynamicMap.toString());
        return dynamicMap;//dynamicMap:是原有的全局参数+局部参数+新增的动态参数
    }

    //签名规则：POST+url+参数的拼装+secret
    private String sign(TreeMap<String, String> dynamicMap) {
        String url = getHttpUrl().url().toString();
        //url = url.replaceAll("%2F", "/");
        StringBuilder sb = new StringBuilder("POST");
        sb.append(url);
        for (Map.Entry<String, String> entry : dynamicMap.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        sb.append(AppConstant.APP_SECRET);
        String signStr = sb.toString();
        try {
            signStr = URLDecoder.decode(signStr, UTF8.name());
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
        }
        HttpLog.i(signStr);
        return MD5.encode(signStr);
    }
}

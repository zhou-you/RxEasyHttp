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

package com.zhouyou.http.demo.customapi.test8;

import android.text.TextUtils;

import com.zhouyou.http.model.ApiResult;

/**
 * <p>描述：自定义ApiResult，使用情景举列8</p>
 * 金山词霸API 举例
 * 作者： zhouyou<br>
 * 日期： 2017/7/6 17:52 <br>
 * 版本： v1.0<br>
 */
public class TestApiResult8<T> extends ApiResult<T> {
    private String event;
    private String describe;

    @Override
    public boolean isOk() {
        return !TextUtils.isEmpty(event)&&event.equals("SUCCESS");
    }

    @Override
    public String getMsg() {
        if (describe != null) {
            return describe;
        }
        return super.getMsg();
    }
}

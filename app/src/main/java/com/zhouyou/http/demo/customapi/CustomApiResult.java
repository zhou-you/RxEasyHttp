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

package com.zhouyou.http.demo.customapi;

import com.zhouyou.http.model.ApiResult;

/**
 * <p>描述：如果你的不是约定的标准ApiResult，你可以继承ApiResult后扩展</p>
 * 1.继承时要写全extends ApiResult<T>{} 不能写成extends ApiResult{}<br>
 * 2.code、msg、data哪个字段不一样就覆写谁不用全部覆写<br>
 * 作者： zhouyou<br>
 * 日期： 2017/5/22 10:00 <br>
 * 版本： v1.0<br>
 */
public class CustomApiResult<T> extends ApiResult<T> {
    //{"resultcode":"200","reason":"Return Successd!","result":{"province":"广东","city":"深圳","areacode":"0755","zip":"18000","company":"联通","card":""},"error_code":0}
    String reason;
    int error_code;
    //int resultcode;
    T result;

    @Override
    public int getCode() {
        return error_code;
    }

    @Override
    public void setCode(int code) {
        error_code = code;
    }

    @Override
    public String getMsg() {
        return reason;
    }

    @Override
    public void setMsg(String msg) {
        reason = msg;
    }

    @Override
    public T getData() {
        return result;
    }

    @Override
    public void setData(T data) {
        result = data;
    }

   /* @Override
    public boolean isOk() {
        return error_code==200;//如果不是0表示成功，请重写isOk()方法。
    }*/

    @Override
    public String toString() {
        return "PhoneIpApiBean{" +
                "reason='" + reason + '\'' +
                ", error_code=" + error_code +
                ", result=" + getData().toString() +
                '}';
    }


}

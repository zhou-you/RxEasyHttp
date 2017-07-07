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

package com.zhouyou.http.demo.customapi.test3;

import com.zhouyou.http.model.ApiResult;

/**
 * <p>描述：自定义ApiResult，使用情景举列3</p>
 * 作者： zhouyou<br>
 * 日期： 2017/7/6 17:52 <br>
 * 版本： v1.0<br>
 * {
 * "no": 1,
 * "msg": "发送成功",
 * "obj": [
 * "139298cef7df403e82dc31c12b069c79"
 * ]
 * }
 */
public class TestApiResult3<T> extends ApiResult<T> {
    int no;//对应默认标准ApiResult的code
    T obj;//对应默认标准ApiResult的data

    //msg和库中默认标准ApiResult的msg一样所以不需要写
    @Override
    public int getCode() {//因为库里使用code字段，而你的结构是error_code,所以覆写getCode()方法，setCode()方法不用覆写
        return no;
    }

    @Override
    public T getData() {//因为库里使用data字段，而你的结构是obj,所以覆写getData()方法,setData()方法不用覆写
        return obj;
    }

    @Override
    public boolean isOk() {////因为库里使用code字段，code==0表示成功，但是你的数据结构是no==1,所以覆写isOk(),用no==1做为判断成功的条件
        return no==1;
    }
}

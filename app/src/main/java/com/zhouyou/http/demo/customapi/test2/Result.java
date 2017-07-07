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

package com.zhouyou.http.demo.customapi.test2;

import java.util.List;

/**
 * <p>描述：从数据结构json中可以看出，result字段下面是一个data字段，data字段对应的是一个集合，所以result对应的bean如下</p>
 * 作者： zhouyou<br>
 * 日期： 2017/6/27 17:02 <br>
 * 版本： v1.0<br>
 */
public class Result {
    private List<Data> data;
    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "data=" + data +
                '}';
    }
}

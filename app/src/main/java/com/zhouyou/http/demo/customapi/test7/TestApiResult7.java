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

package com.zhouyou.http.demo.customapi.test7;

import com.zhouyou.http.model.ApiResult;

/**
 * <p>描述：自定义ApiResult，使用情景举列7</p>
 * 金山词霸API 举例
 * 作者： zhouyou<br>
 * 日期： 2017/7/6 17:52 <br>
 * 版本： v1.0<br>
 * 没有code、msg；只有一个集合List
 */
public class TestApiResult7<T> extends ApiResult<T> {
    private Result result;

    @Override
    public int getCode() {
        if (result != null) {
            return result.getCode();
        }
        return super.getCode();
    }

    @Override
    public boolean isOk() {
        return getCode() == 200;
    }

    @Override
    public String getMsg() {
        if (result != null) {
            return result.getMessage();
        }
        return super.getMsg();
    }

    public class Result {

        private int code;
        private String message;

        public void setCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }
}

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

package com.zhouyou.http.demo.model;

/**
 * <p>描述：post  body提交演示</p>
 * 作者： zhouyou<br>
 * 日期： 2017/8/9 8:58 <br>
 * 版本： v1.0<br>
 */
public class ApiInfo {
    private ApiInfoBean apiInfo;
    public ApiInfoBean getApiInfo() {
        return apiInfo;
    }
    public void setApiInfo(ApiInfoBean apiInfo) {
        this.apiInfo = apiInfo;
    }
    public class ApiInfoBean {
        private String apiName;
        private String apiKey;
        //省略get和set方法

        public String getApiName() {
            return apiName;
        }

        public ApiInfoBean setApiName(String apiName) {
            this.apiName = apiName;
            return this;
        }

        public String getApiKey() {
            return apiKey;
        }

        public ApiInfoBean setApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }
    }
}

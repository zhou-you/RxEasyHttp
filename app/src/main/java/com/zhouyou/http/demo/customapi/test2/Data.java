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
/**
 * <p>描述：json字符串中，data字段对应的bean</p>
 * 作者： zhouyou<br>
 * 日期： 2017/7/6 17:04 <br>
 * 版本： v1.0<br>
 */
public class Data {
    private String content;
    private String hashId;
    private int unixtime;
    private String updatetime;

    public String getContent() {
        return content;
    }

    public Data setContent(String content) {
        this.content = content;
        return this;
    }

    public String getHashId() {
        return hashId;
    }

    public Data setHashId(String hashId) {
        this.hashId = hashId;
        return this;
    }

    public int getUnixtime() {
        return unixtime;
    }

    public Data setUnixtime(int unixtime) {
        this.unixtime = unixtime;
        return this;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public Data setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
        return this;
    }

    @Override
    public String toString() {
        return "Data{" +
                "content='" + content + '\'' +
                ", hashId='" + hashId + '\'' +
                ", unixtime=" + unixtime +
                ", updatetime='" + updatetime + '\'' +
                '}';
    }
}

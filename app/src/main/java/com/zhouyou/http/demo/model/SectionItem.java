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
 * <p>描述：选择</p>
 * 作者： zhouyou<br>
 * 日期： 2017/5/31 16:54 <br>
 * 版本： v1.0<br>
 */
public class SectionItem {
    String description;//
    int  id;//
    String name; //"深夜惊奇",
    String thumbnail;// "http://pic3.zhimg.com/91125c9aebcab1c84f58ce4f8779551e.jpg"

    public String getDescription() {
        return description;
    }

    public SectionItem setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getId() {
        return id;
    }

    public SectionItem setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SectionItem setName(String name) {
        this.name = name;
        return this;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public SectionItem setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    @Override
    public String toString() {
        return "SectionItem{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
